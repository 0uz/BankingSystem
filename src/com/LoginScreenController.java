package com;


import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginScreenController {
    public Button exitButton;
    public Button minimizeButton;

    public void exitButton(){
        System.exit(1);
    }

    public void minimizeButton(){
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

}
