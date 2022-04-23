package com.pap_car_rental;

import java.sql.Date;
import java.time.LocalDate;

public class Car {
    String Car_type;
    String Brand;
    int Cost;
    String Model;
    Date dateFrom;
    Date dateTo;
    Car(String Car_type,String Brand,int Cost,String Model, Date dateFrom, Date dateTo)
    {
        this.Car_type = Car_type;
        this.Brand = Brand;
        this.Cost = Cost;
        this.Model = Model;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
