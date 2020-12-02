package com;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AccountViewController {
    public Label accountID;
    public Button accountButton;
    String a;

    public AccountViewController(String accountID) {
        a=accountID;
    }

    public void  initialize(){
        accountID.setText(a);
        accountButton();
    }

    public void accountButton(){
        accountButton.setOnAction(actionEvent -> {
            if (accountButton.getHeight()==30){
                accountButton.setMinHeight(100);
            }else{
                accountButton.setMinHeight(30);
            }
            System.out.println(a);
        });
    }

    public String getA() {
        return a;
    }
}
