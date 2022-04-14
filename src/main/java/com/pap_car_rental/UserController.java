package com.pap_car_rental;

import java.io.IOException;
import javafx.fxml.FXML;

public class UserController {

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }
}
