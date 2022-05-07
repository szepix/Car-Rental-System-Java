package com.pap_car_rental;

import java.sql.Date;

public class Reservation {
    public final int id;
    public final Date dateFrom;
    public final Date dateTo;
    public final int clientId;
    public final int carId;

    Reservation(int id, Date dateFrom, Date dateTo, int clientId, int carId) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.clientId = clientId;
        this.carId = carId;
    }
}