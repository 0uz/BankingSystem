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

    public AccountViewController(String IBAN,String money,String currency) {
        this.money = money;
        this.IBAN = IBAN;
        this.currency=currency;
    }

    void setCSS(){
        StaticMethod.addCSS(accountButton,"com/view/css/mainsc.css","accView");
    }

    void setCurrencyImage() {
        switch (currency) {
            case "TL" -> StaticMethod.imageLoader(currencyImage, "images/turkish-lira.png");
            case "Dollar" -> StaticMethod.imageLoader(currencyImage, "images/dollar.png");
            case "Euro" -> StaticMethod.imageLoader(currencyImage, "images/euro.png");
            default -> StaticMethod.imageLoader(currencyImage, "images/gold.png");
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
