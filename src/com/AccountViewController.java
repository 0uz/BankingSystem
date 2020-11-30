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
            if (accountButton.getHeight()==200){
                accountButton.setMinHeight(102);
            }else{
                accountButton.setMinHeight(200);
            }
            accountButton.toFront();
            System.out.println("sex");
        });
    }
}
