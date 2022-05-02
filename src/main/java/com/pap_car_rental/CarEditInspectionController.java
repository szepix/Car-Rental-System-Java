package com.pap_car_rental;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CarEditInspectionController {

    @FXML
    private TextField car_name;

    @FXML
    private TextField car_type;

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

    Image carImg;

    @FXML
    private void initialize() {
        car_make.setText(CarEditPaneController.inspectedMake);
        car_model.setText(CarEditPaneController.inspectedModel);
        car_name.setText(CarEditPaneController.inspectedName);
        car_type.setText(CarEditPaneController.inspectedType);
        car_price.setText(Integer.toString(CarEditPaneController.inspectedCost));
        try
        {
            carImg = new Image(getClass().getResourceAsStream("/com/pap_car_rental/"+ CarEditPaneController.inspectedMake + "_" + CarEditPaneController.inspectedModel +".jpg"));
        }
        catch(Exception e)
        {
            carImg = new Image(getClass().getResourceAsStream("/com/pap_car_rental/no_img_found.png"));
        }
        image.setImage(carImg);

    }
    @FXML
    private void editCar() throws SQLException, IOException {
        System.out.println(CarEditPaneController.car.id);
        Car car = new Car(CarEditPaneController.car.id, car_type.getText(), car_make.getText(), Integer.parseInt(car_price.getText()), car_model.getText(), null, null);
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
