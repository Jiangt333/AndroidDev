package com.example.myapplication;

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
        dbURL = "jdbc:mysql://172.20.10.3:3306/androiddevdb?serverTimezone=UTC";
        user = "root";
        password = "jt123456";
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

