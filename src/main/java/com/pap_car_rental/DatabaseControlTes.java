package com.pap_car_rental;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class DatabaseControlTes {
    @Mock
    Connection c;
    @Mock
    Statement stmt;
    @InjectMocks
    DatabaseControl databaseControl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListReservations() throws Exception {
        ArrayList<Reservation> result = databaseControl.listReservations();
        Assert.assertEquals(new ArrayList<>(List.of(new Reservation(0, null, null, 0, 0, true))), result);
    }

    @Test
    public void testListClientReservations() throws Exception {
        ArrayList<Reservation> result = databaseControl.listClientReservations(0);
        Assert.assertEquals(new ArrayList<>(List.of(new Reservation(0, null, null, 0, 0, true))), result);
    }

    @Test
    public void testListCars() throws Exception {
        ArrayList<Car> result = databaseControl.listCars();
        Assert.assertEquals(new ArrayList<>(List.of(new Car(0, "Car_type", "Brand", 0, "Model"))), result);
    }

    @Test
    public void testListClients() throws Exception {
        ArrayList<Client> result = databaseControl.listClients();
        Assert.assertEquals(new ArrayList<>(List.of(new Client(0, "login", "password"))), result);
    }

    @Test
    public void testAddCar() throws Exception {
        databaseControl.addCar("car_type", "brand", 0, "model");
    }

    @Test
    public void testAddClient() throws Exception {
        databaseControl.addClient("login", "password");
    }

    @Test
    public void testEditClient() throws Exception {
        databaseControl.editClient(0, "login", "password");
    }

    @Test
    public void testEditCar() throws Exception {
        databaseControl.editCar(new Car(0, "Car_type", "Brand", 0, "Model"));
    }

    @Test
    public void testRentCar() throws Exception {
        databaseControl.rentCar(0);
    }

    @Test
    public void testAddReservation() throws Exception {
        databaseControl.addReservation(null, null, 0, 0);
    }

    @Test
    public void testRemoveReservation() throws Exception {
        databaseControl.removeReservation(0);
    }

    @Test
    public void testFindClientById() throws Exception {
        Client result = databaseControl.findClientById(0);
        Assert.assertEquals(new Client(0, "login", "password"), result);
    }

    @Test
    public void testFindClientByName() throws Exception {
        Client result = databaseControl.findClientByName("clientName");
        Assert.assertEquals(new Client(0, "login", "password"), result);
    }

    @Test
    public void testFindCar() throws Exception {
        Car result = databaseControl.findCar(0);
        Assert.assertEquals(new Car(0, "Car_type", "Brand", 0, "Model"), result);
    }

    @Test
    public void testFindReservation() throws Exception {
        Reservation result = databaseControl.findReservation(0);
        Assert.assertEquals(new Reservation(0, null, null, 0, 0, true), result);
    }

    @Test
    public void testListComplaints() throws Exception {
        ArrayList<Complaint> result = databaseControl.listComplaints();
        Assert.assertEquals(new ArrayList<>(List.of(new Complaint(0, 0, "Text", true))), result);
    }

    @Test
    public void testAddComplaint() throws Exception {
        databaseControl.addComplaint(0, "Text");
    }

    @Test
    public void testListComplaintsByClientId() throws Exception {
        ArrayList<Complaint> result = databaseControl.listComplaintsByClientId(0);
        Assert.assertEquals(new ArrayList<>(List.of(new Complaint(0, 0, "Text", true))), result);
    }

    @Test
    public void testEditComplaint() throws Exception {
        databaseControl.editComplaint(0, 0, "Text", Boolean.TRUE);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme