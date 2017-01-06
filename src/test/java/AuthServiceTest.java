import com.cleo.cis.server.services.AuthService;
import com.cleo.cis.server.auth.common.AuthException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Response;

/**
 * Created by rohit on 03/01/17.
 */
public class AuthServiceTest {

  // The users are default bound to the thread context by shiros, hence all new login needs to be in new context.

  @Rule
  public RunInThreadRule runInThread = new RunInThreadRule();


  @Test
  @RunInThread
  public void testLoginSuccess() throws Exception {
    String testUserName  = "reportUser";
    String testPassword = "Welcome@2";
    Response response = null;
    response = new AuthService().login(testUserName, testPassword);
    Assert.assertEquals(200,response.getStatus());
    System.out.println("validated the response");
  }

  @Test
  @RunInThread
  public void testLoginFailure() throws Exception {
    String testUserName = "lonestarr1";
    String testPassword = "vespa2";
    Response response = null;
    response = new AuthService().login(testUserName, testPassword);
    String json = response.getEntity().toString();
    JSONObject jsonObject = new JSONObject(json);
    Assert.assertEquals(404, response.getStatus());
    Assert.assertEquals("user not found", jsonObject.getString("message"));
  }

  @Test
  @RunInThread
  public void testCreateAndDeleteNewUser() throws Exception {
    String testUserName  = "testUser";
    String testPassword = "testPassword";
    Response response = null;
    AuthService authService = new AuthService();
    response = authService.createUser(testUserName, testPassword);
    Assert.assertEquals(200,response.getStatus());
    System.out.println("New User creation Successful");
    response = authService.login(testUserName, testPassword);
    Assert.assertEquals(200,response.getStatus());
    response = authService.removeUser(testUserName);
    Assert.assertEquals(200,response.getStatus());
    System.out.println("User deleted Successful");
    Response users = authService.users();
    Assert.assertFalse(users.getEntity().toString().contains(testUserName+"="));
  }

  @Test
  @RunInThread
  public void testGetUsers() throws Exception {
    System.out.println("Fetching all the users.");
    AuthService authService = new AuthService();
    authService.login("viewUser","Welcome@2");
    Response response = authService.users();
    Assert.assertEquals(200,response.getStatus());
    String userJSON = response.getEntity().toString();
    JSONArray jsonArray = new JSONArray(userJSON);
    Assert.assertTrue(jsonArray.length()>2);
  }

  @Test
  @RunInThread
  public void testGetRoles() {
    System.out.println("Fetching all the roles.");
    AuthService authService = new AuthService();
    Response response = authService.roles();
    Assert.assertEquals(200,response.getStatus());
    String userJSON = response.getEntity().toString();
    JSONArray jsonArray = new JSONArray(userJSON);
    Assert.assertTrue(jsonArray.length()>2);
  }

  @Test
  @RunInThread
  public void testAddDeleteRole() throws Exception {
    System.out.println("Checking Role Addition.");
    AuthService authService = new AuthService();
    String testRole = "testRole";
    Response response = authService.addRole(testRole);
    Assert.assertEquals(200,response.getStatus());
    response = authService.roles();
    Assert.assertEquals(200,response.getStatus());
    Assert.assertTrue(response.getEntity().toString().contains(testRole));
    authService.deleteRole(testRole);
    response = authService.roles();
    Assert.assertEquals(200,response.getStatus());
    Assert.assertFalse(response.getEntity().toString().contains(testRole));
  }

  @Test
  @RunInThread
  public void testAssignRole() throws Exception {
    System.out.println("Checking Role Addition.");
    AuthService authService = new AuthService();
    String testRole = "testRole";
    String testUser = "testUser";
    Response response = authService.addRole(testRole);
    Assert.assertEquals(200,response.getStatus());
    response = authService.createUser(testUser, testUser);
    Assert.assertEquals(200,response.getStatus());
    authService.assignRole(testUser,testRole);
    authService.login("viewUser","password");
    response  = authService.users();
    Assert.assertEquals(200,response.getStatus());
    Assert.assertTrue(response.getEntity().toString().contains(testUser));
    response = authService.roles(testUser);
    Assert.assertEquals(200,response.getStatus());
  }

  @Test
  @RunInThread
  public void testPermissions() throws Exception {
    System.out.println("Checking Permissions.");
    AuthService authService = new AuthService();
    String testRole = "viewUserRole";
    Response response = authService.permissions(testRole);
    Assert.assertEquals(200,response.getStatus());
    String perms = response.getEntity().toString();
    Assert.assertTrue(perms.contains("User"));
    String assetName = "car";
    authService.addAsset(assetName);
    String actionName = "crash";
    authService.addAction(actionName);
    response = authService.addPermission(testRole, assetName, actionName);
    Assert.assertEquals(200,response.getStatus());
    response = authService.permissions(testRole);
    Assert.assertEquals(200,response.getStatus());
    perms = response.getEntity().toString();
    Assert.assertTrue(perms.contains("car"));
    Assert.assertTrue(perms.contains("User"));

  }

}
