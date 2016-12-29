package com.cleo.cis.server;

import com.mysql.jdbc.Driver;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by pdubey on 12/28/16.
 */
@Path("/transfers")
public class CISTransfers {

    private static Connection conn = null;
    private static Statement stmt = null;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/data")
    public Response getTransferData(@QueryParam("table") String tableName){
        createConnection();
        JSONArray data = retreiveData(tableName);
        return Response.ok()
                .entity(data.toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "*")
                .build();
    }

    private static void createConnection() {
        try {
            Class.forName(Driver.class.getName()).newInstance();
            //Get a connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/versalexTest", "root", "root");
            stmt = conn.createStatement();
        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    private static JSONArray retreiveData(String tableName){
        JSONArray data = null;
        try{
            String sql = "Select * from "+ tableName;
            ResultSet resultSet = stmt.executeQuery(sql);
            data = convertToJSON(resultSet);
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public static JSONArray convertToJSON(ResultSet resultSet)
            throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject obj = new JSONObject();
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), resultSet.getObject(i + 1));
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }
}
