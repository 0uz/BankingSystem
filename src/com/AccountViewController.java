package com;


import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AccountViewController {
    public Label IBANLabel;
    public Label MoneyLabel;
    public Button accountButton;
    String money;
    String IBAN;

    public AccountViewController(String IBAN,String money) {
        this.IBAN=IBAN;
        this.money = money;
    }

    public void  initialize(){
        IBANLabel.setText("IBAN: " + IBAN);
        MoneyLabel.setText("Money: "+ money);
        accountButton();
    }

    public void accountButton(){
        accountButton.setOnAction(actionEvent -> {
            System.out.println(IBAN);
        });
    }
}
