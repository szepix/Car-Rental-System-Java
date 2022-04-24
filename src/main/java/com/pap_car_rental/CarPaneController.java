package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    @FXML
    private ImageView image;

    Image carImg;

    public void setData(Car car)
    {
        carCost.setText(Integer.toString(car.Cost));
        carMake.setText(car.Brand);
        carModel.setText(car.Model);
        carName.setText("Car");
        carType.setText("("+car.Car_type+")");
        try
        {
        carImg = new Image(getClass().getResourceAsStream("/com/pap_car_rental/"+ car.Brand + "_" + car.Model +".jpg"));
        }
        catch(Exception e)
        {
        carImg = new Image(getClass().getResourceAsStream("/com/pap_car_rental/sus_bike.jpg"));
        }
        image.setImage(carImg);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
