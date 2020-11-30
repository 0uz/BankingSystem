package com;

import com.util.DatabaseLayer;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;

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
    public Button registerButton;

    private boolean disableButton[] ={false,false,false,false,false,false,false};
    DatabaseLayer layer = new DatabaseLayer();

    public void initialize(){
        LoginScreenController.imageLoader(logo,"images/logo.png");
        lengthListeners();
        emailTFListener();
        BDateController();
        makeDisableButton();

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

    static boolean lengthController(Control node , String t1,int max,int min){
        if (t1.length()<=max && t1.length()>=min){
            addCSS(node,"com/view/css/mainsc.css","notError");
            return true;
        }else if (t1.length() == 0){
            removeCSS(node);
            return false;
        }else{
            addCSS(node,"com/view/css/mainsc.css","error");
            return false;
        }
    }

    public static boolean isInteger(String s) {
        try {
            Long.parseLong(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    void makeDisableButton(){
        for (boolean a : disableButton){
            if(!a){
                registerButton.setDisable(true);
                break;
            }else{
                registerButton.setDisable(false);
            }
        }
    }

    void lengthListeners(){
        FNameTF.textProperty().addListener((observableValue, s,t1) ->{
            disableButton[0] = lengthController(FNameTF,t1,20,3);
            makeDisableButton();
        } );

        LNameTF.textProperty().addListener((observableValue, s,t1) ->{
            disableButton[1] = lengthController(LNameTF,t1,20,3);
            makeDisableButton();
        } );

        passwordPF.textProperty().addListener((observableValue, s, t1) -> {
            disableButton[2] = lengthController(passwordPF,t1,30,5);
            makeDisableButton();
        });

        addressTF.textProperty().addListener((observableValue, s, t1) ->{
            disableButton[3] = lengthController(addressTF,t1,50,5);
            makeDisableButton();
        } );

        TCTF.textProperty().addListener((observableValue, s, t1) -> {
            if (lengthController(TCTF,t1,11,11)){
                if(isInteger(t1)){
                    addCSS(TCTF,"com/view/css/mainsc.css","notError");
                    disableButton[4]= true;
                }else{
                    addCSS(TCTF,"com/view/css/mainsc.css","error");
                    disableButton[4] = false;
                }
            }
            makeDisableButton();
        });
    }


    void emailTFListener(){
        mailTF.textProperty().addListener((observableValue, s, t1) -> {
            if (EmailValidator.getInstance().isValid(t1)){
                addCSS(mailTF,"com/view/css/mainsc.css","notError");
                disableButton[5] = true;
            }else if(t1.length()==0){
                removeCSS(mailTF);
                disableButton[5] = false;
            }else{
                addCSS(mailTF,"com/view/css/mainsc.css","error");
                disableButton[5] = false;
            }
            makeDisableButton();
        });

    }

    void BDateController(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        BDateDP.valueProperty().addListener((observableValue, localDate, t1) -> {
            int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
            int BDateYear = Integer.parseInt(formatter.format(t1));
            if (currentYear-BDateYear < 18){
                BDateDP.setStyle("-fx-border-color: red");
                disableButton[6] = false;
            }else{
                BDateDP.setStyle("-fx-border-color: #419A1C");
                disableButton[6] = true;
            }
            makeDisableButton();
        });
    }

    public void registerButtonHandle(){
        java.sql.Date gettedDate = java.sql.Date.valueOf(BDateDP.getValue());
        layer.insertUser(FNameTF.getText(),
                LNameTF.getText(),
                Float.parseFloat(TCTF.getText()),
                mailTF.getText(),
                passwordPF.getText(),
                gettedDate,
                addressTF.getText());
    }





    public void exitButton(){
        System.exit(1);
    }

    public void minimizeButton(){
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }
}
