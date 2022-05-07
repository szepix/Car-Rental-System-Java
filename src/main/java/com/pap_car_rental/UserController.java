package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;


public class UserController {
    @FXML
    private Label userNameDisplay;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private TextField searched_make_text;
    @FXML
    private TextField searched_model_text;
    @FXML
    private CheckBox carType0;
    @FXML
    private CheckBox carType1;
    @FXML
    private CheckBox carType2;
    @FXML
    private CheckBox carType3;
    @FXML
    private CheckBox carType4;
    @FXML
    private CheckBox carType5;
    @FXML
    private Slider costLow;
    @FXML
    private Slider costHigh;

    public static LocalDate dateTo_search;
    public static LocalDate dateFrom_search;
    @FXML
    private void initialize() {
        //initialize username
        userNameDisplay.setText("Hi, " + App.currentUser.login + "!");

        //block illegal dates:

        //block from dates older than today
        dateFrom.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        //block to dates older than today
        dateTo.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        //block typing in date picker
        dateTo.getEditor().setDisable(true);
        dateTo.getEditor().setOpacity(1);
        dateFrom.getEditor().setDisable(true);
        dateFrom.getEditor().setOpacity(1);


        //got to limit the date from the top, when decided what is the limit
    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToCarList() throws IOException {
        App.searched_make = searched_make_text.getText().toUpperCase();
        App.searched_model = searched_model_text.getText().toUpperCase();
        App.carType[0] = carType0.isSelected();
        App.carType[1] = carType1.isSelected();
        App.carType[2] = carType2.isSelected();
        App.carType[3] = carType3.isSelected();
        App.carType[4] = carType4.isSelected();
        App.carType[5] = carType5.isSelected();
        App.costLow = (int) costLow.getValue();
        App.costHigh = (int) costHigh.getValue();
        dateFrom_search = dateFrom.getValue();
        dateTo_search = dateTo.getValue();
        try {
            App.dateFrom = Date.valueOf(dateFrom.getValue());
            App.dateTo = Date.valueOf(dateTo.getValue());
        } catch (Exception e) {
            App.dateFrom = null;
            App.dateTo = null;

        }

        System.out.println("From " + App.dateFrom + " To " + App.dateTo);
        App.setRoot("car_list");
    }

    @FXML
    private void limitToDate() {
        //block to dates older than from day + 1
        dateTo.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate min = dateFrom.getValue();

                setDisable(empty || date.compareTo(min) < 0);
            }
        });
    }

    @FXML
    private void limitFromDate() {
        //block to dates older than from day + 1
        dateFrom.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate max = dateTo.getValue();
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(max) > 0 || date.compareTo(today) < 0);
            }
        });
    }
}

