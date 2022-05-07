package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class AdminController {
    @FXML
    private Label adminNameDisplay;

    @FXML
    private void initialize() {
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0] + "!");
    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToAddCar() throws IOException {
        App.setRoot("car_add");
    }

    @FXML
    private void switchToEditCars() throws IOException {
        App.setRoot("car_edit");
    }

    @FXML
    private void switchClientList() throws IOException {
        App.setRoot("client_list");
    }
}