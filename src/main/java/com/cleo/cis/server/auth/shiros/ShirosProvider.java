package com.cleo.cis.server.auth.shiros;

import com.cleo.cis.server.auth.common.AuthException;
import com.cleo.cis.server.auth.common.AuthUtils;
import com.cleo.cis.server.auth.perm.ActionsRepo;
import com.cleo.cis.server.auth.perm.AssetRepo;
import com.cleo.cis.server.auth.stormpath.StormPathProvider;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.json.JSONArray;

import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by rohit on 03/01/17.
 */
public class ShirosProvider {

  private static String CONFIG_USER_FIND_LINE = "repalcementUser=replacementPassword";

  public static String loggedInUser;

  static IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiros.ini");
  static Ini ini = factory.getIni();

  static {
    DefaultSecurityManager sm = new DefaultSecurityManager();
    sm.setRealm(new IniRealm(ini));
    SecurityUtils.setSecurityManager(sm);
    System.out.println("\n\n loading the users from storm database.");
    JSONArray userAccounts = StormPathProvider.getUserAccounts();
    for(int i=0;i<userAccounts.length();i++) {
      String userName = userAccounts.get(i).toString();
      try {
        System.out.println("\n\n adding user account ." + userName);
        addUser(userName,"Welcome@2");
      } catch (IOException e) {
        e.printStackTrace();
      } catch (AuthException e) {
        e.printStackTrace();
      }
    }
  }

  public static Subject loginUser(String userName, String password) throws AuthException {
    Subject currentUser = SecurityUtils.getSubject();
    System.out.println("user = " + userName);
    System.out.println("passwpord = " + password);
    authenticateUser(currentUser,userName,password);
    return currentUser;
  }

  public static boolean addUser(String userName, String password) throws IOException, AuthException {
    DefaultSecurityManager sm = new DefaultSecurityManager();
    Ini.Section section = ini.getSection(IniRealm.USERS_SECTION_NAME);
    section.put(userName, "Welcome@2");
    sm.setRealm(new IniRealm(ini));
    SecurityUtils.setSecurityManager(sm);
    return true;
  }

  public static boolean addRole(String roleName) {
    DefaultSecurityManager sm = new DefaultSecurityManager();
    Ini.Section section = ini.getSection(IniRealm.ROLES_SECTION_NAME);
    section.put(roleName, "*");
    sm.setRealm(new IniRealm(ini));
    SecurityUtils.setSecurityManager(sm);
    return true;
  }

  public static boolean deleteUser(String userName) throws IOException {
    Ini.Section section = ini.getSection(IniRealm.USERS_SECTION_NAME);
    section.remove(userName);
    DefaultSecurityManager sm = new DefaultSecurityManager();
    sm.setRealm(new IniRealm(ini));
    SecurityUtils.setSecurityManager(sm);
    return true;
  }

  public static JSONArray getUsers() {
    Ini.Section section = ini.getSection(IniRealm.USERS_SECTION_NAME);
    return new JSONArray(section.keySet());
  }

  public static JSONArray getRoles() {
    Ini.Section section = ini.getSection(IniRealm.ROLES_SECTION_NAME);
    return new JSONArray(section.keySet());
  }

  public static JSONArray getRoles(String userName) {
    Ini.Section section = ini.getSection(IniRealm.USERS_SECTION_NAME);
    String roles = section.get(userName);
    return new JSONArray(roles.split(","));
  }

  private static LinkedList<String> addNewUserLine(String userName, String password, File configResource) throws IOException {
    FileInputStream stream = new FileInputStream(configResource);
    List<String> orignalData = IOUtils.readLines(stream);
    stream.close();
    LinkedList<String> modifiedData = new LinkedList<String>();
    for (String line : orignalData) {
      modifiedData.add(line);
      if(line.equals(CONFIG_USER_FIND_LINE)) {
        modifiedData.add(userName+"="+password +",default");
      }
    }
    return modifiedData;
  }

  private static LinkedList<String> removeUserLine(String userName, File configResource) throws IOException {
    FileInputStream stream = new FileInputStream(configResource);
    List<String> orignalData = IOUtils.readLines(stream);
    stream.close();
    boolean userFound = false;
    LinkedList<String> modifiedData = new LinkedList<String>();
    for (String line : orignalData) {
      modifiedData.add(line);
      if(line.contains(userName+"=")) {
        userFound = true;
        continue;
      }
    }
    if(!userFound) {
      throw new RuntimeException("User with the username " + userName + " was not found in the shiros realm.");
    }
    return modifiedData;
  }

  public static boolean isValidUser(String userName) throws AuthException {
    configureSecurityManager();

    Subject currentUser = SecurityUtils.getSubject();

    // Do some stuff with a Session (no need for a web or EJB container!!!)
    Session session = currentUser.getSession();
    session.setAttribute("someKey", "aValue");
    String value = (String) session.getAttribute("someKey");
    if (value.equals("aValue")) {
      System.out.println("Retrieved the correct value! [" + value + "]");
    }

    // let's login the current user so we can check against roles and permissions:
    authenticateUser(currentUser,"lonestarr", "vespa");

    //say who they are:
    //print their identifying principal (in this case, a username):
    System.out.println("User [" + currentUser.getPrincipal() + "] logged in successfully.");

    //test a role:
    if (currentUser.hasRole("schwartz")) {
      System.out.println("May the Schwartz be with you!");
    } else {
      System.out.println("Hello, mere mortal.");
    }

    //test a typed permission (not instance-level)
    if (currentUser.isPermitted("lightsaber:wield")) {
      System.out.println("You may use a lightsaber ring.  Use it wisely.");
    } else {
      System.out.println("Sorry, lightsaber rings are for schwartz masters only.");
    }

    //a (very powerful) Instance Level permission:
    if (currentUser.isPermitted("winnebago:drive:eagle5")) {
      System.out.println("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
              "Here are the keys - have fun!");
    } else {
      System.out.println("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
    }

    //all done - log out!
    currentUser.logout();
    return true;
  }

  private static void authenticateUser(Subject currentUser, String userName, String password) throws AuthException {
      UsernamePasswordToken token = new UsernamePasswordToken(userName, "Welcome@2");
      token.setRememberMe(false);
      try {
        currentUser.login(token);
        loggedInUser = userName;
        System.out.println("setting current userName to " + loggedInUser);
      } catch (UnknownAccountException uae) {
        throw new AuthException("There is no user with username of " + token.getPrincipal());
      } catch (IncorrectCredentialsException ice) {
        throw new AuthException("Password for account " + token.getPrincipal() + " was incorrect!");
      } catch (LockedAccountException lae) {
        throw new AuthException("The account for username " + token.getPrincipal() + " is locked.  " +
                "Please contact your administrator to unlock it.");
      }
      // ... catch more exceptions here (maybe custom ones specific to your application?
      catch (AuthenticationException ae) {
        throw new AuthException(ae);
    }
  }

  private static void configureSecurityManager() {
    IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiros.ini");
    Ini ini = factory.getIni();
    SecurityManager securityManager = factory.getInstance();
    SecurityUtils.setSecurityManager(securityManager);
  }

  public static void deleteRole(String roleName) {
    Ini.Section section = ini.getSection(IniRealm.ROLES_SECTION_NAME);
    section.remove(roleName);
    DefaultSecurityManager sm = new DefaultSecurityManager();
    sm.setRealm(new IniRealm(ini));
    SecurityUtils.setSecurityManager(sm);

  }

  public static void assignRole(String userName, String roleName) {
    Ini.Section section = ini.getSection(IniRealm.USERS_SECTION_NAME);
    String roles = section.remove(userName);
    if(roles!=null) {
      roles = ","+roleName;
    } else {
      roles = roleName;
    }
    section.put(userName,roles);
    DefaultSecurityManager sm = new DefaultSecurityManager();
    sm.setRealm(new IniRealm(ini));
    SecurityUtils.setSecurityManager(sm);
  }

  public static JSONArray permissions(String roleName){
    Ini.Section section = ini.getSection(IniRealm.ROLES_SECTION_NAME);
    String perms = section.get(roleName);
    if(perms == null) {
      return new JSONArray();
    }
    return new JSONArray(perms.split(","));
  }

  public static void assignPermission(String roleName, String assetName, String action) throws AuthException {
    if(!AssetRepo.assetExists(assetName)) {
      throw new AuthException("Asset :" +assetName+" does not exits. Create the asset first");
    }

    if(!ActionsRepo.actionExists(action)) {
      throw new AuthException("Action ::" + action + " does not exists. Create the ");
    }

    Ini.Section section = ini.getSection(IniRealm.ROLES_SECTION_NAME);
    String perms = section.get(roleName);
    if(perms != null) {
      if(perms.contains(assetName+":"+action)){
        throw new AuthException("This permission is already assigned to this role.");
      }
      perms = perms + ","+assetName + ":" + action;
      section.put(roleName,perms);
    } else {
      section.put(roleName,assetName + ":" + action);
    }
    DefaultSecurityManager sm = new DefaultSecurityManager();
    sm.setRealm(new IniRealm(ini));
    SecurityUtils.setSecurityManager(sm);
  }


  public static void main(String[] args) {
    JSONArray users = getUsers();
  }
}
