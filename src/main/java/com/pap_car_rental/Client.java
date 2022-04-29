package com.pap_car_rental;

import java.sql.Date;

public class Client {
    int id;
    String login;
    String password;
    Client(int id, String login, String password)
    {
        this.id = id;
        this.login = login;
        this.password = password;
    }
}

