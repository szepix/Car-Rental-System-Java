package com.pap_car_rental;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CarList {
    @FXML private Label userNameDisplay;

    @FXML private void initialize() throws IOException {
        ArrayList<String[]> allCars = new ArrayList<String[]>();
        ArrayList<String[]> matchingBrand = new ArrayList<String[]>();
        ArrayList<String[]> matchingCars = new ArrayList<String[]>();
        try(BufferedReader buf = new BufferedReader(new FileReader("src/main/resources/com/pap_car_rental/car_list.csv"));)
            {
            String line;
            while ((line = buf.readLine()) != null) {
                String[] data = line.split(",");
                allCars.add(data);
            }
        }catch(Exception e){
            throw new IOException("file error");}

        //Checking Brand
        if (App.searched_make.equals(""))
        {
            for (String[] car1 : allCars)
            {
                matchingBrand.add(car1);
            }
        }
        else
        {
            for (String[] car2 : allCars) {
                if (car2[0].equals(App.searched_make))
                {
                matchingCars.add(car2);
                }
            }
        }

        //Checking Model
        if (App.searched_model.equals(""))
        {
            for (String[] car3 : matchingBrand)
            {
                matchingCars.add(car3);
            }
        }
        else
        {
            for (String[] car4 : matchingBrand) {
                if (car4[1].equals(App.searched_model))
                {
                matchingCars.add(car4);
                }
            }
        }

        System.out.println(App.searched_make+ " "+ App.searched_model);
        for (String[] car: matchingCars)
        {
            System.out.println(car[2]);
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
