package com.cleo.cis.server.mongo.sample;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santoshsingh on 04/01/17.
 */
public class EventRepo {

  private DBCollection table;

  public EventRepo(String repoHost, int repoPort, String dbName, String collectionName) throws UnknownHostException {
    this.table = new MongoClient(repoHost, repoPort)
            .getDB(dbName)
            .getCollection(collectionName);
  }

  public void insertEvent(String event) {
    System.out.println("Inserting record: " + event);
    BasicDBObject document = new BasicDBObject();
    document.put("event", event);
    table.insert(document);
    System.out.println("Inserting record...: " + event);
  }

  public List<String> getEvents(){
    List<String> fetchedEvents = new ArrayList<>();
    DBCursor cursor = table.find();
    while (cursor.hasNext()) {
      fetchedEvents.add(cursor.next().get("event").toString());
    }

    return fetchedEvents;
  }

  public static void main(String[] args) {
    try {
      MongoClient mongo = new MongoClient("ec2-35-166-252-121.us-west-2.compute.amazonaws.com", 27017);

      DB db = mongo.getDB("siteA");

      /**** Get collection / table from 'testdb' ****/
      // if collection doesn't exists, MongoDB will create it for you
      DBCollection table = db.getCollection("test");

      /**** Insert ****/
      // create a document to store key and value
//      BasicDBObject document = new BasicDBObject();
//      document.put("event", "newEvent");
//      table.insert(document);


      /**** Find and display ****/
      BasicDBObject searchQuery = new BasicDBObject();
      searchQuery.put("a", "1");

      DBCursor cursor = table.find();

      while (cursor.hasNext()) {
        System.out.println(cursor.next());
      }

      System.out.println("Done");

    } catch (MongoException e) {
      e.printStackTrace();
    }

  }
}
