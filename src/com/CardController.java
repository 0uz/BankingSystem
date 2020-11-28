package com;

import javafx.scene.control.Label;

public class CardController {
    public Label accountID;
    String a;

    public CardController(String accountID) {
        a=accountID;
    }

    public void  initialize(){
        accountID.setText(a);
    }
}
