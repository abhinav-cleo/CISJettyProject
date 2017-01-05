package com.cleo.cis.server.services;

import com.cleo.cis.server.AWS.SQS.AWSSQSClient;
import com.cleo.cis.server.auth.shiros.ShirosProvider;
import org.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by rohit on 05/01/17.
 */
@Path("/awsService")
public class AWSService {

  @POST
  @Path("/sendMessage")
  @Produces(MediaType.APPLICATION_JSON)
  public Response sendMessage(String message){
    AWSSQSClient.sendMessage(message);
    return Response.status(Response.Status.OK).entity("message sent successfully.")
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();
  }

  @GET
  @Path("/getMsgs")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMsgs(){
    ArrayList<String> messages = AWSSQSClient.receiveMessage();
    JSONArray msgJSON = new JSONArray(messages);
    return Response.status(Response.Status.OK).entity(msgJSON.toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "*")
            .build();

  }

}
