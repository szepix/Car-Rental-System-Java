package com.pap_car_rental;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

import java.io.IOException;
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
    @FXML
    private Button selectButton;

    public static int selectedCost;
    public static String selectedMake;
    public static String selectedModel;
    public static String selectedName;
    public static String selectedType;

    public static int inspectedCost;
    public static String inspectedMake;
    public static String inspectedModel;
    public static String inspectedName;
    public static String inspectedType;


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
        selectButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Make:" + car.Brand);
                System.out.println("Model:" + car.Model);
                System.out.println("Cost:" + car.Cost);
                System.out.println("Type:" + car.Car_type);
                inspectedCost = car.Cost;
                inspectedMake = car.Brand;
                inspectedModel = car.Model;
                inspectedName = "Car";
                inspectedType = car.Car_type;
                try {
                    App.setRoot("car_inspection");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
}


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
