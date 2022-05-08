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

public class RentedCarsListController {
    @FXML
    private Label adminNameDisplay;
    @FXML
    private Button goBackButton;
    @FXML
    private VBox carScroller;


    @FXML
    private void initialize() throws SQLException {
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0] + "!");
        ArrayList <Car> allCars = App.db.listCars();
        ArrayList <Reservation> reservations = App.db.listReservations();
        carScroller.getChildren().clear();
        for (var reservation : reservations) {
            if(reservation.rented) {
                FXMLLoader fxmloader = new FXMLLoader();
                fxmloader.setLocation(getClass().getResource("admin_rented_car.fxml"));
                try {
                    BorderPane hbox = fxmloader.load();
                    AdminRentedCarController carPane = fxmloader.getController();
                    carPane.setData(allCars.get(reservation.carId - 1), reservation);
                    carScroller.getChildren().add(hbox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

