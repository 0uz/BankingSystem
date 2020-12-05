package com;

import com.util.DatabaseLayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class MainScreenController {

    public AnchorPane accountPage;
    public AnchorPane transactionPage;
    public AnchorPane settingsPage;
    public VBox accountVBox;
    public Label moneyLabel;
    public Label IBANLabel;
    public ImageView currencyIV;
    public Button accountButton;
    public Button addAccountButton;
    public Label welcomeLabel;
    DatabaseLayer layer = new DatabaseLayer();

    public String currentUserTC;

    public void initialize() {
        accountPage.setVisible(false);
        transactionPage.setVisible(false);
        settingsPage.setVisible(false);
        if(currentUserTC!=null){
            fillMainAccountInfo(); //%100 kullandi
        }
    }


    public void setCurrentUserTC(String currentUserTC) {
        this.currentUserTC = currentUserTC;
        initialize();
    }

    public void passScreenHandle(boolean account, boolean trans, boolean settings){
        accountPage.setVisible(account);
        transactionPage.setVisible(trans);
        settingsPage.setVisible(settings);
    }
    void fillMainAccountInfo(){
        String[] infos = layer.getUserInfo(currentUserTC);
        welcomeLabel.setText("Welcome "+infos[0]+" "+infos[1]);
        moneyLabel.setText("IBAN: "+ infos[2]);
        IBANLabel.setText("Money : " + infos[3] + " TL");
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



    public void addAccountHandle() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/NewAccount.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(accountPage.getScene().getWindow());
        stage.show();

        /*
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        AccountViewController control = new AccountViewController("Hesap"+String.valueOf(accountNum));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/AccountView.fxml"));
        loader.setController(control);

        if(accountNum<=5) {
            accountVBox.getChildren().add(loader.load());
        }
        accountNum++;
        */

    }
}

