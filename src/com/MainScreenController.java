package com;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class MainScreenController {

    public ImageView buttonIcon;

    public void initialize(){
        File file = new File("images/phone.png");
        Image image = new Image(file.toURI().toString());
        buttonIcon.setImage(image);
    }


    }
}
