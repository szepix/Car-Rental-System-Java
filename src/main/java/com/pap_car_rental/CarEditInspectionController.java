package com.pap_car_rental;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class CarEditInspectionController {

    Image carImg;
    @FXML
    private TextField car_name;
    @FXML
    private ChoiceBox car_type;
    @FXML
    private TextField car_make;
    @FXML
    private TextField car_model;
    @FXML
    private TextField car_price;
    @FXML
    private Button goBackButton;
    @FXML
    private Button editButton;
    @FXML
    private Button mainMenuButton;
    @FXML
    private ImageView image;
    @FXML
    private Label adminNameDisplay;

    @FXML
    private void initialize() {
        adminNameDisplay.setText("Hi, " + App.currentAdmin[0]+"!");
        car_type.getItems().addAll("City", "Mid", "Large", "SUV", "Sport", "Special");
        car_make.setText(CarEditPaneController.inspectedMake);
        car_model.setText(CarEditPaneController.inspectedModel);
        car_type.setValue(CarEditPaneController.inspectedType);
        car_price.setText(Integer.toString(CarEditPaneController.inspectedCost));
        try {
            carImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pap_car_rental/images/" + CarEditPaneController.inspectedMake + "_" + CarEditPaneController.inspectedModel + ".jpg")));
        } catch (Exception e) {
            carImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pap_car_rental/images/no_img_found.png")));
        }
        image.setImage(carImg);

    }
    //editing cars using input values
    @FXML
    private void editCar() throws SQLException, IOException {
        //System.out.println(CarEditPaneController.car.id);
        Car car = new Car(CarEditPaneController.car.id, (String) car_type.getValue(), car_make.getText(), Integer.parseInt(car_price.getText()), car_model.getText());
        App.db.editCar(car);
        App.setRoot("car_edit");
    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToCarList() throws IOException {
        App.setRoot("car_edit");
    }

    @FXML
    private void switchToUser() throws IOException {
        App.setRoot("admin");
    }

}
