package com;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

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

    void setCurrencyImage() {
        if (currency.equals("TL")){
            StaticMethod.imageLoader(currencyImage, "images/turkish-lira.png");
        }
        if (currency.equals("Dollar")){
            StaticMethod.imageLoader(currencyImage, "images/dollar.png");
        }
        if (currency.equals("Euro")){
            StaticMethod.imageLoader(currencyImage, "images/euro.png");
        }
        if (currency.equals("Euro")){
            StaticMethod.imageLoader(currencyImage, "images/euro.png");
        }
        if (currency.equals("Gold")){
            StaticMethod.imageLoader(currencyImage, "images/gold.png");
        }

    }

    public void  initialize(){
        IBANLabel.setText("IBAN: " + IBAN);
        MoneyLabel.setText("Money: "+ money + " " +currency);
        IBANLabel.setTextFill(Color.BLACK);
        MoneyLabel.setTextFill(Color.BLACK);
        accountButton();
        setCurrencyImage();
    }

    public void accountButton(){
        accountButton.setOnAction(actionEvent -> {
            StringSelection selection =  new StringSelection(IBAN);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection,null);
            IBANLabel.setText("IBAN Copied to Clipboard !");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e ->{
                IBANLabel.setText("IBAN: " + IBAN);
            }));
            timeline.play();
        });

        accountButton.setOnMouseEntered(mouseEvent -> {
            accountButton.setStyle("-fx-background-color: #1761A0");
            IBANLabel.setTextFill(Color.WHITE);
            MoneyLabel.setTextFill(Color.WHITE);
        });

        accountButton.setOnMouseExited(mouseEvent -> {
            accountButton.setStyle("-fx-background-color: #419A1C");
            IBANLabel.setTextFill(Color.BLACK);
            MoneyLabel.setTextFill(Color.BLACK);
        });
    }

}
