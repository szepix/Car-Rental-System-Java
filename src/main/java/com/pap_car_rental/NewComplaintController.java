package com.pap_car_rental;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class NewComplaintController {
    @FXML
    private Label userNameDisplay;
    @FXML
    private Button goBackButton;
    @FXML
    private VBox carScroller;
    @FXML
    private Button sendButton;
    @FXML
    private TextArea description;

    @FXML
    private void initialize() throws SQLException {
        userNameDisplay.setText("Hi, " + App.currentUser.login + "!");
        Platform.runLater(() -> userNameDisplay.requestFocus());
    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToUser() throws IOException {
        App.setRoot("user");
    }
    @FXML
    private void addComplaint() throws SQLException, IOException {
        App.db.addComplaint(App.currentUser.id, description.getText());
        App.setRoot("user");
    }
}

