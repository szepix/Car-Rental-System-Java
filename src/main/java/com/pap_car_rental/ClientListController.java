package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private Button createAccount;
    @FXML
    private TextField password;
    @FXML
    private TextField username;
    @FXML
    private Label invalidUser;

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
        createAccount.setOnAction(event -> {
            try {
                invalidUser.setText("");
                String[] potentialUser = {username.getText(), password.getText()};
                App.isUser = false;
                App.isAdmin = false;
                ArrayList<Client> user_list = App.db.listClients();
                Client currentUser;
                user_list.forEach(e -> {
                    boolean isUser = e.login.equals(potentialUser[0]);
                    if (isUser) {
                        App.isUser = true;
                    }
                });

                //pass check
                boolean badText = potentialUser[0].indexOf(',') != -1;
                if (potentialUser[1].indexOf(',') != -1) badText = true;

                boolean toShort = potentialUser[0].length() < 4;
                if (potentialUser[1].length() < 4) toShort = true;

                if (!App.isUser && !badText && !toShort) {
                    //register
                    if (user_list.size() == 0) {
                        user_list.add(new Client(0, potentialUser[0], potentialUser[1]));
                        App.currentUser = user_list.get(0);
                    } else {
                        user_list.add(new Client(user_list.get(user_list.size() - 1).id + 1, potentialUser[0], potentialUser[1]));
                        App.currentUser = user_list.get(user_list.size() - 1);
                    }

                    App.isUser = true;
                    App.db.addClient(potentialUser[0], potentialUser[1]);
                } else {
                    if (badText)
                        invalidUser.setText("Cannot contain ','");
                    else if (toShort)
                        invalidUser.setText("Too short. Must be\nmin 4 chars long.");
                    else
                        invalidUser.setText("Already registered.");
                    username.setText("");
                    password.setText("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

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

