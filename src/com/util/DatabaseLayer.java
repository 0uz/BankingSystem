package com.util;
import com.StaticMethod;
import javafx.scene.control.Alert;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;


public class DatabaseLayer {
    private static final String url = "jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2380411";
    private static final String username = "sql2380411";
    private static final String password = "aB9%mX1*";
    Connection connection;
    java.sql.Timestamp currentDate = new java.sql.Timestamp(new java.util.Date().getTime());
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
        String userTable = "CREATE TABLE IF NOT EXISTS sql2380411.users(\n" +
                "    F_Name VARCHAR(20),\n" +
                "    L_Name VARCHAR(20),\n" +
                "    TC BIGINT(11) PRIMARY KEY,\n" +
                "    address VARCHAR(100),\n" +
                "    mail VARCHAR(320),\n" +
                "    password VARCHAR(50),\n" +
                "    B_Date DATE,\n" +
                "    admin_F BOOL default false\n" +
                ");";

        String accountTable = "CREATE TABLE IF NOT EXISTS sql2380411.accounts(\n" +
                "    TC bigint(11) NOT NULL,\n" +
                "    IBAN VARCHAR(26) PRIMARY KEY,\n" +
                "    amount decimal(28,3)default 0,\n" +
                "    currency varchar(30),\n" +
                "    mainAccF bool default false,\n" +
                "    openDate date,\n" +
                "    interest int default 15,\n" +
                "    depositAccF bool default false,\n" +
                "    goldGram decimal(15,3) default 0,\n" +
                "    foreign key (TC) REFERENCES users(TC)\n" +
                ");";

        String transactionTable = "CREATE TABLE IF NOT EXISTS sql2380411.transactions(\n" +
                "    senderIBAN VARCHAR(23) NOT NULL,\n" +
                "    receiverIBAN VARCHAR(23),\n" +
                "    amount INTEGER,\n" +
                "    T_date date,\n" +
                "    isRead bool default false,\n" +
                "    foreign key (senderIBAN) REFERENCES sql2380411.accounts(IBAN)\n" +
                ")";

        String creditTable = "CREATE TABLE IF NOT EXISTS sql2380411.credits(\n" +
                "    TC bigint(11) not null,\n" +
                "    amount int,\n" +
                "     creditMonths tinyint,\n" +
                "     interest decimal(6,4),\n" +
                "     getCreditDate date,\n" +
                "     confirmation bool default false,\n" +
                "     foreign key (TC) REFERENCES sql2380411.users(TC)\n" +
                ")";

        try {
                Statement statement = connection.createStatement();
                        statement.execute(userTable);
                        statement.execute(accountTable);
                        statement.execute(transactionTable);
                        statement.execute(creditTable);
        }catch (SQLException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Error");
            alert.setContentText("Database connection error!\nPlease check your connection.");
            alert.showAndWait();
            System.exit(1);
        }



    }

    public boolean insertUser(String FName, String LName, Double TC, String eMail, String password, Date BDate, String address, String IBAN, Double moneyAmount){
        try {
            PreparedStatement statement = connection.prepareStatement("insert into users (F_Name, L_Name, TC, address, mail, password, B_Date) VALUES (?,?,?,?,?,?,?)");
            PreparedStatement statement2 = connection.prepareStatement("insert into accounts (TC,IBAN,amount,openDate,mainAccF) value (?,?,?,?,?)");
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
            statement2.setTimestamp(4,currentDate);
            statement2.setBoolean(5,true);
            statement.execute();
            statement2.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }


    }

    public Double getMainMoney(String TC){
        try {
            PreparedStatement statement =  connection.prepareStatement("select amount from accounts where TC = ? and mainAccF = true");
            statement.setString(1,TC);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getDouble("amount");
        } catch (SQLException | RuntimeException throwables) {
            throwables.printStackTrace();
            return 0.0;
        }
    }
    public boolean loginUserControl(Double TC , String password){
        try {
            PreparedStatement statement =  connection.prepareStatement("select password,TC from users where password = ? AND TC = ?");
            statement.setString(1,password);
            statement.setDouble(2,TC);
            ResultSet rs = statement.executeQuery();
            rs.next();
            System.out.println(rs.getString(2));
            return true;
        } catch (SQLException | RuntimeException throwables) {
            return false;
        }

    }

    public boolean updatePassword(Double TC, String password){
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE users set password = ? where TC = ?");
            statement.setString(1,password);
            statement.setDouble(2,TC);
            statement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
            PreparedStatement statement1 = connection.prepareStatement("select F_Name,L_Name,IBAN,amount from accounts,users where users.TC=accounts.TC and mainAccF = true and users.TC = ?");
            statement1.setString(1,TC);
            ResultSet rs = statement1.executeQuery();
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

    public List<String[]> getAccountData(String TC){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT IBAN,amount,currency from accounts where TC = ? AND mainAccF = false");
            statement.setString(1,TC);
            ResultSet rs = statement.executeQuery();
            List<String[]> data = null;
            while (rs.next()){
                data.add(new String[]{rs.getString("IBAN"),
                        String.valueOf(rs.getInt("amount")),
                        rs.getString("currency")
                });
            }
            return data;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }


    }

    public boolean addNewAccount(Double TC,Double amount,String currency,boolean deposit){
        try {
            PreparedStatement statement1 = connection.prepareStatement("insert into accounts (TC,IBAN,amount,currency,depositAccF,openDate) value (?,?,?,?,?,?)");
            PreparedStatement statement2 = connection.prepareStatement("UPDATE accounts set amount = amount - ? where TC = ? AND mainAccF = true ");
            statement1.setDouble(1,TC);
            statement1.setString(2, StaticMethod.IBANCalculator());
            statement1.setDouble(3,amount);
            statement1.setString(4,currency);
            statement1.setBoolean(5,deposit);
            statement1.setTimestamp(6,currentDate);
            statement2.setDouble(1,amount);
            statement2.setDouble(2,TC);
            statement1.execute();
            statement2.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }



    public boolean addNewGoldAccount(Double TC, double money){
        try {
            PreparedStatement statement1 = connection.prepareStatement("insert into accounts (TC,IBAN,currency,goldGram,openDate) value (?,?,?,?,?)");
            PreparedStatement statement2 = connection.prepareStatement("UPDATE accounts set amount = amount - ? where TC = ? AND mainAccF = true ");
            BigDecimal gram = new BigDecimal(money/460);
            statement1.setDouble(1,TC);
            statement1.setString(2, StaticMethod.IBANCalculator());
            statement1.setString(3,"Gold");
            statement1.setBigDecimal(4, gram);
            statement1.setTimestamp(5,currentDate);
            statement2.setDouble(1,money);
            statement2.setDouble(2,TC);
            statement1.execute();
            statement2.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




}
