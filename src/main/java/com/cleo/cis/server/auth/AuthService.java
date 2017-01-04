package com.cleo.cis.server.auth;

import com.cleo.cis.server.auth.common.AuthException;
import com.cleo.cis.server.auth.perm.ActionsRepo;
import com.cleo.cis.server.auth.perm.AssetRepo;
import com.cleo.cis.server.auth.shiros.ShirosProvider;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * Created by rohit on 03/01/17.
 */
@Path("/authService")
public class AuthService {

  @GET
  @Path("/users")
  @Produces(MediaType.APPLICATION_JSON)
  public Response users(){
    JSONArray users = ShirosProvider.getUsers();
    return Response.status(Response.Status.OK).entity(users.toString()).build();
  }

  @GET
  @Path("/roles")
  @Produces(MediaType.APPLICATION_JSON)
  public Response roles(){
    JSONArray roles = ShirosProvider.getRoles();
    return Response.status(Response.Status.OK).entity(roles.toString()).build();
  }

  @GET
  @Path("/roles/{userName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response roles(@QueryParam("userName") String userName){
    JSONArray roles = ShirosProvider.getRoles(userName);
    return Response.status(Response.Status.OK).entity(roles.toString()).build();
  }

  @GET
  @Path("/permissions/{roleName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response permissions(@QueryParam("roleName") String roleName){
    JSONArray roles = ShirosProvider.permissions(roleName);
    return Response.status(Response.Status.OK).entity(roles.toString()).build();
  }

  @GET
  @Path("/login/{userName}/{password}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(@QueryParam("userName") String userName,
                        @QueryParam("password") String password) throws Exception {
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
      return Response.status(Response.Status.NOT_FOUND).entity(responseJSON.toString()).build();
    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString()).build();
  }

  @GET
  @Path("/createUser/{userName}/{password}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response createUser(@QueryParam("userName") String userName,
                        @QueryParam("password") String password) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("userName",userName);
    responseJSON.put("password",password);
    responseJSON.put("message","user created successfully.");
    try {
      ShirosProvider.addUser(userName, password);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","user creation failed");
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString()).build();
    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString()).build();
  }

  @PUT
  @Path("/addRole/{roleName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addRole(@QueryParam("roleName") String roleName) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("roleName",roleName);
    responseJSON.put("message","role created successfully.");
    try {
      ShirosProvider.addRole(roleName);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","user creation failed");
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString()).build();
    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString()).build();
  }

  @PUT
  @Path("/deleteRole/{roleName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteRole(@QueryParam("roleName") String roleName) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("roleName",roleName);
    responseJSON.put("message","role deleted successfully.");
    try {
      ShirosProvider.deleteRole(roleName);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","user creation failed");
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString()).build();
    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString()).build();
  }

  @PUT
  @Path("/assignRole/{userName}/{roleName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response assignRole(@QueryParam("userName") String userName, @QueryParam("roleName") String roleName) throws Exception {
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
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString()).build();
    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString()).build();
  }

  @PUT
  @Path("/addPermission/{roleName}/{assetName}/{action}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPermission(@QueryParam("roleName") String roleName,
                             @QueryParam("assetName") String assetName,
                                @QueryParam("action") String action) throws Exception {
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
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString()).build();
    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString()).build();
  }


  @DELETE
  @Path("/removeUser/{userName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response removeUser(@QueryParam("userName") String userName) throws Exception {
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
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString()).build();
    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString()).build();
  }

  @DELETE
  @Path("/removeRole/{roleName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response removeRole(@QueryParam("roleName") String roleName) throws Exception {
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
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString()).build();
    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString()).build();
  }

  @GET
  @Path("/addAsset/{assetName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addAsset(@QueryParam("assetName") String assetName) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("message","Asset Added sucessfully");
    try {
      AssetRepo.addAsset(assetName);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","Failed to add asset " + ex.getMessage());
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString()).build();
    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString()).build();
  }

  @GET
  @Path("/addAction/{actionName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addAction(@QueryParam("actionName") String actionName) throws Exception {
    JSONObject responseJSON = new JSONObject();
    responseJSON.put("message","Asset Added sucessfully");
    try {
      ActionsRepo.addAction(actionName);
    }catch ( Exception ex) {
      ex.printStackTrace();
      responseJSON = new JSONObject();
      responseJSON.put("message","Failed to add asset " + ex.getMessage());
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJSON.toString()).build();
    }
    return Response.status(Response.Status.OK).entity(responseJSON.toString()).build();
  }

  @GET
  @Path("/getAssets")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAssets() throws Exception {
    JSONObject responseJSON = new JSONObject();
    JSONArray allAssets = AssetRepo.getAllAssets();
    responseJSON.put("assets",allAssets);
    return Response.status(Response.Status.OK).entity(responseJSON.toString()).build();
  }

  @GET
  @Path("/getActions")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getActions() throws Exception {
    JSONObject responseJSON = new JSONObject();
    JSONArray allActions = ActionsRepo.getAllActions();
    responseJSON.put("actions",allActions);
    return Response.status(Response.Status.OK).entity(responseJSON.toString()).build();
  }
}
