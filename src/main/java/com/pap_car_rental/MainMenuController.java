package com.pap_car_rental;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

// import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.pap_car_rental.App.*;

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
    private Tab userTab;
    @FXML
    private Tab adminTab;


    private void keystrokesIni(){
        //set first focus
        Platform.runLater(() -> invalidUser.requestFocus());

        //on enter, user pwd
        userPwd.setOnKeyPressed( event -> {
            if( event.getCode() == KeyCode.ENTER ) {
                try {
                    switchToUser();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
          } );

        //on tab, user pwd
        userName.setOnKeyPressed( event -> {
            if( event.getCode() == KeyCode.TAB ) {
                userPwd.requestFocus();
            }
          } );

        //on enter, admin pwd
        adminPwd.setOnKeyPressed( event -> {
        if( event.getCode() == KeyCode.ENTER ) {
            try {
                switchToAdmin();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        } );

        //on tab, admin pwd
        adminName.setOnKeyPressed( event -> {
            if( event.getCode() == KeyCode.TAB ) {
                adminPwd.requestFocus();
            }
            } );
    }


    @FXML
    private void initialize() {
        //resize
        try {
            Stage stage = (Stage) App.scene.getWindow();
            // GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            // int width = gd.getDisplayMode().getWidth();
            // int height = gd.getDisplayMode().getHeight();
            stage.setWidth(660);    //+20 to keep size
            stage.setHeight(360);   //+40 to keep size
        } catch (Exception e) {}

        //initialize navigation with tab and enter
        keystrokesIni();
    }


    @FXML
    private void switchToAdmin() throws IOException {
        //login action (admin)

        invalidAdmin.setText("");
        String[] potentialAdmin = {adminName.getText(), adminPwd.getText()};
        currentAdmin = new String[2];
        isUser = false;
        isAdmin = false;
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
                    currentAdmin[0] = potentialAdmin[0];
                    currentAdmin[1] = potentialAdmin[1];
                    App.isAdmin = true;
                }
            });
        } catch (Exception e) {
            throw new IOException("file error");
        }

        if (isAdmin) setRoot("admin");
        else {
            invalidAdmin.setText("Invalid username or password.");
            adminName.setText("");
            adminPwd.setText("");
        }
    }

    @FXML
    private void switchToUser() throws IOException, SQLException {
        //login action (user)

        invalidUser.setText("");
        String[] potentialUser = {userName.getText(), userPwd.getText()};
        ArrayList<Client> client_list = db.listClients();
        isUser = false;
        isAdmin = false;

        client_list.forEach(e -> {
            boolean isUser = e.login.equals(potentialUser[0]);
            if (!e.password.equals(potentialUser[1])) isUser = false;
            if (isUser) {
                //System.out.println(e.id);
                App.currentUser = e;
                App.isUser = true;
            }
        });

        if (isUser) {
            setRoot("user");
        } else {
            invalidUser.setText("Invalid username\nor password.");
            userName.setText("");
            userPwd.setText("");
        }
    }

    @FXML
    private void switchToUserRegister() throws IOException, SQLException {
        //register action (user)

        invalidUser.setText("");
        String[] potentialUser = {userName.getText(), userPwd.getText()};
        isUser = false;
        isAdmin = false;
        ArrayList<Client> user_list = db.listClients();

        //check if user registered
        user_list.forEach(e -> {
            boolean isUser = e.login.equals(potentialUser[0]);
            if (isUser) {
                App.isUser = true;
            }
        });

        //pass check characters
        boolean badText = potentialUser[0].indexOf(',') != -1;
        if (potentialUser[1].indexOf(',') != -1) badText = true;

        //pass check length
        boolean toShort = potentialUser[0].length() < 4;
        if (potentialUser[1].length() < 4) toShort = true;

       //case: new user, good password
        if (!isUser && !badText && !toShort) {
            //register
            if (user_list.size() == 0) {
                user_list.add(new Client(0, potentialUser[0], potentialUser[1]));
                currentUser = user_list.get(0);
            } else {
                user_list.add(new Client(user_list.get(user_list.size() - 1).id + 1, potentialUser[0], potentialUser[1]));
                currentUser = user_list.get(user_list.size() - 1);
            }

            isUser = true;
            db.addClient(potentialUser[0], potentialUser[1]);


            setRoot("user");
        } else {
            //case: invalid characters in password
            if (badText)
                invalidUser.setText("Cannot contain ','");

            //case: password to short
            else if (toShort)
                invalidUser.setText("Too short. Must be\nmin 4 chars long.");

            //case: user registered already
            else
                invalidUser.setText("Already registered.");
            userName.setText("");
            userPwd.setText("");
        }
    }

    @FXML
    private void focusUser(){
        Platform.runLater(() -> userName.requestFocus());
    }

    @FXML
    private void focusAdmin(){
        Platform.runLater(() -> adminName.requestFocus());
    }
}
