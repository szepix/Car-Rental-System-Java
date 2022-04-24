package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;


public class CarPaneController implements Initializable{

    @FXML
    private Label carCost;
    @FXML
    private Label carMake;
    @FXML
    private Label carModel;
    @FXML
    private Label carName;
    @FXML
    private Label carType;

    public void setData(Car car)
    {
        carCost.setText(Integer.toString(car.Cost));
        carMake.setText(car.Brand);
        carModel.setText(car.Model);
        carName.setText("Car");
        carType.setText("("+car.Car_type+")");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
