module com.pap_car_rental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires sqlite.jdbc;
    requires junit;
    requires org.assertj.core;
    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;
    exports com.pap_car_rental;
    opens com.pap_car_rental to javafx.fxml;
}
