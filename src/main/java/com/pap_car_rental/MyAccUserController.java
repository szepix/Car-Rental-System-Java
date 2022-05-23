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

public class MyAccUserController {
    @FXML
    private Label userNameDisplay;
    @FXML
    private Button goBackButton;
    @FXML
    private Button editAccount;
    @FXML
    private TextField password;
    @FXML
    private TextField username;
    @FXML
    private Label invalidUser;

    @FXML
    private void initialize() {
        userNameDisplay.setText("Hi, " + App.currentUser.login + "!");
        username.setText(App.currentUser.login);
        password.setText(App.currentUser.password);
        //action to create a new client account
        editAccount.setOnAction(event -> {
            try {
                invalidUser.setText("");
                String[] potentialUser = {username.getText(), password.getText()};
                ArrayList<Client> user_list = App.db.listClients();

                //pass check
                boolean badText = potentialUser[0].indexOf(',') != -1;
                if (potentialUser[1].indexOf(',') != -1) badText = true;

                boolean toShort = potentialUser[0].length() < 4;
                if (potentialUser[1].length() < 4) toShort = true;

                if (!badText && !toShort) {
                    App.db.editClient(App.currentUser.id , username.getText(), password.getText()); //does not work
                    switchToUser();
                } else {
                    if (badText)
                        invalidUser.setText("Cannot contain ','");
                    else if (toShort)
                        invalidUser.setText("Too short. Must be\nmin 4 chars long.");
                    username.setText(App.currentUser.login);
                    password.setText(App.currentUser.password);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

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

