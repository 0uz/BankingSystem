package com.util;



import javafx.scene.control.Alert;

import java.sql.*;


public class DatabaseLayer {
    private static final String url = "jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2380411";
    private static final String username = "sql2380411";
    private static final String password = "aB9%mX1*";
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

    public void createTables() {
        String userTable = "CREATE TABLE IF NOT EXISTS users(" +
            "F_Name VARCHAR(20)," +
            "L_Name VARCHAR(20)," +
            "TC BIGINT(11) PRIMARY KEY," +
            "address VARCHAR(100)," +
            "mail VARCHAR(320)," +
            "password VARCHAR(50)," +
            "B_Date DATE," +
            "admin_F BOOL default false);";

        String accountTable = "CREATE TABLE IF NOT EXISTS accounts(" +
                "TC bigint(11) NOT NULL," +
                "IBAN VARCHAR(26) PRIMARY KEY ," +
                "currency varchar(30) DEFAULT 'TL'," +
                "mainAccF bool default false " +
                "foreign key ('TC') REFERENCES users('TC'));";

        String transactionTable = "CREATE TABLE IF NOT EXISTS transactions(" +
                "senderIBAN VARCHAR(23) NOT NULL," +
                "receiverIBAN VARCHAR(23)," +
                "amount INTEGER," +
                "T_date date," +
                "isRead bool default false, " +
                "FOREIGN KEY ('senderIBAN') REFERENCES accounts('IBAN'));";

        String creditTable = "CREATE TABLE IF NOT EXISTS credits(" +
                "TC bigint(11) not null," +
                "amount int," +
                "creditMonths tinyint," +
                "getCreditDate date," +
                "confirmation bool default false, " +
                "FOREIGN KEY ('TC') REFERENCES users('TC'));";

        try {
                Statement statement = connection.createStatement();
                        statement.execute(userTable);
                        statement.execute(accountTable);
                        statement.execute(transactionTable);
                        statement.execute(creditTable);
                        connection.close();
        }catch (SQLException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Error");
            alert.setContentText("Database connection error!\nPlease check your connection.");
            alert.showAndWait();
            System.exit(1);
        }



    }

    public boolean insertUser(String FName, String LName, Double TC, String eMail, String password, Date BDate, String address,String IBAN,Double moneyAmount){
        try {
            PreparedStatement statement = connection.prepareStatement("insert into users (F_Name, L_Name, TC, address, mail, password, B_Date) VALUES (?,?,?,?,?,?,?)");
            PreparedStatement statement2 = connection.prepareStatement("insert into accounts (TC,IBAN,amount,mainAccF) value (?,?,?,?)");
            statement.setString(1,FName);
            statement.setString(2,LName);
            statement.setDouble(3,TC);
            statement.setString(4,address);
            statement.setString(5,eMail);
            statement.setString(6,password);
            statement.setDate(7,BDate);
            statement2.setDouble(1,TC);
            statement2.setString(2,IBAN);
            statement2.setDouble(3,moneyAmount);
            statement2.setBoolean(4,true);
            statement.execute();
            statement2.execute();
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

    public boolean IBANConflictControl(String IBAN){
        try {
            PreparedStatement statement =  connection.prepareStatement("select IBAN from accounts where IBAN = ?");
            statement.setString(1,IBAN);
            ResultSet rs = statement.executeQuery();
            rs.next();
            rs.getString(1);
            return true;
        } catch (SQLException | RuntimeException throwables) {
            return false;
        }
    }

    public String[] getUserInfo(String TC){
        try {
            PreparedStatement statement = connection.prepareStatement("select F_Name,L_Name,IBAN,amount from users join accounts on users.TC = ?");
            statement.setString(1,TC);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return new String[]{rs.getString("F_Name"),
            rs.getString("L_Name"),
            rs.getString("IBAN"),
            String.valueOf(rs.getInt("amount"))};

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


}
