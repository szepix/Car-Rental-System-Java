package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientInfoController implements Initializable {

    @FXML
    private Label login_label;
    @FXML
    private Label password_label;
    @FXML
    private Label id_label;

    public void setData(Client client)
    {
        login_label.setText(client.login);
        password_label.setText(client.password);
        id_label.setText(String.valueOf(client.id));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
