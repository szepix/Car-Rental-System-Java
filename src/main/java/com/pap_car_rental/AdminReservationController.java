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
import java.util.Collections;

public class AdminReservationController {
    private ArrayList<Car> allCars;
    private ArrayList<Reservation> reservations;
    @FXML
    private Label adminNameDisplay;
    @FXML
    private Button goBackButton;
    @FXML
    private VBox carScroller;

    @FXML
    private Button pickUpCarsButton;

    @FXML
    private Button reserveCarsButton;

    public AdminReservationController() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0] + "!");

        reserveCarsButton.setOnAction(event -> {
            try {
                changeToReservations();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });
        pickUpCarsButton.setOnAction(event -> {
            try {
                changeToPickUp();
            } catch (IOException | SQLException e) {
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

    private void changeToReservations() throws IOException, SQLException {
        allCars = App.db.listCars();
        reservations = App.db.listReservations();
        carScroller.getChildren().clear();
        for (var car : allCars) {
            FXMLLoader fxmloader = new FXMLLoader();
            fxmloader.setLocation(getClass().getResource("client_rented_car.fxml"));

            try {
                BorderPane hbox = fxmloader.load();
                ClientRentedCarController carPane = fxmloader.getController();
                carPane.setData(car, reservations.get(0));
                carScroller.getChildren().add(hbox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void changeToPickUp() throws IOException, SQLException {
        allCars = App.db.listCars();
        reservations = App.db.listReservations();
        carScroller.getChildren().clear();
        for (var reservation : reservations) {
            if(!reservation.rented) {
                FXMLLoader fxmloader = new FXMLLoader();
                fxmloader.setLocation(getClass().getResource("admin_reserved_car.fxml"));
                try {
                    BorderPane hbox = fxmloader.load();
                    AdminReservedCarController carPane = fxmloader.getController();
                    carPane.setData(allCars.get(reservation.carId - 1), reservation);
                    carScroller.getChildren().add(hbox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

