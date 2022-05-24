package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ComplaintController {

    @FXML
    private Label status;
    @FXML
    private TextFlow text;

    public void setData(Complaint complaint) {
        if(complaint.Resolved)
        {
            status.setText("Resolved");
        }
        else
        {
            status.setText("Unresolved");
        }
        text.getChildren().add(new Text(complaint.Text));
    }

}
