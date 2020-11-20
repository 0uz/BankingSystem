package com;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainScreenController {

    public AnchorPane accountPage;
    public AnchorPane transactionPage;
    public AnchorPane settingsPage;

    public void initialize(){
        accountPage.setVisible(false);
        transactionPage.setVisible(false);
        settingsPage.setVisible(false);
    }

    public void accountsButtonAction(){
        accountPage.setVisible(true);
        transactionPage.setVisible(false);
        settingsPage.setVisible(false);
    }

    public void transactionButtonAction(){
        accountPage.setVisible(false);
        transactionPage.setVisible(true);
        settingsPage.setVisible(false);
    }

    public void settingsButtonAction(){
        accountPage.setVisible(false);
        transactionPage.setVisible(false);
        settingsPage.setVisible(true);
    }

    public void exitButtonAction(){
        System.exit(1);
    }

    public void minimizeButtonAction(){
        Stage stage = (Stage) accountPage.getScene().getWindow();
        stage.setIconified(true);
    }
}

