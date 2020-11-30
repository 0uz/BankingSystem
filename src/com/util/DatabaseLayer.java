package com.util;



import java.sql.*;


public class DatabaseLayer {
    private static final String url = "jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2379001";
    private static final String username = "sql2379001";
    private static final String password = "vU8%eR4*";
    Connection connection;
    public DatabaseLayer() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Succesfull");
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
    }

    public void insertUser(String FName, String LName, Float TC, String eMail, String password, Date BDate, String address){
        try {
            PreparedStatement statement = connection.prepareStatement("insert into users (F_Name, L_Name, TC, address, mail, password, B_Date) VALUES (?,?,?,?,?,?,?)");
            statement.setString(1,FName);
            statement.setString(2,LName);
            statement.setFloat(3,TC);
            statement.setString(4,address);
            statement.setString(5,eMail);
            statement.setString(6,password);
            statement.setDate(7,BDate);
            statement.execute();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }



}
