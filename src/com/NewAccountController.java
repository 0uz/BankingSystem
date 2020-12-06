package com;

import com.util.DatabaseLayer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NewAccountController{
    public VBox goldAccountVBox;
    public ComboBox<String> selectCBox;
    public ComboBox<String> currencyCBox;
    public TextField moneyTF;
    public Label dailyEarningLabel;
    public Label yearlyEarningLabel;
    public Button submitButton;
    public Button cancelButton;
    public TextField goldMoneyTF;
    public Label boughtGoldLabel;
    public VBox AccountVBox;
    public Label interestLabel;
    public static String currentUserTC;
    static Double currentUserMoney;
    public Label yourMoney;
    DatabaseLayer layer = new DatabaseLayer();


    public void setCurrentUserData(String currentUser) {
        currentUserTC = currentUser;
        currentUserMoney = layer.getMainMoney(currentUser);
        yourMoney.setText("Your Money: "+ currentUserMoney);
    }


    public void initialize(){
        selectCBox.getItems().addAll("Draw Account","Deposit Account","Gold Account");
        currencyCBox.getItems().addAll("TL","Dollar","Euro");
        currencyCBox.getSelectionModel().select(0);
        addComboBoxListener();
        addListener();

    }


    void addComboBoxListener(){
        selectCBox.valueProperty().addListener((observableValue, o, t1) -> {
            if (t1.equals("Draw Account")){
                interestLabel.setVisible(false);
                AccountVBox.setVisible(true);
                goldAccountVBox.setVisible(false);
                dailyEarningLabel.setVisible(false);
                yearlyEarningLabel.setVisible(false);
            }else if(t1.equals("Deposit Account")){
                interestLabel.setVisible(true);
                AccountVBox.setVisible(true);
                goldAccountVBox.setVisible(false);
                dailyEarningLabel.setVisible(true);
                yearlyEarningLabel.setVisible(true);
            }else{
                goldAccountVBox.setVisible(true);
                AccountVBox.setVisible(false);
            }
        });

    }

    void makeInterestCalculation(String money){
        NumberFormat format = new DecimalFormat("#0.00");
        yearlyEarningLabel.setText("Yearly Earning: "+(format.format((Double.parseDouble(money)*15)/100))+ " " +currencyCBox.getSelectionModel().getSelectedItem());
        dailyEarningLabel.setText("Daily Earning: " +(format.format((Double.parseDouble(money)*15)/36500))+" " +currencyCBox.getSelectionModel().getSelectedItem());
        boughtGoldLabel.setText("Bought Gold: "+ format.format(Double.parseDouble(money)/460) + " gram");
    }

    void addListener(){
        moneyTF.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.length() == 0){
                StaticMethod.addCSS(moneyTF,"com/view/css/mainsc.css","error");
                yearlyEarningLabel.setText("Yearly Earning: ");
                dailyEarningLabel.setText("Daily Earning:");
                yourMoney.setText("Your Money:"+currentUserMoney);
            }else{
                if (StaticMethod.isDouble(t1)){
                    if (StaticMethod.lengthController(moneyTF,t1,10,0)){
                        makeInterestCalculation(t1);
                        yourMoney.setText("Your Money:" + (currentUserMoney-Integer.parseInt(t1)));
                        System.out.println(StaticMethod.makeExcCalc(currencyCBox.getSelectionModel().getSelectedItem(),Double.parseDouble(moneyTF.getText())));
                    }
                }else{
                    yearlyEarningLabel.setText("Yearly Earning: ");
                    dailyEarningLabel.setText("Daily Earning: ");
                    yourMoney.setText("Your Money:"+currentUserMoney);
                }
            }

        } );

        goldMoneyTF.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.length() == 0){
                StaticMethod.addCSS(goldMoneyTF,"com/view/css/mainsc.css","error");
                boughtGoldLabel.setText("Bought Gold: ");
                yourMoney.setText("Your Money:"+currentUserMoney);
            }else{
                if (StaticMethod.isDouble(t1)){
                    if (StaticMethod.lengthController(goldMoneyTF,t1,10,0)){
                        makeInterestCalculation(t1);
                        yourMoney.setText("Your Money:" + (currentUserMoney-Double.parseDouble(t1)));
                    }
                }else{
                    boughtGoldLabel.setText("Bought Gold: ");
                    yourMoney.setText("Your Money:"+currentUserMoney);
                }
            }
        });

        currencyCBox.valueProperty().addListener((observableValue, o, t1) -> {
            if (StaticMethod.lengthController(moneyTF,moneyTF.getText(),10,0) && moneyTF.getText().length()!=0 && StaticMethod.isDouble(moneyTF.getText())){
                makeInterestCalculation(moneyTF.getText());
            }

        });


    }
    public void cancelButton(){
        ((Stage) cancelButton.getScene().getWindow()).close();

    }

    public void submitButton(){

        if(selectCBox.getSelectionModel().getSelectedItem().equals("Draw Account")){
            if(moneyTF.getText().length()!=0 && Double.parseDouble(moneyTF.getText()) <= currentUserMoney){
                layer.addNewAccount(Double.parseDouble(currentUserTC),Double.parseDouble(moneyTF.getText()),currencyCBox.getSelectionModel().getSelectedItem(),false);
                ((Stage) cancelButton.getScene().getWindow()).close();
            }else{
                StaticMethod.addCSS(moneyTF,"com/view/css/mainsc.css","error");
            }
        }else if(selectCBox.getSelectionModel().getSelectedItem().equals("Deposit Account")){
            if (moneyTF.getText().length()!=0 && Double.parseDouble(moneyTF.getText()) <= currentUserMoney){
                layer.addNewAccount(Double.parseDouble(currentUserTC),Double.parseDouble(moneyTF.getText()),currencyCBox.getSelectionModel().getSelectedItem(),true);
                ((Stage) cancelButton.getScene().getWindow()).close();
            }else{
                StaticMethod.addCSS(moneyTF,"com/view/css/mainsc.css","error");
            }
        }else if(selectCBox.getSelectionModel().getSelectedItem().equals("Gold Account")){
            if (goldMoneyTF.getText().length()!=0 && Double.parseDouble(moneyTF.getText()) <= currentUserMoney){
                layer.addNewGoldAccount(Double.parseDouble(currentUserTC),Double.parseDouble(goldMoneyTF.getText()));
                ((Stage) cancelButton.getScene().getWindow()).close();
            }else{
                StaticMethod.addCSS(goldMoneyTF,"com/view/css/mainsc.css","error");
            }
        }

    }



}
