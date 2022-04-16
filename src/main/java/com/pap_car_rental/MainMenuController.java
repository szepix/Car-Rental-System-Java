package com.pap_car_rental;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainMenuController {
    @FXML
    private void switchToAdmin() throws IOException {
        App.setRoot("admin");
    }

    @FXML
    private void switchToUser() throws IOException {
        App.setRoot("user");
    }
}
