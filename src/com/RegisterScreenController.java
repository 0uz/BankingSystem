package com;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class RegisterScreenController {

    public Button minimizeButton;
    public TextField FNameTF;
    public TextField LNameTF;
    public TextField TCTF;
    public TextField mailTF;
    public PasswordField passwordPF;
    public DatePicker BDateDP;
    public TextField addressTF;
    public ImageView logo;

    public void initialize(){
        LoginScreenController.imageLoader(logo,"images/logo.png");
        lengthListeners();
        emailTFListener();
        BDateController();
    }

    static void addCSS (Control node,String path, String className){
            node.getStyleClass().clear();
            node.getStyleClass().addAll("text-field", "text-input");
            node.getStylesheets().add(path);
            node.getStyleClass().add(className);
    }

    static void removeCSS(Control node){
        node.getStyleClass().clear();
        node.getStyleClass().addAll("text-field", "text-input");
    }

    static void lengthController(Control node , String t1,int max,int min){
        if (t1.length()<=max && t1.length()>=min){
            addCSS(node,"com/view/css/mainsc.css","notError");
        }else if (t1.length() == 0){
            removeCSS(node);
        }else{
            addCSS(node,"com/view/css/mainsc.css","error");
        }
    }

    void lengthListeners(){
        FNameTF.textProperty().addListener((observableValue, s,t1) ->{
            lengthController(FNameTF,t1,20,3);
        } );

        LNameTF.textProperty().addListener((observableValue, s,t1) ->{
            lengthController(LNameTF,t1,20,3);
        } );

        passwordPF.textProperty().addListener((observableValue, s, t1) -> {
            lengthController(passwordPF,t1,30,5);
        });

        addressTF.textProperty().addListener((observableValue, s, t1) ->{
            lengthController(addressTF,t1,50,5);
        } );
        TCTF.textProperty().addListener((observableValue, s, t1) -> {
            lengthController(TCTF,t1,11,11);
        });
    }


    void emailTFListener(){
        mailTF.textProperty().addListener((observableValue, s, t1) -> {
            if (EmailValidator.getInstance().isValid(t1)){
                addCSS(mailTF,"com/view/css/mainsc.css","notError");
            }else if(t1.length()==0){
                removeCSS(mailTF);
            }else{
                addCSS(mailTF,"com/view/css/mainsc.css","error");
            }
        });


    }

    void BDateController(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        BDateDP.valueProperty().addListener((observableValue, localDate, t1) -> {
            int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
            int BDateYear = Integer.parseInt(formatter.format(t1));
            if (currentYear-BDateYear < 18){
                BDateDP.setStyle("-fx-border-color: red");
            }else{
                BDateDP.setStyle("-fx-border-color: #419A1C");
            }
        });
    }





    public void exitButton(){
        System.exit(1);
    }

    public void minimizeButton(){
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }
}
