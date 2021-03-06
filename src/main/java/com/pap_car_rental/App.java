package com.pap_car_rental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    public static final DatabaseControl db = new DatabaseControl();
    public static final boolean[] carType = new boolean[6];
    public static boolean isUser = false;
    public static boolean isAdmin = false;
    public static Client currentUser;
    public static String[] currentAdmin = new String[2];
    public static String searched_make;
    public static String searched_model;
    public static int costLow;
    public static int costHigh;
    public static Date dateFrom;
    public static Date dateTo;
    public static Scene scene;  //was private
    public static boolean inspectionSourcePromo;

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        //System.out.println(db.listClientReservations(2));
        removePastReservations();
        scene = new Scene(loadFXML("main_menu"), 640, 320);
        stage.setScene(scene);
        stage.setTitle("Car Rental System v1.0");
        stage.show();
    }

    private void removePastReservations() throws SQLException {
        ArrayList<Reservation> reservations = db.listReservations();
        for (Reservation reservation : reservations) {
            if(reservation.rented && reservation.dateTo.toLocalDate().isBefore(LocalDate.now())) {
                db.removeReservation(reservation.id);
            }
            else
            {
                if (reservation.dateFrom.toLocalDate().isBefore(LocalDate.now())) {
                    db.removeReservation(reservation.id);
                }
            }
        }
    }
}