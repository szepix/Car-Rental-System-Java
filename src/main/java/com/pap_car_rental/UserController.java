package com.pap_car_rental;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
}
