package com.pap_car_rental;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class CarInspectionControl {

    Image carImg;
    @FXML
    private Label total_price;
    @FXML
    private Label InspectedMake;
    @FXML
    private Label userNameDisplay;
    @FXML
    private Label InspectedModel;
    @FXML
    private Label InspectedName;
    @FXML
    private Label InspectedPrice;
    @FXML
    private Label InspectedType;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private Button goBackButton;
    @FXML
    private Button reserveButton;
    @FXML
    private Button mainMenuButton;
    @FXML
    private ImageView image;


    @FXML
    private void initialize() {
        dateFrom.setValue(UserController.dateFrom_search);
        dateTo.setValue(UserController.dateTo_search);
        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.millis(100),
                        event -> {
        if(dateTo.getValue() != null && dateFrom.getValue() != null) {
            total_price.setText(String.valueOf(DAYS.between(dateFrom.getValue(), dateTo.getValue().plusDays(1))*CarPaneController.inspectedCost));
        };
                }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
        InspectedMake.setText(CarPaneController.inspectedMake);
        InspectedModel.setText(CarPaneController.inspectedModel);
        InspectedName.setText(CarPaneController.inspectedName);
        InspectedType.setText(CarPaneController.inspectedType);
        InspectedPrice.setText(Integer.toString(CarPaneController.inspectedCost));
        userNameDisplay.setText("Hi, " + App.currentUser.login + "!");
        try {
            carImg = new Image(getClass().getResourceAsStream("/com/pap_car_rental/" + CarPaneController.inspectedMake + "_" + CarPaneController.inspectedModel + ".jpg"));
        } catch (Exception e) {
            carImg = new Image(getClass().getResourceAsStream("/com/pap_car_rental/no_img_found.png"));
        }
        image.setImage(carImg);

        dateFrom.setDayCellFactory(picker -> new DateCell() {
            final LocalDate today = LocalDate.now();
            final Set<LocalDate> datesToDisable = new HashSet<>();

            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                try {
                    ArrayList<Reservation> reservations = App.db.listReservations();
                    for (Reservation res : reservations) {
                        if (res.carId == CarPaneController.inspectedCar.id) {
                            datesToDisable.addAll(res.dateFrom.toLocalDate().datesUntil(res.dateTo.toLocalDate().plusDays(1)).collect(Collectors.toSet()));

                        }
                    }
                    setDisable(empty || datesToDisable.contains(date) || date.compareTo(today) < 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        //block to dates older than today
        dateTo.setDayCellFactory(picker -> new DateCell() {
            final LocalDate today = LocalDate.now();
            final Set<LocalDate> datesToDisable = new HashSet<>();

            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                try {
                    ArrayList<Reservation> reservations = App.db.listReservations();
                    for (Reservation res : reservations) {
                        if (res.carId == CarPaneController.inspectedCar.id) {
                            datesToDisable.addAll(res.dateFrom.toLocalDate().datesUntil(res.dateTo.toLocalDate().plusDays(1)).collect(Collectors.toSet()));

                        }
                    }
                    setDisable(empty || datesToDisable.contains(date) || date.compareTo(today) < 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        //block typing in date picker
        dateTo.getEditor().setDisable(true);
        dateTo.getEditor().setOpacity(1);
        dateFrom.getEditor().setDisable(true);
        dateFrom.getEditor().setOpacity(1);

    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToCarList() throws IOException {
        App.setRoot("car_list");
    }

    @FXML
    private void switchToUser() throws IOException {
        App.setRoot("user");
    }

    @FXML
    private void reserve() throws SQLException, IOException {
        Date DateFrom = Date.valueOf(dateFrom.getValue());
        Date DateTo = Date.valueOf(dateTo.getValue());
        int CarId = CarPaneController.inspectedCar.id;
        int ClientId = App.currentUser.id;
        App.db.addReservation(DateFrom, DateTo, ClientId, CarId);
        App.setRoot("user");
    }
}
