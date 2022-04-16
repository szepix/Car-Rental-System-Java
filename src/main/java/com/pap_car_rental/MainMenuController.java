package com.pap_car_rental;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class MainMenuController {
    @FXML private TextField adminName;
    @FXML private TextField adminPwd;
    @FXML private Label invalidAdmin;

    @FXML
    private void switchToAdmin() throws IOException {
        invalidAdmin.setText("");
        String[] potentialAdmin = {adminName.getText(), adminPwd.getText()};
        App.currentAdmin = new String[2];
        App.isUser = false;
        App.isAdmin = false;
        ArrayList<String[]> allNames = new ArrayList<String[]>();
        try(BufferedReader buf = new BufferedReader(new FileReader("src/main/resources/com/pap_car_rental/admin_list.csv"));){
            String line;
            while ((line = buf.readLine()) != null) {
                String[] data = line.split(",");
                allNames.add(data);
            }

            allNames.forEach(e->{
                boolean isAdmin = true;
                if(!e[0].equals(potentialAdmin[0])) isAdmin = false;
                if(!e[1].equals(potentialAdmin[1])) isAdmin = false;
                if(isAdmin){
                    App.currentAdmin[0]=potentialAdmin[0];
                    App.currentAdmin[1]=potentialAdmin[1];
                    App.isAdmin=true;
                }
            });
        }catch(Exception e){
            throw new IOException("file error");
        }

        if(App.isAdmin) App.setRoot("admin");
        else{
            invalidAdmin.setText("Invalid username or password.");
            adminName.setText("");
            adminPwd.setText("");
       }
    }

    @FXML private TextField userName;
    @FXML private TextField userPwd;
    @FXML private Label invalidUser;

    @FXML
    private void switchToUser() throws IOException {
        invalidUser.setText("");
        String[] potentialUser = {userName.getText(), userPwd.getText()};
        App.currentUser = new String[2];
        App.isUser = false;
        App.isAdmin = false;
        ArrayList<String[]> allNames = new ArrayList<String[]>();
        try(BufferedReader buf = new BufferedReader(new FileReader("src/main/resources/com/pap_car_rental/user_list.csv"));){
            String line;
            while ((line = buf.readLine()) != null) {
                String[] data = line.split(",");
                allNames.add(data);
            }

            allNames.forEach(e->{
                boolean isUser = true;
                if(!e[0].equals(potentialUser[0])) isUser = false;
                if(!e[1].equals(potentialUser[1])) isUser = false;
                if(isUser){
                    App.currentUser[0]=potentialUser[0];
                    App.currentUser[1]=potentialUser[1];
                    App.isUser=true;
                }
            });
        }catch(Exception e){
            throw new IOException("file error");
        }

        if(App.isUser) App.setRoot("user");
        else{
             invalidUser.setText("Invalid username or password.");
             userName.setText("");
             userPwd.setText("");
        }
    }
}
