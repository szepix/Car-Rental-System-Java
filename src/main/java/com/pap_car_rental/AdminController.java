package com.pap_car_rental;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminController {
    @FXML private Label adminNameDisplay;

    @FXML
    private void initialize() {
        adminNameDisplay.setText("HAIL, "+App.currentAdmin[0]+"!");
    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }
    @FXML
    private void switchToAddCar() throws IOException {
        App.setRoot("car_add");
    }
}