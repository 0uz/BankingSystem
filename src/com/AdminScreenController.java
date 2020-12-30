package com;

import com.util.DatabaseLayer;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Date;

public class AdminScreenController {
    public TableColumn<AdminTable,String> TCCol;
    public TableColumn<AdminTable,Double>  amountCol;
    public TableColumn<AdminTable,Double>  amountIntCol;
    public TableColumn<AdminTable,Integer>  creditMonthCol;
    public TableColumn<AdminTable, Date>  creditDateCol;
    public TableView<AdminTable> table;
    public Button exitButton;
    public Label warning;
    String currentUserTC;

    DatabaseLayer layer = new DatabaseLayer();

    public void initialize(){
        TCCol.setCellValueFactory(new PropertyValueFactory<>("TC"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountIntCol.setCellValueFactory(new PropertyValueFactory<>("amountInt"));
        creditMonthCol.setCellValueFactory(new PropertyValueFactory<>("creditMonth"));
        creditDateCol.setCellValueFactory(new PropertyValueFactory<>("creditDate"));
        table.setPlaceholder(new Label("There is no application"));
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setData();
    }

    void setData(){
        table.setItems(layer.adminTable());
    }

    public void setCurrentUserTC(String currentUserTC) {
        this.currentUserTC = currentUserTC;
    }


    public void acceptButtonHandle(){
        try {
            ObservableList<AdminTable> data=table.getSelectionModel().getSelectedItems();
            for(AdminTable i : data){
                layer.updateCreditControl(i.TC,true);
                layer.creditConfirmationAdmin(i.TC,i.amount,i.paymentDate,i.creditMonth,i.creditID);
            }
            setData();
            warning.setVisible(false);
        }catch (NullPointerException e){
            warning.setVisible(true);
        }

    }
    public void rejectButtonHandle(){
        try {
            ObservableList<AdminTable> data=table.getSelectionModel().getSelectedItems();
            for(AdminTable i : data){
                layer.updateCreditControl(i.TC,false);
            }
            setData();
            warning.setVisible(false);
        }catch (NullPointerException e){
            warning.setVisible(true);
        }
    }

    public void exitButtonAction(){
        System.exit(1);
        layer.closeConnection();
    }
    public void minimizeButtonAction(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setIconified(true);
    }
}
