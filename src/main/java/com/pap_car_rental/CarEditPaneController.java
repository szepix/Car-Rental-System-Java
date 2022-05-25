package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class CarEditPaneController implements Initializable {

    public static Car car;
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
    @FXML
    private Label carCost;
    @FXML
    private Label carMake;
    @FXML
    private Label carModel;
    @FXML
    private Label carType;
    @FXML
    private ImageView image;
    @FXML
    private Button selectButton;

    public void setData(Car car) {
        carCost.setText("$ " + Integer.toString(car.Cost));
        carMake.setText(car.Brand);
        carModel.setText(car.Model);
        carType.setText("(" + car.Car_type + ")");
        try {
            carImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pap_car_rental/images/" + car.Brand.toUpperCase() + "_" + car.Model.toUpperCase() + ".jpg")));
        } catch (Exception e) {
            carImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pap_car_rental/images/no_img_found.png")));
        }
        image.setImage(carImg);
        selectButton.setOnAction(event -> {
            CarEditPaneController.car = car;
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
                App.setRoot("car_edit_inspection");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void switchToReservationInspection() throws IOException {
        App.setRoot("admin_car_inspection");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
