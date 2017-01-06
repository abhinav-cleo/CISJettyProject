package com.cleo.cis.server.services;

import com.cleo.cis.server.auth.common.AuthException;
import com.cleo.cis.server.auth.shiros.ShirosProvider;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;

import javax.ws.rs.core.Response;

/**
 * Created by rohit on 06/01/17.
 */
public class ServiceUtils {

  public static Response hasPermission(String permission) throws AuthException {
    if (ShirosProvider.loggedInUser == null) {
      System.out.println("\n\n\n\n ------- user is null  login first");
      return Response.status(Response.Status.NOT_FOUND).entity("User is not logged in.")
              .header("Access-Control-Allow-Origin", "*")
              .header("Access-Control-Allow-Methods", "*")
              .build();
    }

    Subject curentUser = ShirosProvider.loginUser(ShirosProvider.loggedInUser, "Welcome@2");
    if (!curentUser.isPermitted(permission)) {
      System.out.println("\n\n\n - -Permissions not provided to view users for the user  " + curentUser.getPrincipal());
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("ERROR","User is not allowed to view this resource." + curentUser.getPrincipal().toString());
      return Response.status(Response.Status.FORBIDDEN).entity(jsonObject.toString())
              .header("Access-Control-Allow-Origin", "*")
              .header("Access-Control-Allow-Methods", "*")
              .build();
    }
    return null;
  }
}
