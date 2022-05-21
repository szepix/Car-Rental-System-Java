package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


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
    private Label carType;
    @FXML
    private Label dateFrom;
    @FXML
    private Label dateTo;
    @FXML
    private ImageView image;
    @FXML
    private Label userName;

    //function to create car entries on the list
    public void setData(Car car, Reservation reservation) throws SQLException {
        AdminRentedCarController.reservation = reservation;
        ClientRentedCarController.textSetup(car, reservation, carTotalCost, dateFrom, dateTo, carMake, carModel, carType);
        Client client = App.db.findClientById(reservation.clientId);
        userName.setText(client.login + " id: " + client.id);
        try {
            carImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pap_car_rental/images/" + car.Brand.toUpperCase() + "_" + car.Model.toUpperCase() + ".jpg")));
        } catch (Exception e) {
            carImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pap_car_rental/images/no_img_found.png")));
        }
        image.setImage(carImg);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
