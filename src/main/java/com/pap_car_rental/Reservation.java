package com.pap_car_rental;

import java.sql.Date;

public class Reservation {
    public int id;
    public Date dateFrom;
    public Date dateTo;
    public int clientId;
    public int carId;
    Reservation(int id, Date dateFrom, Date dateTo,int clientId,int carId)
    {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.clientId = clientId;
        this.carId = carId;
    }
}
