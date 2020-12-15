package com;

import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class TransactionAccountController {
    public Label accIBAN;
    public Label accAmount;
    public Label accCurrency;
    public Button accButton;
    String accIBANN,accAmountt,accCurencyy;



    public void initialize(){
        accIBAN.setText(accIBANN);
        accAmount.setText(accAmountt);
        accCurrency.setText(accCurencyy);
    }

    public TransactionAccountController(String accIBANN, String accAmountt, String accCurencyy) {
        this.accIBANN = accIBANN;
        this.accAmountt = accAmountt;
        this.accCurencyy = accCurencyy;
    }


}
