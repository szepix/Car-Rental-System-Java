package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


public class AdminReservedCarController implements Initializable {

    public static Car car;

    public static Reservation reservation;
    Image carImg;
    @FXML
    private Label carTotalCost;
    @FXML
    private Label carMake;
    @FXML
    private Label carModel;

    @FXML
    private Label carType;
    @FXML
    private Label dateFrom;
    @FXML
    private Label dateTo;
    @FXML
    private ImageView image;
    @FXML
    private Button cancelButton;
    @FXML
    private Button rentButton;
    @FXML
    private Label userName;

    public void setData(Car car, Reservation reservation) throws SQLException {
        AdminReservedCarController.reservation = reservation;
        ClientRentedCarController.textSetup(car, reservation, carTotalCost, dateFrom, dateTo, carMake, carModel, carType);
        Client client = App.db.findClientById(reservation.clientId);
        userName.setText(client.login + " id: " + client.id);
        try {
            carImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pap_car_rental/images/" + car.Brand.toUpperCase() + "_" + car.Model.toUpperCase() + ".jpg")));
        } catch (Exception e) {
            carImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pap_car_rental/images/no_img_found.png")));
        }
        image.setImage(carImg);

        cancelButton.setOnAction(event -> {
            ClientRentedCarController.reservation = reservation;
            try {
                App.db.removeReservation(reservation.id);
                App.setRoot("admin_reservation");
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        });
        rentButton.setOnAction(event -> {
            try {
                App.db.rentCar(reservation.id);
                App.setRoot("admin_reservation");
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
