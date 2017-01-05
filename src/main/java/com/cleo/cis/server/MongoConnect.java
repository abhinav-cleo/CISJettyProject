package com.cleo.cis.server;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pdubey on 1/5/17.
 */

@Path("/mongoTransfers")
public class MongoConnect {

    private static MongoClient mongoClient;

    public MongoConnect() {
    }

    @GET
    @Produces({"application/json"})
    @Path("/data")
    public Response getTransferData(@QueryParam("table") String tableName) {
        this.connectDB();
        JSONArray data = this.retreiveData(tableName);
        return Response.ok().entity(data.toString()).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "*").build();
    }

    public JSONArray retreiveData(String tableName) {
        this.connectDB();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("testDB");
        MongoCollection collection = mongoDatabase.getCollection("test");
        List documents = (List) collection.find().into(new ArrayList());
        JSONArray jsonArray = new JSONArray();
        Iterator var6 = documents.iterator();

        while (var6.hasNext()) {
            Document document = (Document) var6.next();
            jsonArray.put(document);
        }

        return jsonArray;
    }

    public void connectDB() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://10.20.101.232:27017"));
    }
}


