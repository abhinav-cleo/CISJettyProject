package com.cleo.cis.server.services;

import com.cleo.cis.server.AWS.DB.AWSDynamoClient;
import com.cleo.cis.server.AWS.SQS.AWSSQSClient;
import com.cleo.cis.server.auth.common.AuthException;
import com.cleo.cis.server.auth.perm.ActionsRepo;
import com.cleo.cis.server.auth.perm.AssetRepo;
import com.cleo.cis.server.auth.shiros.ShirosProvider;
import org.apache.shiro.subject.Subject;
import org.json.JSONArray;

import javax.ws.rs.*;
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
    public Response sendMessage(String message) throws InterruptedException {
        AWSSQSClient.sendMessage(message);
        AWSDynamoClient.addEventToTable(message);
        return Response.status(Response.Status.OK).entity("message sent successfully.")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "*")
                .build();
    }

    @POST
    @Path("/addEvent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEvent(String event) throws InterruptedException {
        AWSDynamoClient.addEventToTable(event);
        return Response.status(Response.Status.OK).entity("message sent successfully.")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "*")
                .build();
    }

    @GET
    @Path("/getMsgs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMsgs() {
        ArrayList<String> messages = AWSSQSClient.receiveMessage();
        JSONArray msgJSON = new JSONArray(messages);
        return Response.status(Response.Status.OK).entity(msgJSON.toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "*")
                .build();

    }

    @GET
    @Path("/getEventsFromDB")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventsFromDB(@QueryParam("table") String tableName) throws AuthException {
        String permission = AssetRepo.reportAsset + ":" + ActionsRepo.view;

        Subject curentUser = ShirosProvider.loginUser(ShirosProvider.loggedInUser, "Welcome@2");


        if (!curentUser.isPermitted(permission)) {
            System.out.println("\n\n\n - -Permissions not possible for the user  " + curentUser.getPrincipal());
            return Response.status(Response.Status.NOT_FOUND).entity("User is not allowed to view this resource." + curentUser.getPrincipal().toString())
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "*")
                    .build();
        }

        JSONArray eventsFromTable = AWSDynamoClient.getEventsFromTable();
        return Response.status(Response.Status.OK).entity(eventsFromTable.toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "*")
                .build();

    }

}
