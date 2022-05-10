package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientInfoController implements Initializable {

    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private Label id;
    @FXML
    private Button edit;

    public void setData(Client client) {
        login.setText(client.login);
        password.setText(client.password);
        System.out.println(client.login);
        System.out.println(client.id);
        id.setText(String.valueOf(client.id));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        edit.setOnAction(event -> {
            try {
                App.db.editClient(Integer.parseInt(id.getText()), login.getText(), password.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }
}
