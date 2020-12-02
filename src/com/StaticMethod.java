package com;

import javafx.scene.control.Control;

public class StaticMethod {
    static void addCSS (Control node, String path, String className){
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

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
