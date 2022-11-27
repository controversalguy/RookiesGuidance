package pt.isec.gps.rookiesguidance.bd;

import java.sql.*;

public class ConnDB {

    //Project structure w/jar file
    public final String DATABASE = "jdbc:sqlite:TP.db";
    public final Connection dbConn;


    public ConnDB() throws SQLException {
        dbConn = DriverManager.getConnection(DATABASE);
    }


    public void close() throws SQLException {
        if (dbConn != null)
            dbConn.close();
    }

    public void createTables() {
        try {
            Statement statement = dbConn.createStatement();


            String sqlQuery = "CREATE TABLE IF NOT EXISTS utilizador" +
                    "(numero INTEGER NOT NULL," +
                    "nome TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "password TEXT NOT NULL," +
                    "curso TEXT NOT NULL," +
                    "isGestor INTEGER NOT NULL)";

            statement.executeUpdate(sqlQuery);
            statement.close();
        } catch (SQLException e) {
            System.out.println("erro");
        }

    }



}

