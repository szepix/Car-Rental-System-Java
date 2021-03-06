package com.pap_car_rental;

public class Car implements Comparable<Car> {
    final int id;
    final String Car_type;
    final String Brand;
    final int Cost;
    final String Model;


    Car(int id, String Car_type, String Brand, int Cost, String Model) {
        this.id = id;
        this.Car_type = Car_type;
        this.Brand = Brand;
        this.Cost = Cost;
        this.Model = Model;

    }

    @Override
    public int compareTo(Car comp) {
        return comp.Cost - this.Cost;
    }
}
