package com;

import com.util.DatabaseLayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class MainScreenController {

    public AnchorPane accountPage;
    public AnchorPane transactionPage;
    public AnchorPane settingsPage;
    public VBox accountVBox;

    public void initialize() {
        accountPage.setVisible(false);
        transactionPage.setVisible(false);
        settingsPage.setVisible(false);
        DatabaseLayer layer = new DatabaseLayer();
    }

    public void passScreenHandle(boolean account,boolean trans,boolean settings){
        accountPage.setVisible(account);
        transactionPage.setVisible(trans);
        settingsPage.setVisible(settings);
    }
    public void accountsButtonAction(){
        passScreenHandle(true,false,false);
    }

    public void transactionButtonAction(){
        passScreenHandle(false,true,false);
    }

    public void settingsButtonAction(){
        passScreenHandle(false,false,true);
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
        AccountViewController control = new AccountViewController("Hesap"+String.valueOf(accountNum));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/AccountView.fxml"));
        loader.setController(control);
        if(accountNum<=5) {
            accountVBox.getChildren().add(loader.load());
        }
        accountNum++;


    }
}

