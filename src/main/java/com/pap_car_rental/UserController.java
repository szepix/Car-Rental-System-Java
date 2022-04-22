package com.pap_car_rental;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    @FXML
    private void switchToCarList() throws IOException {
        App.searched_make = searched_make_text.getText();
        App.searched_model = searched_model_text.getText();

        App.setRoot("car_list");
    }
}

