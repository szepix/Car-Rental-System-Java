module com.pap_car_rental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires sqlite.jdbc;

    opens com.pap_car_rental to javafx.fxml;
    exports com.pap_car_rental;
}
