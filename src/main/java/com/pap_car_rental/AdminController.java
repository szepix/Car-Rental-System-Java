package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class AdminController {
    public static String mode;
    @FXML
    private Label adminNameDisplay;

    @FXML
    private void initialize() {
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0] + "!");
        Stage stage = (Stage) App.scene.getWindow();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        stage.setWidth(720);
        stage.setHeight(640);
    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToAddCar() throws IOException {
        App.setRoot("car_add");
    }

    @FXML
    private void switchToEditCars() throws IOException {
        App.setRoot("car_edit");
    }

    @FXML
    private void switchClientList() throws IOException {
        App.setRoot("client_list");
    }

    @FXML
    private void switchToReservationController() throws IOException {
        App.setRoot("admin_reservation");
    }

    @FXML
    private void switchToRentedCarList() throws IOException {
        App.setRoot("rented_cars_list");
    }
}