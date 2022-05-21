package com.pap_car_rental;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

public class AdminCarInspectionController {

    Image carImg;
    @FXML
    private Label total_price;
    @FXML
    private Label InspectedMake;
    @FXML
    private Label adminNameDisplay;
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
    private ChoiceBox<String> userName;

    @FXML
    private void initialize() throws SQLException {
        //timer to calculate total cost
        Timeline timer = new Timeline(
                new KeyFrame(Duration.millis(100),
                        event -> {
                            if (dateTo.getValue() != null && dateFrom.getValue() != null) {
                                total_price.setText(String.valueOf(DAYS.between(dateFrom.getValue(), dateTo.getValue().plusDays(1)) * AdminReservationPaneController.inspectedCost));
                            }
                        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        ArrayList<Client> allClients = App.db.listClients();
        for (var client : allClients) {
            userName.getItems().add(client.login);
        }
        InspectedMake.setText(AdminReservationPaneController.inspectedMake);
        InspectedModel.setText(AdminReservationPaneController.inspectedModel);
        InspectedName.setText(AdminReservationPaneController.inspectedName);
        InspectedType.setText(AdminReservationPaneController.inspectedType);
        InspectedPrice.setText(Integer.toString(AdminReservationPaneController.inspectedCost));
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0] + "!");
        try {
            carImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pap_car_rental/images/" + AdminReservationPaneController.inspectedMake + "_" + AdminReservationPaneController.inspectedModel + ".jpg")));
        } catch (Exception e) {
            carImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pap_car_rental/images/no_img_found.png")));
        }
        image.setImage(carImg);
        //block reserved dates and past dates
        dateFrom.setDayCellFactory(picker -> new DateCell() {
            final LocalDate today = LocalDate.now();
            final Set<LocalDate> datesToDisable = new HashSet<>();

            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                try {
                    ArrayList<Reservation> reservations = App.db.listReservations();
                    for (Reservation res : reservations) {
                        if (res.carId == AdminReservationPaneController.car.id) {
                            datesToDisable.addAll(res.dateFrom.toLocalDate().datesUntil(res.dateTo.toLocalDate().plusDays(1)).collect(Collectors.toSet()));

                        }
                    }
                    setDisable(empty || datesToDisable.contains(date) || date.compareTo(today) < 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        //block reserved dates and past dates
        dateTo.setDayCellFactory(picker -> new DateCell() {
            final LocalDate today = LocalDate.now();
            final Set<LocalDate> datesToDisable = new HashSet<>();

            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                try {
                    ArrayList<Reservation> reservations = App.db.listReservations();
                    for (Reservation res : reservations) {
                        if (res.carId == AdminReservationPaneController.car.id) {
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
        App.setRoot("admin_reservation");
    }

    @FXML
    private void switchToAdmin() throws IOException {
        App.setRoot("admin");
    }
    //function used to reserve cars for registered user
    @FXML
    private void reserve() throws SQLException, IOException {
        Date DateFrom = Date.valueOf(dateFrom.getValue());
        Date DateTo = Date.valueOf(dateTo.getValue());
        int CarId = AdminReservationPaneController.car.id;
        int ClientId = App.db.findClientByName(String.valueOf(userName.getSelectionModel().getSelectedItem())).id;
        App.db.addReservation(DateFrom, DateTo, ClientId, CarId);
        App.setRoot("admin_reservation");
    }
}
