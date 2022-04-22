package com.pap_car_rental;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;



public class UserController {
    @FXML private Label userNameDisplay;

    @FXML
    private void initialize() throws IOException {
        userNameDisplay.setText("Hi, "+App.currentUser[0]+"!");
    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML private TextField searched_make_text;
    @FXML private TextField searched_model_text;
    @FXML private CheckBox carType0;
    @FXML private CheckBox carType1;
    @FXML private CheckBox carType2;
    @FXML private CheckBox carType3;
    @FXML private CheckBox carType4;
    @FXML private CheckBox carType5;
    @FXML private Slider costLow;
    @FXML private Slider costHigh;
    @FXML private DatePicker dateFrom;
    @FXML private DatePicker dateTo;


    @FXML
    private void switchToCarList() throws IOException {
        App.searched_make = searched_make_text.getText();
        App.searched_model = searched_model_text.getText();
        App.carType[0]=carType0.isSelected();
        App.carType[1]=carType1.isSelected();
        App.carType[2]=carType2.isSelected();
        App.carType[3]=carType3.isSelected();
        App.carType[4]=carType4.isSelected();
        App.carType[5]=carType5.isSelected();
        App.costLow=(int)costLow.getValue();
        App.costLow=(int)costHigh.getValue();
        App.dateFrom=dateFrom.getValue();
        App.dateTo=dateTo.getValue();

        System.out.println("From "+App.dateFrom+" To "+App.dateTo);
        App.setRoot("car_list");
    }
}

