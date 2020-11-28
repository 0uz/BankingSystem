package com.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseLayer {
    private static final String url = "jdbc:mysql://sql2.freemysqlhosting.net:3306";
    private static final String username = "sql2379001";
    private static final String password = "vU8%eR4*";

    public DatabaseLayer() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
    }




}
