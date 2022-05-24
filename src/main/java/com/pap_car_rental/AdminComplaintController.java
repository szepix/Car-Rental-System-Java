package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.sql.SQLException;

public class AdminComplaintController {
    private Complaint complaint;
    @FXML
    private Label status;
    @FXML
    private TextFlow text;
    @FXML
    private Label info;
    @FXML
    private HBox hbox;
    @FXML
    private Button resolve;
    public void setData(Complaint complaint) throws SQLException {
        this.complaint = complaint;
        if(complaint.Resolved)
        {
            status.setText("Resolved");
            resolve.setVisible(false);
        }
        else
        {
            status.setText("Unresolved");
        }
        text.getChildren().add(new Text(complaint.Text));
        Client client = App.db.findClientById(complaint.ClientId);
        info.setText(complaint.id+ ". " + client.login + " " + "id: " + client.id);
    }

    @FXML
    public void setResolve() throws SQLException, IOException {
        App.db.editComplaint(complaint.id,  complaint.ClientId, complaint.Text, true);
        resolve.setVisible(false);
        App.setRoot("admin_complaints_list");
    }
}
