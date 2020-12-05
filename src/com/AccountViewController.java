package com;


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
            System.out.println(a);
        });
    }

    public String getA() {
        return a;
    }
}
