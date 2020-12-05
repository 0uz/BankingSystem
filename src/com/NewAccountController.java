package com;

import com.util.DatabaseLayer;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NewAccountController {
    public VBox goldAccountVBox;
    public ComboBox<String> selectCBox;
    public ComboBox<String> currencyCBox;
    public TextField moneyTF;
    public Label dailyEarningLabel;
    public Label yearlyEarningLabel;
    public Button submitButton;
    public Button cancelButton;
    public TextField goldMoneyTF;
    public Label gramGoldLabel;
    public Label boughtGoldLabel;
    public VBox AccountVBox;
    public Label interestLabel;
    public String currentUserTC;

    public void setCurrentUserTC(String currentUser) {
        this.currentUserTC = currentUser;
    }

    DatabaseLayer layer = new DatabaseLayer();
    public void initialize(){
        selectCBox.getItems().addAll("Draw Account","Deposit Account","Gold Account");
        currencyCBox.getItems().addAll("TL","Dollar","Euro");
        currencyCBox.getSelectionModel().select(1);
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
    }

    void addListener(){
        moneyTF.textProperty().addListener((observableValue, s, t1) -> {
            if (s.length() == 0 || t1.length() == 0){
                StaticMethod.addCSS(moneyTF,"com/view/css/mainsc.css","error");
                yearlyEarningLabel.setText("Yearly Earning: ");
                dailyEarningLabel.setText("Daily Earning:");
            }else{
                if (StaticMethod.isDouble(t1)){
                    if (StaticMethod.lengthController(moneyTF,t1,10,0)){
                        makeInterestCalculation(t1);
                    }
                }else{
                    yearlyEarningLabel.setText("Yearly Earning: ");
                    dailyEarningLabel.setText("Daily Earning: ");
                }
            }

        } );

        currencyCBox.valueProperty().addListener((observableValue, o, t1) -> {
            if (StaticMethod.lengthController(moneyTF,moneyTF.getText(),10,0) && moneyTF.getText().length()!=0){
                makeInterestCalculation(moneyTF.getText());
            }
        });


    }
    public void cancelButton(){
        ((Stage) cancelButton.getScene().getWindow()).close();

    }

    public void submitButton(){
        if(selectCBox.getSelectionModel().getSelectedItem().equals("Draw Account")){
            if(moneyTF.getText().length()!=0){
                layer.addNewAccount(Double.parseDouble(currentUserTC),Double.parseDouble(moneyTF.getText()),currencyCBox.getSelectionModel().getSelectedItem(),false);
            }
        }else if(selectCBox.getSelectionModel().getSelectedItem().equals("Deposit Account")){
                layer.addNewAccount(Double.parseDouble(currentUserTC),Double.parseDouble(moneyTF.getText()),currencyCBox.getSelectionModel().getSelectedItem(),true);
        }else{
            layer.addNewAccount(Double.parseDouble(currentUserTC),Double.parseDouble(moneyTF.getText()),currencyCBox.getSelectionModel().getSelectedItem(),false);
        }

    }



}
