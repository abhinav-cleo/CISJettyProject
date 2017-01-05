package com.cleo.cis.server.auth.stormpath;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.api.ApiKeyBuilder;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequestBuilder;
import com.stormpath.sdk.authc.UsernamePasswordRequests;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.impl.api.ClientApiKey;
import com.stormpath.sdk.resource.ResourceException;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rohit on 05/01/17.
 */
public class StormPathProvider {

  static String id = "5GZFKUEB5VZ58IQOWS6PIXD43";
  static String secret = "meqdpiBQihsblcqq3MDzyt1wMeXz+bTUM3L2RvHcVuQ";
  static String appURL = "https://api.stormpath.com/v1/applications/A1B1KHZGDz8StRSilXTNj";


  public static void main(String[] args) {
//    useJava();
    getUserAccounts();
  }

  public static Account createUserAccount(String userName, String password) {
    Client client = createStormClient();
    Application application = getApplication(client);
    return createUserAccount(client, application,userName,password);
  }

  public static JSONArray getUserAccounts() {
    Client client = createStormClient();
    Application application = getApplication(client);
    Map queryParams = new HashMap<>();
    AccountList accounts = application.getAccounts(queryParams);
    JSONArray userArray = new JSONArray();
    for (Account acct : accounts) {
      System.out.println("Found Account: " + acct.getHref() + ", " + acct.getEmail());
      userArray.put(acct.getEmail());
    }
    return userArray;
  }

  private static void useJava() {
    Client client = createStormClient();
    Application application = getApplication(client);
    Account account = createUserAccount(client, application,"test","test");
    findUserAccount(application);
    authenticateUser(application);
  }

  private static void authenticateUser(Application application) {
    Account account;AuthenticationRequest request = UsernamePasswordRequests.builder()
            .setUsernameOrEmail("test")
            .setPassword("Welcome@2")
            .build();

    try {
      AuthenticationResult result = application.authenticateAccount(request);
      account = result.getAccount();
      System.out.println("Authenticated Account: " + account.getUsername() + ", Email: " + account.getEmail());
    } catch (ResourceException ex) {
      throw new RuntimeException(ex);
    }
  }

  private static void findUserAccount(Application application) {
    Map<String, Object> queryParams = new HashMap<String, Object>();
    queryParams.put("username", "test");
    AccountList accounts = application.getAccounts(queryParams);

    for (Account acct : accounts) {
      System.out.println("Found Account: " + acct.getHref() + ", " + acct.getEmail());
    }
  }

  private static Account createUserAccount(Client client, Application application, String userName, String password) {
    Account account = client.instantiate(Account.class);
    populateAccountDetails(account, userName, password);
    account = application.createAccount(account);
    return account;
  }

  private static void populateAccountDetails(Account account, String userName, String password) {
    account
            .setGivenName(userName)
            .setSurname(userName)
            .setUsername(userName)
            .setEmail(userName+"@stormpath.com")
            .setPassword("Welcome@2")
            .getCustomData().put("favoriteColor", "white");
  }

  private static Application getApplication(Client client) {
    return (Application) client.getResource(appURL, Application.class);
  }

  private static Client createStormClient() {
    ClientBuilder builder = Clients.builder();
    ClientApiKey clientApiKey = new ClientApiKey(id, secret);
    builder.setApiKey(clientApiKey);
    return builder.build();
  }
}
