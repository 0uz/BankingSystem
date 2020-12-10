package com;

import javafx.scene.control.Label;


public class TransactionAccountController {
    public Label accIBAN;
    public Label accCurrency;
    public Label accAmount;
    String accIBANN,accCurencyy,accAmountt;



    public void initialize(){
        accIBAN.setText(accIBANN);
        accCurrency.setText(accCurencyy);
        accAmount.setText(accAmountt);
    }

    public TransactionAccountController(String accIBANN, String accCurencyy, String accAmountt) {
        this.accIBANN = accIBANN;
        this.accCurencyy = accCurencyy;
        this.accAmountt = accAmountt;
    }
}
