package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class AdminReservationController {
    private ArrayList<Reservation> reservations;
    private int id = 0;
    @FXML
    private Label adminNameDisplay;
    @FXML
    private Button goBackButton;
    @FXML
    private VBox carScrollerReserve;
    @FXML
    private VBox carScrollerPickup;
    @FXML
    private Tab pickUpCars;

    @FXML
    private Tab reserveCars;

    @FXML
    private TextField clientId;

    @FXML
    private Button searchButton;

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

    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToAdmin() throws IOException {
        App.setRoot("admin");
    }
    @FXML
    private void changeToReservations() throws SQLException {
        AdminController.mode = "reserve";
        ArrayList<Car> allCars = App.db.listCars();
        reservations = App.db.listReservations();
        carScrollerReserve.getChildren().clear();
        for (var car : allCars) {
            FXMLLoader fxmloader = new FXMLLoader();
            fxmloader.setLocation(getClass().getResource("admin_reservation_pane.fxml"));
            try {
                BorderPane hbox = fxmloader.load();
                AdminReservationPaneController carPane = fxmloader.getController();
                carPane.setData(car);
                carScrollerReserve.getChildren().add(hbox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    @FXML
    private void changeToPickUp() throws SQLException {
        AdminController.mode = "pickup";
        carScrollerPickup.getChildren().clear();
        if(id == 0) {reservations = App.db.listReservations();}
        else{
            reservations = App.db.listClientReservations(id);
            id = 0;
        }
        for (var reservation : reservations) {
            if (!reservation.rented) {
                FXMLLoader fxmloader = new FXMLLoader();
                fxmloader.setLocation(getClass().getResource("admin_reserved_car.fxml"));
                try {
                    BorderPane hbox = fxmloader.load();
                    AdminReservedCarController carPane = fxmloader.getController();
                    carPane.setData(App.db.findCar(reservation.carId), reservation);
                    carScrollerPickup.getChildren().add(hbox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @FXML
    private void searchPickup() throws SQLException {
        id = Integer.parseInt(clientId.getText());
        changeToPickUp();
    }
}

