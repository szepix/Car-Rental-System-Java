package com.pap_car_rental;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CarAddController {
    @FXML private ChoiceBox<String> car_type;
    @FXML private TextField brand_text;
    @FXML private TextField model_text;
    @FXML private TextField cost_text;

    @FXML
    private void initialize() {
        car_type.getItems().addAll("City", "Mid", "Large", "SUV", "Sport", "Special");
    }

    @FXML
    private void switchToAdminMenu() throws IOException {
        App.setRoot("admin");
    }
    @FXML
    private void addCar() throws SQLException {
        String Car_type = car_type.getValue();
        String Brand = brand_text.getText();
        int Cost = Integer.parseInt(cost_text.getText());
        String Model = model_text.getText();
        App.db.addCar(Car_type, Brand, Cost, Model);
        car_type.setValue(null);
        brand_text.clear();
        cost_text.clear();
        model_text.clear();
    }
}