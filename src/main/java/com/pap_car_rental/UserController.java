package com.pap_car_rental;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class UserController {
    @FXML private Label userNameDisplay;

    @FXML
    private void initialize() throws IOException {
        userNameDisplay.setText("Hi, "+App.currentUser[0]+"!");
    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML private TextField marka_text;
    @FXML private TextField model_text;

    @FXML
    private void switchToCarList() throws IOException {
        String marka = marka_text.getText();
        String model = model_text.getText();
        ArrayList<String[]> allCars = new ArrayList<String[]>();
        ArrayList<String[]> matchingCars = new ArrayList<String[]>();
        try(BufferedReader buf = new BufferedReader(new FileReader("src/main/resources/com/pap_car_rental/car_list.csv"));)
            {
            String line;
            while ((line = buf.readLine()) != null) {
                String[] data = line.split(",");
                allCars.add(data);
            }

            allCars.forEach(e->{
                if (e[0].equals(marka) & e[1].equals(model))
                {matchingCars.add(e);}
            });
        }catch(Exception e){
            throw new IOException("file error");}
        for(var car: matchingCars){
            System.out.println(car[2]);
        }
        App.setRoot("car_list");
    }
}

