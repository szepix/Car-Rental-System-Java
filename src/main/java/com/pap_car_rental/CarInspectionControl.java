package com.pap_car_rental;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CarInspectionControl {

    @FXML
    private Label InspectedMake;

    @FXML
    private  Label userNameDisplay;

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

    Image carImg;

    @FXML
    private void initialize() {
        InspectedMake.setText(CarPaneController.inspectedMake);
        InspectedModel.setText(CarPaneController.inspectedModel);
        InspectedName.setText(CarPaneController.inspectedName);
        InspectedType.setText(CarPaneController.inspectedType);
        InspectedPrice.setText(Integer.toString(CarPaneController.inspectedCost));
        userNameDisplay.setText("Hi, "+ App.currentUser.login+"!");
        try
        {
        carImg = new Image(getClass().getResourceAsStream("/com/pap_car_rental/"+ CarPaneController.inspectedMake + "_" + CarPaneController.inspectedModel +".jpg"));
        }
        catch(Exception e)
        {
        carImg = new Image(getClass().getResourceAsStream("/com/pap_car_rental/no_img_found.png"));
        }
        image.setImage(carImg);

        dateFrom.setDayCellFactory(picker -> new DateCell() {
            LocalDate today = LocalDate.now();
            Set<LocalDate> datesToDisable = new HashSet<LocalDate>();
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                try {
                    ArrayList<Reservation> reservations = App.db.listReservations();
                    for (Reservation res : reservations) {
                        if (res.carId == CarPaneController.inspectedCar.id) {
                            datesToDisable.addAll(res.dateFrom.toLocalDate().datesUntil(res.dateTo.toLocalDate().plusDays(1)).collect(Collectors.toSet()));

                        }
                    }
                    if(datesToDisable!=null) setDisable(empty || datesToDisable.contains(date) || date.compareTo(today) < 0);
                    else setDisable(empty || date.compareTo(today) < 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        //block to dates older than today
        dateTo.setDayCellFactory(picker -> new DateCell() {
            LocalDate today = LocalDate.now();
            Set<LocalDate> datesToDisable = new HashSet<LocalDate>();
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                try {
                    ArrayList<Reservation> reservations = App.db.listReservations();
                    for (Reservation res : reservations) {
                        if (res.carId == CarPaneController.inspectedCar.id) {
                            datesToDisable.addAll(res.dateFrom.toLocalDate().datesUntil(res.dateTo.toLocalDate().plusDays(1)).collect(Collectors.toSet()));

                        }
                    }
                    if(datesToDisable!=null) setDisable(empty || datesToDisable.contains(date) || date.compareTo(today) < 0);
                    else setDisable(empty || date.compareTo(today) < 0);
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
