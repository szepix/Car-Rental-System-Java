package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserComplaintsListController {
    @FXML
    private VBox complaints;
    @FXML
    private Label userNameDisplay;

    @FXML
    private void initialize() throws SQLException {
        userNameDisplay.setText("Hi, " + App.currentUser.login + "!");
        ArrayList<Complaint> complaints_list = App.db.listComplaintsByClientId(App.currentUser.id);
        for (var complaint : complaints_list) {
            FXMLLoader fxmloader = new FXMLLoader();
            fxmloader.setLocation(getClass().getResource("complaint.fxml"));

            try {
                HBox hbox = fxmloader.load();
                ComplaintController complaint_pane = fxmloader.getController();
                complaint_pane.setData(complaint);
                complaints.getChildren().add(hbox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToUser() throws IOException {
        App.setRoot("user");
    }
}
