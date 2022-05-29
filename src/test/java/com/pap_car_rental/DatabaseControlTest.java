package com.pap_car_rental;

import com.pap_car_rental.Car;
import com.pap_car_rental.DatabaseControl;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class DatabaseControlTest {

    private DatabaseControl databaseControlUnderTest;

    @Before
    public void setUp() {
        databaseControlUnderTest = new DatabaseControl();
        databaseControlUnderTest.c = mock(Connection.class);
        databaseControlUnderTest.stmt = mock(Statement.class);
    }


    @Test
    public void testListReservations_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.createStatement()).thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.listReservations());
    }



    @Test
    public void testListClientReservations_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM RESERVATIONS WHERE ClientId = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.listClientReservations(0));
    }

    @Test
    public void testListCars_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.createStatement()).thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.listCars());
    }


    @Test
    public void testListClients_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.createStatement()).thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.listClients());
    }



    @Test
    public void testAddCar_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "INSERT INTO `CAR_LIST`(Id, Car_type, Brand, Cost, Model) VALUES (NULL, ?, ?, ?, ?)"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.addCar("car_type", "brand", 0, "model"));
    }

    @Test
    public void testAddClient_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "INSERT INTO `CLIENT_LIST`(Id, Login, Password) VALUES (NULL, ?, ?)"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.addClient("login", "password"));
    }


    @Test
    public void testEditClient_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "UPDATE `CLIENT_LIST`SET Login = ?, Password = ? WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.editClient(0, "login", "password"));
    }

    @Test
    public void testEditCar_ConnectionThrowsSQLException() throws Exception {
        // Setup
        final Car car = null;
        when(databaseControlUnderTest.c.prepareStatement(
                "UPDATE `CAR_LIST`SET Car_type = ?, Brand = ?, Cost = ?, Model = ? WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.editCar(car));
    }


    @Test
    public void testRentCar_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "UPDATE `RESERVATIONS`SET Rented = TRUE WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.rentCar(0));
    }


    @Test
    public void testAddReservation_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "INSERT INTO `RESERVATIONS`(Id, DateFrom, DateTo, ClientId, CarId, Rented) VALUES (NULL, ?, ?, ?, ?, FALSE)"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.addReservation(
                Date.valueOf(LocalDate.of(2020, 1, 1)), Date.valueOf(LocalDate.of(2020, 1, 1)), 0, 0));
    }



    @Test
    public void testRemoveReservation_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("DELETE FROM `RESERVATIONS` WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.removeReservation(0));
    }

    @Test
    public void testFindClientById_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM CLIENT_LIST WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.findClientById(0));
    }


    @Test
    public void testFindClientByName_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM CLIENT_LIST WHERE Login = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.findClientByName("clientName"));
    }



    @Test
    public void testFindCar_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM CAR_LIST WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.findCar(0));
    }


    @Test
    public void testFindReservation_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM RESERVATIONS WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.findReservation(0));
    }


    @Test
    public void testListComplaints_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.createStatement()).thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.listComplaints());
    }



    @Test
    public void testAddComplaint_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "INSERT INTO `COMPLAINTS`(Id, ClientId, Text, Resolved) VALUES (NULL, ?, ?, FALSE)"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.addComplaint(0, "Text"));
    }


    @Test
    public void testListComplaintsByClientId_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM COMPLAINTS WHERE ClientId = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.listComplaintsByClientId(0));
    }



    @Test
    public void testEditComplaint_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "UPDATE `COMPLAINTS` SET ClientId = ?, Text = ?, Resolved = ?  WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThrows(SQLException.class, () -> databaseControlUnderTest.editComplaint(0, 0, "Text", false));
    }


}


