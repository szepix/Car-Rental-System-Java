package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.stage.Stage;
import javafx.scene.Scene;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class MainMenuController {
    @FXML
    private TextField adminName;
    @FXML
    private TextField adminPwd;
    @FXML
    private Label invalidAdmin;
    @FXML
    private TextField userName;
    @FXML
    private TextField userPwd;
    @FXML
    private Label invalidUser;


    @FXML
    private void initialize() throws SQLException {
        //resize
        try{
            Stage stage = (Stage) App.scene.getWindow();
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();
            stage.setWidth(660);    //+20 to keep size
            stage.setHeight(360);   //+40 to keep size
        }catch(Exception e){}
    }


    @FXML
    private void switchToAdmin() throws IOException {
        invalidAdmin.setText("");
        String[] potentialAdmin = {adminName.getText(), adminPwd.getText()};
        App.currentAdmin = new String[2];
        App.isUser = false;
        App.isAdmin = false;
        ArrayList<String[]> allNames = new ArrayList<>();
        try (BufferedReader buf = new BufferedReader(new FileReader("src/main/resources/com/pap_car_rental/admin_list.csv"))) {
            String line;
            while ((line = buf.readLine()) != null) {
                String[] data = line.split(",");
                allNames.add(data);
            }

            allNames.forEach(e -> {
                boolean isAdmin = e[0].equals(potentialAdmin[0]);
                if (!e[1].equals(potentialAdmin[1])) isAdmin = false;
                if (isAdmin) {
                    App.currentAdmin[0] = potentialAdmin[0];
                    App.currentAdmin[1] = potentialAdmin[1];
                    App.isAdmin = true;
                }
            });
        } catch (Exception e) {
            throw new IOException("file error");
        }

        if (App.isAdmin) App.setRoot("admin");
        else {
            invalidAdmin.setText("Invalid username or password.");
            adminName.setText("");
            adminPwd.setText("");
        }
    }

    @FXML
    private void switchToUser() throws IOException, SQLException {
        invalidUser.setText("");
        String[] potentialUser = {userName.getText(), userPwd.getText()};
        ArrayList<Client> client_list = App.db.listClients();
        App.isUser = false;
        App.isAdmin = false;
        ArrayList<String[]> allNames = new ArrayList<>();
        client_list.forEach(e -> {
            boolean isUser = e.login.equals(potentialUser[0]);
            if (!e.password.equals(potentialUser[1])) isUser = false;
            if (isUser) {
                App.currentUser = e;
                App.isUser = true;
            }
        });

        if (App.isUser) {
            App.setRoot("user");
        } else {
            invalidUser.setText("Invalid username\nor password.");
            userName.setText("");
            userPwd.setText("");
        }
    }

    @FXML
    private void switchToUserRegister() throws IOException, SQLException {
        invalidUser.setText("");
        String[] potentialUser = {userName.getText(), userPwd.getText()};
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


            App.setRoot("user");
        } else {
            if (badText)
                invalidUser.setText("Cannot contain ','");
            else if (toShort)
                invalidUser.setText("Too short. Must be\nmin 4 chars long.");
            else
                invalidUser.setText("Already registered.");
            userName.setText("");
            userPwd.setText("");
        }
    }
}
