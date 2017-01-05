package com.cleo.cis.server.AWS.DB;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.cleo.cis.server.AWS.AWSUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.*;

/**
 * Created by rohit on 05/01/17.
 */
public class AWSDynamoClient {

    static String tableName = "EventsTable";

    public static void addEventToTable(String jsonString) throws InterruptedException {
        AmazonDynamoDBClient dynamoDB = getDBClient();

        CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
                .withKeySchema(new KeySchemaElement().withAttributeName("name").withKeyType(KeyType.HASH))
                .withAttributeDefinitions(new AttributeDefinition().withAttributeName("name").withAttributeType(ScalarAttributeType.S))
                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));

        // Create table if it does not exist yet
        TableUtils.createTableIfNotExists(dynamoDB, createTableRequest);
        // wait for the table to move into ACTIVE state
        TableUtils.waitUntilActive(dynamoDB, tableName);

        // Describe our new table
        DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
        TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
        System.out.println("Table Description: " + tableDescription);

        // Add an item
        PutItemRequest putItemRequest = new PutItemRequest(tableName, getAWSMapFromJSON(jsonString));
        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
        System.out.println("Result: " + putItemResult);
    }

    private static AmazonDynamoDBClient getDBClient() {
        AWSCredentials credentials = AWSUtils.getCredentials();
        AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        dynamoDB.setRegion(usWest2);
        return dynamoDB;
    }

    private static Map<String, AttributeValue> getAWSMapFromJSON(String json) {
        JSONObject jsonObject = new JSONObject(json);
        HashMap<String, AttributeValue> map = new HashMap<>();
        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next().toString();
            map.put(key, new AttributeValue(jsonObject.getString(key)));
        }
        return map;
    }

    public static JSONArray getEventsFromTable() {
        HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
        ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
        ScanResult scanResult = getDBClient().scan(scanRequest);
        List<Map<String, AttributeValue>> items = scanResult.getItems();
        JSONArray jsonArray = new JSONArray();
        for (Map<String, AttributeValue> item : items) {
            Set<Map.Entry<String, AttributeValue>> entries = item.entrySet();
            for (Map.Entry<String, AttributeValue> entry : entries) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(entry.getKey(), entry.getValue().getS());
                jsonArray.put(jsonObject);
            }
        }
        return jsonArray;
    }

    public static void main(String[] args) throws InterruptedException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "rohit2");
        addEventToTable(jsonObject.toString());
        JSONArray eventsFromTable = getEventsFromTable();
        System.out.println(eventsFromTable);
    }
}
