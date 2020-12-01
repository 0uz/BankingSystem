package com;

import com.util.DatabaseLayer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class LoginScreenController {
    public Button exitButton;
    public Button minimizeButton;
    public ImageView logo;
    public PasswordField passwordPF;
    public TextField TCTF;
    public Label loginWrongL;
    DatabaseLayer layer = new DatabaseLayer();

    static void imageLoader(ImageView image,String path){
        File file = new File(path);
        Image imageFile = new Image(file.toURI().toString());
        image.setImage(imageFile);

    }

    public void initialize(){
        imageLoader(logo,"images/logo.png");
    }

    public void exitButton(){
        System.exit(1);
    }

    public void minimizeButton(){
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    public void singUpButtonHandle() throws IOException {
        Main main = new Main();
        main.loader("view/RegisterScreen.fxml");
        ((Stage)exitButton.getScene().getWindow()).close();
    }

    public void loginUserHandle() throws IOException {
           if(StaticMethod.lengthController(TCTF,TCTF.getText(),11,11) &&
                   StaticMethod.isInteger(TCTF.getText()) &&
                   StaticMethod.lengthController(passwordPF,passwordPF.getText(),50,3)){

               if(layer.loginUserControl(Double.parseDouble(TCTF.getText()),passwordPF.getText())){
                   Main main =  new Main();
                   ((Stage)exitButton.getScene().getWindow()).close();
                   main.loader("view/MainScreen.fxml");
               }else{
                   loginWrongL.setVisible(true);
               }
           }else{
               StaticMethod.addCSS(TCTF,"com/view/css/mainsc.css","error");
               StaticMethod.addCSS(passwordPF,"com/view/css/mainsc.css","error");
           }

    }

}
