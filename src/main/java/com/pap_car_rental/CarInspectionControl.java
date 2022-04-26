package com.pap_car_rental;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CarInspectionControl {

    @FXML
    private Label InspectedMake;

    @FXML
    private Label InspectedModel;

    @FXML
    private Label InspectedName;

    @FXML
    private Label InspectedPrice;

    @FXML
    private Label InspectedType;

    @FXML
    private Button goBackButton;

    @FXML
    private Button goBackButton1;

    @FXML
    private Button mainMenuButton;

    @FXML
    private ImageView image;

    Image carImg;

    @FXML
    private void initialize() {
        InspectedMake.setText(CarPaneController.inspectedMake);
        InspectedModel.setText(CarPaneController.inspectedModel);
        InspectedName.setText(CarPaneController.inspectedName);
        InspectedType.setText(CarPaneController.inspectedType);
        InspectedPrice.setText(Integer.toString(CarPaneController.inspectedCost));
        try
        {
        carImg = new Image(getClass().getResourceAsStream("/com/pap_car_rental/"+ CarPaneController.inspectedMake + "_" + CarPaneController.inspectedModel +".jpg"));
        }
        catch(Exception e)
        {
        carImg = new Image(getClass().getResourceAsStream("/com/pap_car_rental/no_img_found.png"));
        }
        image.setImage(carImg);

    }

    @FXML
    private void switchToMainMenu() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToCarList() throws IOException {
        App.setRoot("car_list");
    }

    @FXML
    private void switchToUser() throws IOException {
        App.setRoot("user");
    }

}
