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


public class AdminComplaintsListController {
    @FXML
    private VBox complaints;
    @FXML
    private Label adminNameDisplay;

    @FXML
    private void initialize() throws SQLException {
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0] + "!");
        ArrayList<Complaint> complaints_list = App.db.listComplaints();
        for (var complaint : complaints_list) {
            FXMLLoader fxmloader = new FXMLLoader();
            fxmloader.setLocation(getClass().getResource("admin_complaint.fxml"));
            try {
                BorderPane hbox = fxmloader.load();
                AdminComplaintController complaint_pane = fxmloader.getController();
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
    private void switchToAdmin() throws IOException {
        App.setRoot("admin");
    }
}
