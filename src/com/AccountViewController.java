package com;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class AccountViewController {
    public Label IBANLabel;
    public Label MoneyLabel;
    public Button accountButton;
    public ImageView currencyImage;
    String money;
    String IBAN;
    String currency;
    boolean middle;

    public AccountViewController(String IBAN,String money,boolean middle,String currency) {
        this.money = money;
        this.IBAN = IBAN;
        this.middle = middle;
        this.currency=currency;
    }

    void setCSS(){
        if (middle){
            StaticMethod.addCSS(accountButton,"com/view/css/mainsc.css","middleAcc");
        }else{
            StaticMethod.addCSS(accountButton,"com/view/css/mainsc.css","lastAcc");
        }
    }

    void setCurrencyImage() {
        if(currency.equals("TL")){
            StaticMethod.imageLoader(currencyImage,"images/turkish-lira.png");
        }else if(currency.equals("Dollar")){
            StaticMethod.imageLoader(currencyImage,"images/dollar.png");
        }else if(currency.equals("Euro")){
            StaticMethod.imageLoader(currencyImage,"images/euro.png");
        }else{
            StaticMethod.imageLoader(currencyImage,"images/gold.png");
        }
    }

    public void  initialize(){
        IBANLabel.setText("IBAN: " + IBAN);
        MoneyLabel.setText("Money: "+ money);
        accountButton();
        setCSS();
        setCurrencyImage();
    }

    public void accountButton(){
        accountButton.setOnAction(actionEvent -> {
            System.out.println(IBAN);
        });
    }
}
