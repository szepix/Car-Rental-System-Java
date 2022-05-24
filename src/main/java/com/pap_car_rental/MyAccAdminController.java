package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;

import static com.pap_car_rental.App.*;

public class MyAccAdminController {
    @FXML
    private Label adminNameDisplay;
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
    private void initialize() throws IOException{
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0] + "!");
        username.setText(App.currentAdmin[0]);
        password.setText(App.currentAdmin[1]);
        //action to edit account
        editAccount.setOnAction(event -> {
            try {
                invalidUser.setText("");
                String[] potentialAdmin = {username.getText(), password.getText()};

                //pass check
                boolean badText = potentialAdmin[0].indexOf(',') != -1;
                if (potentialAdmin[1].indexOf(',') != -1) badText = true;

                boolean toShort = potentialAdmin[0].length() < 4;
                if (potentialAdmin[1].length() < 4) toShort = true;

                if (!badText && !toShort) {
                    //edit account data

                    //read csv
                    ArrayList<String[]> allNames = new ArrayList<>();
                    try (BufferedReader buf = new BufferedReader(new FileReader("src/main/resources/com/pap_car_rental/admin_list.csv"))) {
                        String line;
                        while ((line = buf.readLine()) != null) {
                            String[] data = line.split(",");
                            allNames.add(data);
                        }

                        allNames.forEach(e -> {
                            boolean isAdmin = e[0].equals(currentAdmin[0]);
                            if (isAdmin) {
                                e[0] = potentialAdmin[0];
                                e[1] = potentialAdmin[1];
                                currentAdmin[0]=e[0];
                                currentAdmin[1]=e[1];
                            }
                        });
                    } catch (Exception e) {
                        throw new IOException("file error");
                    }

                    //write csv back
                    try (BufferedWriter buf = new BufferedWriter(new FileWriter("src/main/resources/com/pap_car_rental/admin_list.csv", false))) {
                        allNames.forEach(e -> {
                            try {
                                String line = e[0]+","+e[1];
                                buf.write(line);
                                buf.newLine();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });
                    } catch (Exception e) {
                        throw new IOException("file error");
                    }

                    switchToAdmin();
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
    private void switchToAdmin() throws IOException {
        App.setRoot("admin");
    }
}

