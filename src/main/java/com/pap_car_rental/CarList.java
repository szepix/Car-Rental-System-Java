package com.pap_car_rental;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class CarList {
    @FXML private Label userNameDisplay;

    @FXML private void initialize() throws IOException, SQLException {
        ArrayList<Car> allCars = App.db.listCars();
        ArrayList<String> possibleTypes = new ArrayList<>(Arrays.asList("City", "Mid", "Large", "SUV", "Sport", "Special"));
        ArrayList<String> searchedTypes = new ArrayList<>();
        ArrayList<Car> matchingMake = new ArrayList<>();
        ArrayList<Car> matchingModel = new ArrayList<>();
        ArrayList<Car> matchingCost = new ArrayList<>();
        ArrayList<Car> matchingType = new ArrayList<>();
        ArrayList<Car> matchingDate = new ArrayList<>();

        //Checking Brand
        if (App.searched_make.equals(""))
        {
            matchingMake.addAll(allCars);
        }
        else
        {
            for (Car car2 : allCars) {
                if (car2.Brand.equals(App.searched_make))
                {
                matchingMake.add(car2);
                }
            }
        }

        //Checking Model
        if (App.searched_model.equals(""))
        {
            matchingModel.addAll(matchingMake);
        }
        else
        {
            for (Car car4 : matchingMake) {
                if (car4.Model.equals(App.searched_model))
                {
                matchingModel.add(car4);
                }
            }
        }

        //Checking Cost
        if (App.costLow == 0 & App.costHigh == 200)
        {
            matchingCost.addAll(matchingModel);
        }
        else
        {
            for (Car car5 : matchingModel) {
                if ((car5.Cost <= App.costHigh) & (car5.Cost >= App.costLow))
                {
                matchingCost.add(car5);
                }
            }
        }

        //Checking Type
        for(int i=0;i<possibleTypes.size(); i++)
        {
            if(App.carType[i]){
                searchedTypes.add(possibleTypes.get(i));
            }
        }

        if (searchedTypes.size() == 0)
        {
            matchingType.addAll(matchingCost);
        }
        else
        {
            for (Car car6 : matchingCost) {
                if (searchedTypes.contains(car6.Car_type))
                {
                matchingType.add(car6);
                }
            }
        }

        //Checking Date
        if (App.dateFrom == null & App.dateTo == null)
        {
            matchingDate.addAll(matchingType);
        }
        else
        {
            matchingDate.addAll(matchingType);
        }

        //Diagnostics
        for (Car car: matchingType)
        {
            System.out.println("Searched: " + car.Brand + " "+ car.Car_type + " "+ car.Model + " " + car.Cost);
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
