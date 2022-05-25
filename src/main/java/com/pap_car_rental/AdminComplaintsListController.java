package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;


public class AdminComplaintsListController {
    @FXML
    private VBox complaints;
    @FXML
    private Label adminNameDisplay;
    @FXML
    private Button searchButton;
    @FXML
    private CheckBox unresolved;
    @FXML
    private TextField clientId;

    @FXML
    private void initialize() throws SQLException {
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0] + "!");
        ArrayList<Complaint> complaints_list;
        unresolved.setSelected(AdminController.onlyUnres);
        if(AdminController.id!=0) clientId.setText(String.valueOf(AdminController.id));
        if(AdminController.id == 0)
        {
            complaints_list = App.db.listComplaints();
        }
        else
        {
            complaints_list = App.db.listComplaintsByClientId(AdminController.id);
        }
       Collections.reverse(complaints_list);
        for (var complaint : complaints_list) {
            if(AdminController.onlyUnres)
            {
                if(!complaint.Resolved)
                {
                    setupInfo(complaint);
                }

            }
            else
            {
                setupInfo(complaint);
            }

        }
    }

    private void setupInfo(Complaint complaint) throws SQLException {
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

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToAdmin() throws IOException {
        App.setRoot("admin");
    }
    @FXML
    private void search() throws IOException {

        if (clientId.getText() == "")
        {
            AdminController.id = 0;
        }
        else
        {
            AdminController.id = Integer.parseInt(clientId.getText());
        }
        AdminController.onlyUnres = unresolved.isSelected();
        App.setRoot("admin_complaints_list");
    }
}
