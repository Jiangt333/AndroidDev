package com.example.myapplication.util;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

// An highlighted block
public class MySQLConnections {
    private String driver = "";
    private String dbURL = "";
    private String user = "";
    private String password = "";
    private static MySQLConnections connection = null;
    private MySQLConnections()
    {
        driver = "com.mysql.jdbc.Driver";
        dbURL = "jdbc:mysql://172.17.23.103:3306/android?useSSL=false&serverTimezone=UTC";
        user = "root";
        password = "ZCWei123456789";
    }
    public static Connection getConnection() {
        Connection conn = null;
        connection = new MySQLConnections();
        try {
            Class.forName(connection.driver);
            conn = (Connection) DriverManager.getConnection(connection.dbURL,
                    connection.user, connection.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

