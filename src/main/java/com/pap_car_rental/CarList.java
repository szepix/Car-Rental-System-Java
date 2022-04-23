package com.pap_car_rental;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarList {
    @FXML private Label userNameDisplay;

    @FXML private void initialize() throws IOException, SQLException {
        ArrayList<Car> allCars = App.db.listCars();
        ArrayList<Car> matchingBrand = new ArrayList<>();
        ArrayList<Car> matchingCars = new ArrayList<>();

        //Checking Brand
        if (App.searched_make.equals(""))
        {
            matchingBrand.addAll(allCars);
        }
        else
        {
            for (Car car2 : allCars) {
                if (car2.Brand.equals(App.searched_make))
                {
                matchingCars.add(car2);
                }
            }
        }

        //Checking Model
        if (App.searched_model.equals(""))
        {
            matchingCars.addAll(matchingBrand);
        }
        else
        {
            for (Car car4 : matchingBrand) {
                if (car4.Model.equals(App.searched_model))
                {
                matchingCars.add(car4);
                }
            }
        }

        System.out.println(App.searched_make+ " "+ App.searched_model);
        for (Car car: matchingCars)
        {
            System.out.println(car.Car_type);
        }
    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToUser() throws IOException {
        App.setRoot("user");
    }
}
