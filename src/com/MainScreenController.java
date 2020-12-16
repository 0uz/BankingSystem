package com;

import com.util.DatabaseLayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
import javafx.util.Duration;
import org.apache.commons.validator.routines.EmailValidator;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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
    public Label currentAddressLabel;
    public TextArea newAddressTF;
    public TextField newMailTF;
    public TextField recevIBAN;
    public TextField recevNameSurname;
    public Button sendButton;
    public TextField recevAmount;
    public VBox currencyVBox;
    public TextField baseTF;
    public TextField toTF;
    public Button currencySearchButton;
    public PieChart myMoneyPC;
    public VBox transAccount;
    public Label passwordInf;
    public Label mailInf;
    public Label addressInf;
    public ImageView logo;
    public Label yourIBAN;
    public ImageView newAccountIV;
    public Button refreshButton;
    public ImageView refreshIV;


    DatabaseLayer layer = new DatabaseLayer();
    public String currentUserTC;
    boolean[] searchControl = {false,false};

    public void initialize() {
        accountPage.setVisible(false);
        transactionPage.setVisible(false);
        settingsPage.setVisible(false);
        passwordInf.setText("");
        mailInf.setText("");
        addressInf.setText("");
        currencyVBox.getChildren().addAll(new Label("USD/TRY: "+StaticMethod.API("USD","TRY")),new Label("EUR/TRY: "+StaticMethod.API("EUR","TRY")));
        addListener();
        loadImages();
    }

    void loadImages(){
        StaticMethod.imageLoader(acc_img,"images/account.png");
        StaticMethod.imageLoader(trans_img,"images/transaction.png");
        StaticMethod.imageLoader(credit_img,"images/credit.png");
        StaticMethod.imageLoader(settings_img,"images/settings.png");
        StaticMethod.imageLoader(logo,"images/logo.png");
        StaticMethod.imageLoader(newAccountIV,"images/newAccount.png");
        StaticMethod.imageLoader(refreshIV,"images/refresh.png");
    }

    public void setCurrentUserTC(String currentUserTC) {
        this.currentUserTC = currentUserTC;
        listAccounts();
        fillMainAccountInfo();
        myMoneyPC.getData().addAll(layer.fillPieChart(currentUserTC));
        listTransAccounts();
    }

    public void passScreenHandle(boolean account, boolean trans, boolean settings){
        accountPage.setVisible(account);
        transactionPage.setVisible(trans);
        settingsPage.setVisible(settings);
    }

    void fillMainAccountInfo(){
        String[] infos = layer.getUserInfo(currentUserTC);
        welcomeLabel.setText("Welcome "+infos[0]+" "+infos[1]);
        IBANLabel.setText("IBAN: "+ infos[2]);
        moneyLabel.setText("Money : " + infos[3] + " TL");
        StaticMethod.imageLoader(currencyIV,"images/turkish-lira.png");
        currentMailLabel.setText(infos[4]);
        currentAddressLabel.setText(infos[5]);

        accountButton.setOnAction(actionEvent -> {
            StringSelection selection =  new StringSelection(infos[2]);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection,null);
            IBANLabel.setText("IBAN Copied to Clipboard !");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> IBANLabel.setText("IBAN: " + infos[2])));
            timeline.play();
        });

        accountButton.setOnMouseEntered(mouseEvent -> {
            accountButton.setStyle("-fx-background-color: #1761A0; -fx-border-radius: 20");
            IBANLabel.setTextFill(Color.WHITE);
            moneyLabel.setTextFill(Color.WHITE);
        });

        accountButton.setOnMouseExited(mouseEvent -> {
            accountButton.setStyle("-fx-background-color: #419A1C; -fx-border-radius: 20");
            IBANLabel.setTextFill(Color.BLACK);
            moneyLabel.setTextFill(Color.BLACK);
        });
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

    void addListener(){
        baseTF.textProperty().addListener((observableValue, s, t1) -> {
           searchControl[0]= StaticMethod.lengthController(baseTF,t1,3,3,"text-fieldError","text-field");
           searchExcButDisable();
        });

        toTF.textProperty().addListener((observableValue, s, t1) -> {
            searchControl[1]= StaticMethod.lengthController(toTF,t1,3,3,"text-fieldError","text-field");
            searchExcButDisable();
        });
    }



    public void addAccountHandle() throws IOException {
        if (accountVBox.getChildren().size()<7) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/NewAccount.fxml"));
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
        }
    }
    public void searchExcButDisable(){
        for (boolean i : searchControl){
            if (!i){
                currencySearchButton.setDisable(true);
                return;
            }else{
                currencySearchButton.setDisable(false);
            }
        }
    }

    public void searchExcButton(){
        String search = StaticMethod.API(baseTF.getText().toUpperCase(),toTF.getText().toUpperCase());
        if (search !=null){
            currencyVBox.getChildren().add(new Label(baseTF.getText().toUpperCase()+"/"+toTF.getText().toUpperCase()+": "+search));
        }else{
            baseTF.setText("Wrong unit");
            toTF.setText("Wrong unit");
        }
    }

    public void sendTransactionButton() {
        //TransactionAccountController senderIBAN=new TransactionAccountController() ;
        DatabaseLayer db=new DatabaseLayer();
        recevNameSurname.getText();
        recevIBAN.getText();
        recevAmount.getText();
        yourIBAN.getText();
        if(db.IBANandNSConflictControl(recevIBAN.getText()))
        db.transaction(yourIBAN.getText(),recevIBAN.getText(),Double.parseDouble(recevAmount.getText()));





    }


    void listAccounts()  {
        List<String[]> data = layer.getAccountData(currentUserTC);
        System.out.println(currentUserTC);
        for (int i = 0 ; i <data.size();i++){
            AccountViewController control = new AccountViewController(data.get(i)[0],data.get(i)[1],data.get(i)[2]);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/AccountView.fxml"));
            loader.setController(control);
            try {
                accountVBox.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void refreshButtonHandle(){
        System.out.println(accountVBox.getChildren().size());
        int count = accountVBox.getChildren().size();
        for (int i = 2 ; i < count  ; i++){
            accountVBox.getChildren().remove(2);
        }
        listAccounts();
        myMoneyPC.getData().clear();
        myMoneyPC.getData().addAll(layer.fillPieChart(currentUserTC));
    }
    void listTransAccounts()  {
        List<String[]> accountsData = layer.getAccountDataForTrans(currentUserTC);

        for (String[] accountsDatum : accountsData) {
            TransactionAccountController controller = new TransactionAccountController(accountsDatum[0], accountsDatum[1], accountsDatum[2]);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/TransactionAccount.fxml"));
            loader.setController(controller);
            try {
                transAccount.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }



    public void changePasswordHandle(){
        if(layer.loginUserControl(Double.parseDouble(currentUserTC),currentPasswordTF.getText())){
            if(StaticMethod.lengthController(newPasswordTF,newPasswordTF.getText(),30,5,"error","notError")){
                layer.updatePassword(Double.parseDouble(currentUserTC),newPasswordTF.getText());
                StaticMethod.addCSS(currentPasswordTF,"com/view/css/mainsc.css","notError");
                StaticMethod.addCSS(newPasswordTF,"com/view/css/mainsc.css","notError");
                currentPasswordTF.clear();
                newPasswordTF.clear();
                passwordInf.setText("Successful!");
                passwordInf.setTextFill(Color.web("#419A1C"));
            }
            else{
                StaticMethod.addCSS(newPasswordTF,"com/view/css/mainsc.css","error");
                passwordInf.setText("New password is too long or too short!");
                passwordInf.setTextFill(Color.web("#FF0000"));
            }
            StaticMethod.addCSS(currentPasswordTF,"com/view/css/mainsc.css","notError");
        }
        else{
            StaticMethod.addCSS(currentPasswordTF,"com/view/css/mainsc.css","error");
            passwordInf.setText("Current password is wrong!");
            passwordInf.setTextFill(Color.web("#FF0000"));
        }
    }

    public void changeAddressHandle() {
        if (StaticMethod.lengthController(newAddressTF,newAddressTF.getText(),70,5,"error","notError")) {
            layer.updateAddress(Double.parseDouble(currentUserTC), newAddressTF.getText());
            StaticMethod.addCSS(newAddressTF, "com/view/css/mainsc.css", "notError");
            newAddressTF.clear();
            addressInf.setText("Successful!");
            addressInf.setTextFill(Color.web("#419A1C"));
        }
        else {
            StaticMethod.addCSS(newAddressTF, "com/view/css/mainsc.css", "error");
            addressInf.setText("New address is too long or too short!");
            addressInf.setTextFill(Color.web("#FF0000"));
        }
    }

    public void changeMailHandle(){
        if(EmailValidator.getInstance().isValid(newMailTF.getText())){
            layer.updateMail(Double.parseDouble(currentUserTC),newMailTF.getText());
            StaticMethod.addCSS(newMailTF,"com/view/css/mainsc.css", "notError");
            newMailTF.clear();
            mailInf.setText("Successful!");
            mailInf.setTextFill(Color.web("#419A1C"));
        }
        else {
            StaticMethod.addCSS(newMailTF,"com/view/css/mainsc.css", "error");
            mailInf.setText("Enter a valid mail!!");
            mailInf.setTextFill(Color.web("#FF0000"));

        }
    }



}

