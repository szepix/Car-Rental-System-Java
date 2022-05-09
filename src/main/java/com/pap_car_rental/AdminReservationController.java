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
import java.util.Objects;

public class AdminReservationController {
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

    public AdminReservationController() {
    }

    @FXML
    private void initialize() throws SQLException {
        System.out.println(AdminController.mode);
        if (Objects.equals(AdminController.mode, "reserve")) {
            this.changeToReservations();
        } else if (Objects.equals(AdminController.mode, "pickup")) {
            this.changeToPickUp();
        }
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0] + "!");

        reserveCarsButton.setOnAction(event -> {
            try {
                changeToReservations();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        pickUpCarsButton.setOnAction(event -> {
            try {
                changeToPickUp();
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

    private void changeToReservations() throws SQLException {
        AdminController.mode = "reserve";
        ArrayList<Car> allCars = App.db.listCars();
        reservations = App.db.listReservations();
        carScroller.getChildren().clear();
        for (var car : allCars) {
            FXMLLoader fxmloader = new FXMLLoader();
            fxmloader.setLocation(getClass().getResource("admin_reservation_pane.fxml"));
            try {
                BorderPane hbox = fxmloader.load();
                AdminReservationPaneController carPane = fxmloader.getController();
                carPane.setData(car);
                carScroller.getChildren().add(hbox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void changeToPickUp() throws SQLException {
        AdminController.mode = "pickup";
        reservations = App.db.listReservations();
        carScroller.getChildren().clear();
        for (var reservation : reservations) {
            if (!reservation.rented) {
                FXMLLoader fxmloader = new FXMLLoader();
                fxmloader.setLocation(getClass().getResource("admin_reserved_car.fxml"));
                try {
                    BorderPane hbox = fxmloader.load();
                    AdminReservedCarController carPane = fxmloader.getController();
                    carPane.setData(App.db.findCar(reservation.carId), reservation);
                    carScroller.getChildren().add(hbox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

