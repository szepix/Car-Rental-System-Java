package com.pap_car_rental;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CarList {
    @FXML private Label userNameDisplay;

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToUser() throws IOException {
        App.setRoot("user");
    }
}
