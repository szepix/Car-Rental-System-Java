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

import static java.time.temporal.ChronoUnit.DAYS;


public class ClientRentedCarController implements Initializable {

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

    static void textSetup(Car car, Reservation reservation, Label carTotalCost, Label dateFrom, Label dateTo, Label carMake, Label carModel, Label carType) {
        carTotalCost.setText("$" + String.valueOf(DAYS.between(reservation.dateFrom.toLocalDate(), reservation.dateTo.toLocalDate().plusDays(1)) * car.Cost));
        dateFrom.setText(String.valueOf(reservation.dateFrom));
        dateTo.setText(String.valueOf(reservation.dateTo));
        carMake.setText(car.Brand);
        carModel.setText(car.Model);
        carType.setText("(" + car.Car_type + ")");
    }

    public void setData(Car car, Reservation reservation) {
        ClientRentedCarController.reservation = reservation;
        textSetup(car, reservation, carTotalCost, dateFrom, dateTo, carMake, carModel, carType);
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
                App.setRoot("client_rents");
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
