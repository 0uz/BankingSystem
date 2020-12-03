package com;

import com.util.DatabaseLayer;
import javafx.scene.control.Control;
import java.util.Random;

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

    public static String IBANCalculator(){
        DatabaseLayer layer = new DatabaseLayer();
        Random random = new Random();
        String countryCode = "TR";
        int controlDigit = random.nextInt(90)+10;
        int bankNo = 14058;
        int rezDigit = random.nextInt(10);
        int accountNo1 = random.nextInt(90000000)+10000000;
        int accountNo2 = random.nextInt(90000000)+10000000;
        String IBAN = countryCode + controlDigit + bankNo + rezDigit + accountNo1 +accountNo2;
        while (layer.IBANConflictControl(IBAN)){
            accountNo1 = random.nextInt(90000000)+10000000;
        }
        return IBAN;
    }
}
