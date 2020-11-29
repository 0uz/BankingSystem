package com;

import com.util.DatabaseLayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {

    public AnchorPane accountPage;
    public AnchorPane transactionPage;
    public AnchorPane settingsPage;
    public GridPane accountGridPane;

    public void initialize() {
        accountPage.setVisible(false);
        transactionPage.setVisible(false);
        settingsPage.setVisible(false);
        DatabaseLayer layer = new DatabaseLayer();
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

    int accountNum = 1;
    public void addAccountHandle() throws IOException {
        AccountViewController control = new AccountViewController("Hesap "+(Integer.toString(accountNum)));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/AccountView.fxml"));
        loader.setController(control);
        if(accountNum <=3){
            accountGridPane.add(loader.load(),(accountNum%4)-1,0);
        }else if(accountNum<=6){
            accountGridPane.add(loader.load(),(accountNum%4),1);
        }else{

        }
        accountNum++;


    }
}

