package com.util;



import javafx.scene.control.Alert;

import java.sql.*;


public class DatabaseLayer {
    private static final String url = "jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2379001";
    private static final String username = "sql237900";
    private static final String password = "vU8%eR4*";
    Connection connection;
    public DatabaseLayer() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Error");
            alert.setContentText("Database connection error!\nPlease check your connection.");
            alert.showAndWait();
            System.exit(1);
        }
    }

    public boolean insertUser(String FName, String LName, Double TC, String eMail, String password, Date BDate, String address){
        try {
            PreparedStatement statement = connection.prepareStatement("insert into users (F_Name, L_Name, TC, address, mail, password, B_Date) VALUES (?,?,?,?,?,?,?)");
            statement.setString(1,FName);
            statement.setString(2,LName);
            statement.setDouble(3,TC);
            statement.setString(4,address);
            statement.setString(5,eMail);
            statement.setString(6,password);
            statement.setDate(7,BDate);
            statement.execute();
            connection.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }


    }


    public boolean loginUserControl(Double TC , String password){
        try {
            PreparedStatement statement =  connection.prepareStatement("select password,TC from users where password = ? AND TC = ?");
            statement.setString(1,password);
            statement.setDouble(2,TC);
            ResultSet rs = statement.executeQuery();
            rs.next();
            rs.getString(1);
            return true;
        } catch (SQLException | RuntimeException throwables) {
            return false;
        }

    }


}
