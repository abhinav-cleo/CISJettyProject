package com.cleo.cis.server.services;

import com.cleo.cis.server.auth.common.AuthException;
import com.cleo.cis.server.auth.perm.ActionsRepo;
import com.cleo.cis.server.auth.perm.AssetRepo;
import com.cleo.cis.server.auth.shiros.ShirosProvider;
import com.cleo.cis.server.auth.stormpath.StormPathProvider;
import org.apache.shiro.subject.Subject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.Service;
import java.util.Set;

/**
 * Created by rohit on 03/01/17.
 */
@Path("/authService")
public class AuthService {

  @GET
  @Path("/users")
  @Produces(MediaType.APPLICATION_JSON)
  public Response users() throws AuthException {
    String permission = AssetRepo.userAsset + ":" + ActionsRepo.view;
    Response jsonObject = ServiceUtils.hasPermission(permission);
    if (jsonObject != null) return jsonObject;
    JSONArray users = ShirosProvider.getUsers();
    return Response.status(Response.Status.OK).entity(users.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();
  }

  public static void main(String[] args) throws AuthException {
    ShirosProvider.loggedInUser = "viewUser";
    Response users = new AuthService().users();
    System.out.println(users.getEntity().toString());
  }

  @GET
  @Path("/roles")
  @Produces(MediaType.APPLICATION_JSON)
  public Response roles(){
    JSONArray roles = ShirosProvider.getRoles();
    return Response.status(Response.Status.OK).entity(roles.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }

  @GET
  @Path("/roles/{userName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response roles(@PathParam("userName") String userName){
    JSONArray roles = ShirosProvider.getRoles(userName);
    return Response.status(Response.Status.OK).entity(roles.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }

  @GET
  @Path("/permissions/{roleName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response permissions(@PathParam("roleName") String roleName){
    JSONArray roles = ShirosProvider.permissions(roleName);
    return Response.status(Response.Status.OK).entity(roles.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }


  @GET
  @Path("/login/{userName}/{password}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(@PathParam("userName") String userName,
                        @PathParam("password") String password) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("userName",userName);
    responseJSON.put("password",password);
    responseJSON.put("message","user logged in successfully.");
    try {
      ShirosProvider.loginUser(userName, password);
    }catch (Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","user not found");
      responseJSON.put("error",ex.getMessage());
      return Response.status(Response.Status.NOT_FOUND).entity(responseJSON.toString())
              .header("Access-Control-Allow-Origin", "*")
              .header("Access-Control-Allow-Methods", "*")
              .build();

    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }

  @GET
  @Path("/createUser/{userName}/{password}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response createUser(@PathParam("userName") String userName,
                             @PathParam("password") String password) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("userName",userName);
    responseJSON.put("password",password);
    responseJSON.put("message","user created successfully.");
    try {
      ShirosProvider.addUser(userName, password);
      StormPathProvider.createUserAccount(userName,password);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","user creation failed");
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString())
              .header("Access-Control-Allow-Origin", "*")
              .header("Access-Control-Allow-Methods", "*")
              .build();

    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }

  @GET
  @Path("/addRole/{roleName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addRole(@PathParam("roleName") String roleName) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("roleName",roleName);
    responseJSON.put("message","role created successfully.");
    try {
      ShirosProvider.addRole(roleName);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","user creation failed");
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString())
              .header("Access-Control-Allow-Origin", "*")
              .header("Access-Control-Allow-Methods", "*")
              .build();

    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }

  @GET
  @Path("/deleteRole/{roleName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteRole(@PathParam("roleName") String roleName) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("roleName",roleName);
    responseJSON.put("message","role deleted successfully.");
    try {
      ShirosProvider.deleteRole(roleName);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","user creation failed");
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString())
              .header("Access-Control-Allow-Origin", "*")
              .header("Access-Control-Allow-Methods", "*")
              .build();

    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }

  @GET
  @Path("/assignRole/{userName}/{roleName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response assignRole(@PathParam("userName") String userName, @PathParam("roleName") String roleName) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("userName",userName);
    responseJSON.put("roleName",roleName);
    responseJSON.put("message","role deleted successfully.");
    try {
      ShirosProvider.assignRole(userName,roleName);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","user creation failed");
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString())
              .header("Access-Control-Allow-Origin", "*")
              .header("Access-Control-Allow-Methods", "*")
              .build();

    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }

  @GET
  @Path("/addPermission/{roleName}/{assetName}/{action}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPermission(@PathParam("roleName") String roleName,
                                @PathParam("assetName") String assetName,
                                @PathParam("action") String action) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("assetName",assetName);
    responseJSON.put("action",action);
    responseJSON.put("roleName",roleName);
    responseJSON.put("message","role deleted successfully.");
    try {
      ShirosProvider.assignPermission(roleName,assetName,action);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","user creation failed");
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString())
              .header("Access-Control-Allow-Origin", "*")
              .header("Access-Control-Allow-Methods", "*")
              .build();

    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }


  @GET
  @Path("/removeUser/{userName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response removeUser(@PathParam("userName") String userName) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("userName",userName);
    responseJSON.put("message","user deleted successfully.");
    try {
      ShirosProvider.deleteUser(userName);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","user deletion failed");
      responseJSON.put("error",ex.getMessage());
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString())
              .header("Access-Control-Allow-Origin", "*")
              .header("Access-Control-Allow-Methods", "*")
              .build();

    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }

  @GET
  @Path("/removeRole/{roleName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response removeRole(@PathParam("roleName") String roleName) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("roleName",roleName);
    responseJSON.put("message","Role deleted successfully.");
    try {
      ShirosProvider.deleteRole(roleName);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","user deletion failed");
      responseJSON.put("error",ex.getMessage());
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString())
              .header("Access-Control-Allow-Origin", "*")
              .header("Access-Control-Allow-Methods", "*")
              .build();

    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }

  @GET
  @Path("/addAsset/{assetName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addAsset(@PathParam("assetName") String assetName) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("message","Asset Added sucessfully");
    try {
      AssetRepo.addAsset(assetName);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","Failed to add asset " + ex.getMessage());
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString())
              .header("Access-Control-Allow-Origin", "*")
              .header("Access-Control-Allow-Methods", "*")
              .build();

    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }

  @GET
  @Path("/addAction/{actionName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addAction(@PathParam("actionName") String actionName) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("message","Asset Added sucessfully");
    try {
      ActionsRepo.addAction(actionName);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","Failed to add asset " + ex.getMessage());
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString())
              .header("Access-Control-Allow-Origin", "*")
              .header("Access-Control-Allow-Methods", "*")
              .build();

    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();
  }

  @GET
  @Path("/getAssets")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAssets() throws Exception {
    JSONObject responseJSON = new JSONObject();
    JSONArray allAssets = AssetRepo.getAllAssets();
    responseJSON.put("assets",allAssets);
    return Response.status(Response.Status.OK).entity(responseJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();
  }

  @GET
  @Path("/getActions")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getActions() throws Exception {
    JSONObject responseJSON = new JSONObject();
    JSONArray allActions = ActionsRepo.getAllActions();
    responseJSON.put("actions",allActions);
    return Response.status(Response.Status.OK).entity(responseJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();
  }
}
