package com.cleo.cis.server;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("/cis")
public class CisProvider {

    private static Connection conn = null;
    private static Statement stmt = null;
    public JSONArray data = null;

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readData(@QueryParam("table") String table) throws Exception {
        System.out.println(table);
        createConnection();
        try {
            dropTable(table);
        } catch (Exception ex) {
            System.out.println("Drop table exception ignored");
        }
        createTable(table);

        insertStocks("1", "4715", "KEANU", table);
        insertStocks("2", "1234", "NEO", table);
        insertStocks("3", "234", "BLADE", table);
        insertStocks("6", "3456", "MATRIX", table);
        JSONArray data = new JSONArray();
        stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("select * from " + table);
        data = convertToJSON(results);
        return Response.ok()
                .entity(data.toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "*")
                .build();
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

    private static void createConnection() {
        try {
            Class.forName(EmbeddedDriver.class.getName()).newInstance();
            //Get a connection
            conn = DriverManager.getConnection("jdbc:derby:.\\cis1.db;create=true");
            stmt = conn.createStatement();
        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    private static void insertStocks(String STOCK_ID, String STOCK_CODE, String STOCK_NAME, String table) {
        try {
            stmt.execute("insert into " + table + " values ('" +
                    STOCK_ID + "','" + STOCK_CODE + "','" + STOCK_NAME + "')");
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    private static void createTable(String table) throws SQLException {
        stmt.execute("create table "+table+" ("+table+"_ID varchar(10), "+table+"_CODE varchar(10), "+table+"_NAME varchar(10))");
    }

    private static void dropTable(String table) throws SQLException {
        stmt.execute("drop table " + table);
    }
}
