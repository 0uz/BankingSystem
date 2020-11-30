package com;

import com.util.DatabaseLayer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {

    public AnchorPane accountPage;
    public AnchorPane transactionPage;
    public AnchorPane settingsPage;
    public HBox accountHBox;
    public HBox accountHBox2;

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
        AccountViewController control = new AccountViewController("Hesap asdasdasqweewqqweqweqwedasdaasdd");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/AccountView.fxml"));
        loader.setController(control);
        if(accountNum <=3){
            accountHBox.getChildren().add(loader.load());
        }else if(accountNum<=6){
            accountHBox2.getChildren().add(loader.load());

        }else{

        }

        accountNum++;


    }
}

