package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class AdminReservationController {
    private ArrayList<Reservation> reservations;
    private int id = 0;

    public static LocalDate dateTo_search;
    public static LocalDate dateFrom_search;
    @FXML
    private Label adminNameDisplay;
    @FXML
    private VBox carScrollerPickup;

    @FXML
    private TextField clientId;


    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private TextField searched_make_text;
    @FXML
    private TextField searched_model_text;
    @FXML
    private CheckBox carType0;
    @FXML
    private CheckBox carType1;
    @FXML
    private CheckBox carType2;
    @FXML
    private CheckBox carType3;
    @FXML
    private CheckBox carType4;
    @FXML
    private CheckBox carType5;
    @FXML
    private Slider costLow;
    @FXML
    private Slider costHigh;
    @FXML
    private Label noReservations;
    public AdminReservationController() {
    }

    private void limitDates() {
        //block from dates older than today
        dateFrom.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        //block to dates older than today
        dateTo.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    @FXML
    private void initialize() throws SQLException {
        //System.out.println(AdminController.mode);
        noReservations.setVisible(false);
        if (Objects.equals(AdminController.mode, "reserve")) {
            this.changeToReservations();
        } else if (Objects.equals(AdminController.mode, "pickup")) {
            this.changeToPickUp();
        }
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0] + "!");

        limitDates();

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
    private void changeToReservations() {
        AdminController.mode = "reserve";

    }
    //used to initialize the pick-up pane
    @FXML
    private void changeToPickUp() throws SQLException {
        noReservations.setVisible(false);
        AdminController.mode = "pickup";
        carScrollerPickup.getChildren().clear();
        //used to clear the list after refreshing
        if(id == 0) {reservations = App.db.listReservations();}
        else{
            reservations = App.db.listClientReservations(id);
            id = 0;
        }

        //lists all reserved cars
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
        if (carScrollerPickup.getChildren().size() == 0) {
            noReservations.setVisible(true);
        }
    }
    //used to search reservations by user id
    @FXML
    private void searchPickup() throws SQLException {
        try {
            noReservations.setVisible(false);
            id = Integer.parseInt(clientId.getText());
            changeToPickUp();
        } catch (Exception e) {
            noReservations.setVisible(true);
        }
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
    //checks all available cars matching the search
    @FXML
    private void checkAvailable() throws IOException {
        App.searched_make = searched_make_text.getText().toUpperCase();
        App.searched_model = searched_model_text.getText().toUpperCase();
        App.carType[0] = carType0.isSelected();
        App.carType[1] = carType1.isSelected();
        App.carType[2] = carType2.isSelected();
        App.carType[3] = carType3.isSelected();
        App.carType[4] = carType4.isSelected();
        App.carType[5] = carType5.isSelected();
        App.costLow = (int) costLow.getValue();
        App.costHigh = (int) costHigh.getValue();
        dateFrom_search = dateFrom.getValue();
        dateTo_search = dateTo.getValue();
        try {
            App.dateFrom = Date.valueOf(dateFrom.getValue());
            App.dateTo = Date.valueOf(dateTo.getValue());
        } catch (Exception e) {
            App.dateFrom = null;
            App.dateTo = null;

        }
        App.setRoot("admin_car_list");
    }
}

