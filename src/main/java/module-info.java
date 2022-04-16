module com.pap_car_rental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.pap_car_rental to javafx.fxml;
    exports com.pap_car_rental;
}
