package com;

import com.util.DatabaseLayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

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
    public ImageView acc_img;
    public ImageView trans_img;
    public ImageView credit_img;
    public ImageView settings_img;
    public TextField recevIBAN;
    public TextField recevNameSurname;
    public Button sendButton;
    public TextField recevAmount;
    DatabaseLayer layer = new DatabaseLayer();

    public String currentUserTC;

    public void initialize() {
        accountPage.setVisible(false);
        transactionPage.setVisible(false);
        settingsPage.setVisible(false);
        StaticMethod.imageLoader(acc_img,"images/account.png");
        StaticMethod.imageLoader(trans_img,"images/transaction.png");
        StaticMethod.imageLoader(credit_img,"images/credit.png");
        StaticMethod.imageLoader(settings_img,"images/settings.png");
        if(currentUserTC!=null){
            fillMainAccountInfo(); //%100 kullandi
        }
       // listAccounts();
    }


    public void setCurrentUserTC(String currentUserTC) {
        this.currentUserTC = currentUserTC;
        initialize();
    }

    public void passScreenHandle(boolean account, boolean trans, boolean settings){
        accountPage.setVisible(account);
        transactionPage.setVisible(trans);
        settingsPage.setVisible(settings);
        System.out.println(StaticMethod.API("EUR","TRY"));
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
        layer.closeConnection();
    }

    public void minimizeButtonAction(){
        Stage stage = (Stage) accountPage.getScene().getWindow();
        stage.setIconified(true);
    }



    public void addAccountHandle() throws IOException {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("view/NewAccount.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(accountPage.getScene().getWindow());

        NewAccountController controller = loader.getController();
        controller.setCurrentUserData(currentUserTC);

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

    public void SendButton() {

        DatabaseLayer IBANcheck=new DatabaseLayer();
        recevNameSurname.getText();
        recevIBAN.getText();
        recevAmount.getText();
        //listener
        boolean check=IBANcheck.IBANConflictControl( recevIBAN.getText());


        if(check=!true){


        }





    }


    void listAccounts(){
        List<String[]> data = layer.getAccountData(currentUserTC);
        for (int i = 0 ; i <data.size()-1;i++){
            AccountViewController control = new AccountViewController(data.get(i)[0],data.get(i)[1]);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/AccountVi ew.fxml"));
            loader.setController(control);
            try {
                accountVBox.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //TODO last account add with fxml

    }
}

