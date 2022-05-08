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

public class ClientRentsController {
    @FXML
    private Label userNameDisplay;
    @FXML
    private Button goBackButton;
    @FXML
    private VBox carScroller;


    @FXML
    private void initialize() throws SQLException {
        userNameDisplay.setText("Hi, " + App.currentUser.login + "!");
        ArrayList<Car> allCars = App.db.listCars();
        ArrayList<Reservation> reservations = App.db.listClientReservations(App.currentUser.id);
        for (var reservation : reservations)
        {
            FXMLLoader fxmloader = new FXMLLoader();
            fxmloader.setLocation(getClass().getResource("client_rented_car.fxml"));

            try {
                BorderPane hbox = fxmloader.load();
                ClientRentedCarController carPane = fxmloader.getController();
                carPane.setData(allCars.get(reservation.carId-1), reservation);
                carScroller.getChildren().add(hbox);
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
    private void switchToUser() throws IOException {
        App.setRoot("user");
    }
}

