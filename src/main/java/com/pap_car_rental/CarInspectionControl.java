package com.pap_car_rental;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class CarInspectionControl {

    Image carImg;
    @FXML
    private Label totalCost;
    @FXML
    private Label InspectedMake;
    @FXML
    private Label userNameDisplay;
    @FXML
    private Label InspectedModel;
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
        //get paramteres
        String insMake;
        String insModel;
        String insType;
        int insCost;
        int insID;
        if(!App.inspectionSourcePromo){
            insMake = CarPaneController.inspectedMake;
            insModel = CarPaneController.inspectedModel;
            insType = CarPaneController.inspectedType;
            insCost = CarPaneController.inspectedCost;
            insID = CarPaneController.inspectedCar.id;
        }else{
            insMake = CarPromoController.inspectedMake;
            insModel = CarPromoController.inspectedModel;
            insType = CarPromoController.inspectedType;
            insCost = CarPromoController.inspectedCost;
            insID = CarPromoController.inspectedCar.id;
        }


        dateFrom.setValue(UserController.dateFrom_search);
        dateTo.setValue(UserController.dateTo_search);
        //timer to calculate the total cost
        Timeline timer = new Timeline(
                new KeyFrame(Duration.millis(100),
                        event -> {
                            if (dateTo.getValue() != null && dateFrom.getValue() != null) {
                                totalCost.setText("$"+String.valueOf(DAYS.between(dateFrom.getValue(), dateTo.getValue().plusDays(1)) * insCost));
                            }
                        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        InspectedMake.setText(insMake);
        InspectedModel.setText(insModel);
        InspectedType.setText(insType);
        InspectedPrice.setText(Integer.toString(insCost)+" $/d");
        userNameDisplay.setText("Hi, " + App.currentUser.login + "!");
        try {
            carImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pap_car_rental/images/" + insMake.toUpperCase() + "_" + insModel.toUpperCase() + ".jpg")));
        } catch (Exception e) {
            carImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pap_car_rental/images/no_img_found.png")));
        }
        image.setImage(carImg);


        //block to dates older than today and reserved dates
        dateFrom.setDayCellFactory(picker -> new DateCell() {
            final LocalDate today = LocalDate.now();
            final Set<LocalDate> datesToDisable = new HashSet<>();

            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                try {
                    ArrayList<Reservation> reservations = App.db.listReservations();
                    for (Reservation res : reservations) {
                        if (res.carId == insID) {
                            datesToDisable.addAll(res.dateFrom.toLocalDate().datesUntil(res.dateTo.toLocalDate().plusDays(1)).collect(Collectors.toSet()));

                        }
                    }
                    setDisable(empty || datesToDisable.contains(date) || date.compareTo(today) < 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        //block to dates older than today and reserved dates
        dateTo.setDayCellFactory(picker -> new DateCell() {
            final LocalDate today = LocalDate.now();
            final Set<LocalDate> datesToDisable = new HashSet<>();

            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                try {
                    ArrayList<Reservation> reservations = App.db.listReservations();
                    for (Reservation res : reservations) {
                        if (res.carId == insID) {
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

     //action on sign off button
    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    //action on go back button
    @FXML
    private void goBackAction() throws IOException {
        if(!App.inspectionSourcePromo) App.setRoot("car_list");
        else App.setRoot("user");
    }

    //action on main menu button
    @FXML
    private void switchToUser() throws IOException {
        App.setRoot("user");
    }

    //action on reserve button
    @FXML
    private void reserve() throws SQLException, IOException {
        Date DateFrom = Date.valueOf(dateFrom.getValue());
        Date DateTo = Date.valueOf(dateTo.getValue());
        int CarId;
        if(!App.inspectionSourcePromo) CarId = CarPaneController.inspectedCar.id;
        else CarId = CarPromoController.inspectedCar.id;
        int ClientId = App.currentUser.id;
        App.db.addReservation(DateFrom, DateTo, ClientId, CarId);
        App.setRoot("user");
    }

    @FXML
    private void limitToDate() {
        //block to dates older than from day + 1
        dateTo.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate min = dateFrom.getValue();

                setDisable(empty || date.compareTo(min) < 0);
            }
        });
    }

    @FXML
    private void limitFromDate() {
        //block to dates older than from day + 1
        dateFrom.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate max = dateTo.getValue();
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(max) > 0 || date.compareTo(today) < 0);
            }
        });
    }
}
