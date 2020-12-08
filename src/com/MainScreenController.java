package com;

import com.util.DatabaseLayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.validator.routines.EmailValidator;

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
    public TextField currentPasswordTF;
    public TextField newPasswordTF;
    public Label currentMailLabel;
    public Label currentPnoLabel;
    public Label currentAddressLabel;
    public TextArea newAddressTF;
    public TextField newMailTF;
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
        listAccounts();
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
        StaticMethod.imageLoader(currencyIV,"images/turkish-lira.png");
        currentMailLabel.setText(infos[4]);
        currentAddressLabel.setText(infos[5]);
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


    void listAccounts()  {
        List<String[]> data = layer.getAccountData(currentUserTC);
        System.out.println(currentUserTC);
        for (int i = 0 ; i <data.size();i++){
            AccountViewController control;
            if (i==data.size()-1){
                control = new AccountViewController(data.get(i)[0],data.get(i)[1],false,data.get(i)[2]);
            }else{
                control = new AccountViewController(data.get(i)[0],data.get(i)[1],true,data.get(i)[2]);
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/AccountView.fxml"));
            loader.setController(control);
            try {
                accountVBox.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void changePasswordHandle(){
        if(layer.loginUserControl(Double.parseDouble(currentUserTC),currentPasswordTF.getText())){
            if(StaticMethod.lengthController(newPasswordTF,newPasswordTF.getText(),30,5)){
                layer.updatePassword(Double.parseDouble(currentUserTC),newPasswordTF.getText());
                StaticMethod.addCSS(currentPasswordTF,"com/view/css/mainsc.css","notError");
            }
            else{
                StaticMethod.addCSS(currentPasswordTF,"com/view/css/mainsc.css","error");
            }
            StaticMethod.addCSS(currentPasswordTF,"com/view/css/mainsc.css","notError");
        }
        else{
            StaticMethod.addCSS(currentPasswordTF,"com/view/css/mainsc.css","error");
        }
    }

    public void changeAddressHandle() {
        if (StaticMethod.lengthController(newAddressTF,newAddressTF.getText(),70,5)) {
            layer.updateAddress(Double.parseDouble(currentUserTC), newAddressTF.getText());
            StaticMethod.addCSS(newAddressTF, "com/view/css/mainsc.css", "notError");
        }
        else {
            StaticMethod.addCSS(newAddressTF, "com/view/css/mainsc.css", "error");
        }
    }

    public void changeMailHandle(){
        if(EmailValidator.getInstance().isValid(newMailTF.getText())){
            layer.updateMail(Double.parseDouble(currentUserTC),newMailTF.getText());
            StaticMethod.addCSS(newMailTF,"com/view/css/mainsc.css", "notError");
        }
        else {
            StaticMethod.addCSS(newMailTF,"com/view/css/mainsc.css", "error");
        }
    }

}

