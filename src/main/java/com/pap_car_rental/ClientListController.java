package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientListController {
    @FXML
    private Label adminNameDisplay;
    @FXML
    private Button goBackButton;
    @FXML
    private VBox clientList;


    @FXML
    private void initialize() throws SQLException {
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0] + "!");

        ArrayList<Client> allClients = App.db.listClients();

        for (var client : allClients) {
            FXMLLoader fxmloader = new FXMLLoader();
            fxmloader.setLocation(getClass().getResource("client_info.fxml"));

            try {
                BorderPane hbox = fxmloader.load();
                ClientInfoController clientInfo = fxmloader.getController();
                clientInfo.setData(client);
                clientList.getChildren().add(hbox);
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

