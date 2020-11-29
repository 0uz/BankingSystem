package com;

import javafx.scene.control.Label;

public class AccountViewController {
    public Label accountID;
    String a;

    public AccountViewController(String accountID) {
        a=accountID;
    }

    public void  initialize(){
        accountID.setText(a);
    }
}
