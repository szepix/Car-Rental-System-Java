package com.pap_car_rental;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AdminCarListController {
    @FXML
    private Label adminNameDisplay;

    @FXML
    private Button goBackButton;

    @FXML
    private VBox carScroller;

    @FXML
    private Label noResultLabel;

    @FXML
    private void initialize() throws SQLException {
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0] + "!");

        ArrayList<Car> allCars = App.db.listCars();
        ArrayList<String> possibleTypes = new ArrayList<>(Arrays.asList("City", "Mid", "Large", "SUV", "Sport", "Special"));
        ArrayList<String> searchedTypes = new ArrayList<>();
        ArrayList<Car> matchingMake = new ArrayList<>();
        ArrayList<Car> matchingModel = new ArrayList<>();
        ArrayList<Car> matchingCost = new ArrayList<>();
        ArrayList<Car> matchingType = new ArrayList<>();
        ArrayList<Car> matchingDate = new ArrayList<>();
        ArrayList<Reservation> reservations = App.db.listReservations();
        boolean valid;

        //Checking Brand
        if (App.searched_make.equals("")) {
            matchingMake.addAll(allCars);
        } else {
            for (Car car2 : allCars) {
                if (car2.Brand.toUpperCase().contains(App.searched_make)) {
                    matchingMake.add(car2);
                }
            }
        }

        //Checking Model
        if (App.searched_model.equals("")) {
            matchingModel.addAll(matchingMake);
        } else {
            for (Car car4 : matchingMake) {
                if (car4.Model.toUpperCase().contains(App.searched_model)) {
                    matchingModel.add(car4);
                }
            }
        }

        //Checking Cost
        if (App.costLow == 0 & App.costHigh == 300) {
            matchingCost.addAll(matchingModel);
        } else {
            for (Car car5 : matchingModel) {
                if ((car5.Cost <= App.costHigh) & (car5.Cost >= App.costLow)) {
                    matchingCost.add(car5);
                }
            }
        }

        //Checking Type
        for (int i = 0; i < possibleTypes.size(); i++) {
            if (App.carType[i]) {
                searchedTypes.add(possibleTypes.get(i));
            }
        }

        if (searchedTypes.size() == 0) {
            matchingType.addAll(matchingCost);
        } else {
            for (Car car6 : matchingCost) {
                if (searchedTypes.contains(car6.Car_type)) {
                    matchingType.add(car6);
                }
            }
        }

        //Checking Date
        if (App.dateFrom == null & App.dateTo == null) {
            matchingDate.addAll(matchingType);
        } else {
            for (Car car7 : matchingType) {
                valid = true;
                for (Reservation res : reservations) {
                    if (res.carId == car7.id) {
                        System.out.println((Objects.requireNonNull(App.dateTo).compareTo(res.dateFrom) < 0));
                        if ((App.dateTo.compareTo(res.dateFrom) < 0) & (App.dateFrom.compareTo(res.dateFrom) < 0)) {
                        } else if ((App.dateFrom.compareTo(res.dateTo) > 0) & (App.dateTo.compareTo(res.dateTo) > 0)) {
                        } else {
                            valid = false;
                        }

                    }

                }
                if (valid) {
                    matchingDate.add(car7);
                }
            }
        }

        //initializing car list
        carScroller.getChildren().clear();
        for (var car : matchingType) {
            FXMLLoader fxmloader = new FXMLLoader();
            fxmloader.setLocation(getClass().getResource("admin_reservation_pane.fxml"));
            try {
                BorderPane hbox = fxmloader.load();
                AdminReservationPaneController carPane = fxmloader.getController();
                carPane.setData(car);
                carScroller.getChildren().add(hbox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (matchingDate.size() == 0)
        {
            noResultLabel.setText("No results Found");
        }
        else
        {
            noResultLabel.setText("");
        }


    }
    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }
    @FXML
    private void switchToReserve() throws IOException {
        App.setRoot("admin_reservation");
    }
}
