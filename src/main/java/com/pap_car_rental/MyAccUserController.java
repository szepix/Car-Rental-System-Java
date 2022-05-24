package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.pap_car_rental.App.*;

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
        //action to edit account
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
                    //edit account data
                    App.db.editClient(App.currentUser.id , username.getText(), password.getText());
                    //relogin
                    ArrayList<Client> client_list = db.listClients();
                    client_list.forEach(e -> {
                        boolean isUser = e.login.equals(potentialUser[0]);
                        if (isUser) {
                            System.out.println(e.id);
                            App.currentUser = e;
                        }
                    });
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

