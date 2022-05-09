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
import java.time.temporal.Temporal;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;


public class AdminRentedCarController implements Initializable {

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
    private Label carName;
    @FXML
    private Label carType;
    @FXML
    private Label dateFrom;
    @FXML
    private Label dateTo;
    @FXML
    private ImageView image;
    @FXML
    private Label name;

    public void setData(Car car, Reservation reservation) throws SQLException {
        this.reservation = reservation;
        carTotalCost.setText(String.valueOf(DAYS.between(reservation.dateFrom.toLocalDate(),reservation.dateTo.toLocalDate().plusDays(1))*car.Cost));
        dateFrom.setText(String.valueOf(reservation.dateFrom));
        dateTo.setText(String.valueOf(reservation.dateTo));
        carMake.setText(car.Brand);
        carModel.setText(car.Model);
        carName.setText("Car");
        carType.setText("(" + car.Car_type + ")");
        name.setText(App.db.findClientById(reservation.clientId).login);
        try {
            carImg = new Image(getClass().getResourceAsStream("/com/pap_car_rental/" + car.Brand.toUpperCase() + "_" + car.Model.toUpperCase() + ".jpg"));
        } catch (Exception e) {
            carImg = new Image(getClass().getResourceAsStream("/com/pap_car_rental/no_img_found.png"));
        }
        image.setImage(carImg);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
//    @FXML
//    private void cancelReservation() throws SQLException, IOException {
//        App.db.removeReservation(reservation.id);
//        App.setRoot("client_rents");
//    }
}
