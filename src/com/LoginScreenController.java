package com;


import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class LoginScreenController {
    public Button exitButton;
    public Button minimizeButton;
    public ImageView logo;

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

}
