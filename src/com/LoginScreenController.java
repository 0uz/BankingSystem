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

    public void initialize(){
        File file = new File("images/logo.png");
        Image image = new Image(file.toURI().toString());
        logo.setImage(image);
    }

    public void exitButton(){
        System.exit(1);
    }

    public void minimizeButton(){
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

}
