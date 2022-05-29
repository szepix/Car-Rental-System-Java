package com.pap_car_rental;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class DatabaseControlTest {

    private DatabaseControl databaseControlUnderTest;

    @Before
    public void setUp() throws SQLException {

        databaseControlUnderTest = new DatabaseControl();
        databaseControlUnderTest.c = mock(Connection.class);
        databaseControlUnderTest.stmt = mock(Statement.class);
        //Statement stmt = databaseControlUnderTest.c.createStatement();
        //stmt.executeUpdate("CREATE TABLE CAR_LIST" + "(Id	INTEGER,"+ "Car_type	TEXT,"+ "Brand	TEXT," + "Cost	INTEGER," + "Model	TEXT," + "PRIMARY KEY(Id)" + ")");
    }

    @Test
    public void testListReservations() throws Exception {
        // Setup
        // Configure Connection.createStatement(...).
        final Statement mockStatement = mock(Statement.class);
        when(databaseControlUnderTest.c.createStatement()).thenReturn(mockStatement);

        // Configure Statement.executeQuery(...).
        final ResultSet mockResultSet = mock(ResultSet.class);
        when(databaseControlUnderTest.stmt.executeQuery("SELECT * FROM RESERVATIONS")).thenReturn(mockResultSet);

        // Run the test
        final ArrayList<Reservation> result = databaseControlUnderTest.listReservations();

        // Verify the results
        verify(mockStatement).close();
        verify(mockResultSet).close();
    }

    @Test
    public void testListReservations_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.createStatement()).thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.listReservations()).isInstanceOf(SQLException.class);
    }

    @Test
    public void testListReservations_StatementThrowsSQLException() throws Exception {
        // Setup
        // Configure Connection.createStatement(...).
        final Statement mockStatement = mock(Statement.class);
        when(databaseControlUnderTest.c.createStatement()).thenReturn(mockStatement);

        when(databaseControlUnderTest.stmt.executeQuery("SELECT * FROM RESERVATIONS")).thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.listReservations()).isInstanceOf(SQLException.class);
        verify(mockStatement).close();
    }

    @Test
    public void testListClientReservations() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM RESERVATIONS WHERE ClientId = ?"))
                .thenReturn(mockPreparedStatement);

        // Run the test
        final ArrayList<Reservation> result = databaseControlUnderTest.listClientReservations(0);

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testListClientReservations_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM RESERVATIONS WHERE ClientId = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.listClientReservations(0)).isInstanceOf(SQLException.class);
    }

    @Test
    public void testListCars() throws Exception {
        // Setup
        // Configure Connection.createStatement(...).
        final Statement mockStatement = mock(Statement.class);
        when(databaseControlUnderTest.c.createStatement()).thenReturn(mockStatement);

        // Configure Statement.executeQuery(...).
        final ResultSet mockResultSet = mock(ResultSet.class);
        when(databaseControlUnderTest.stmt.executeQuery("SELECT * FROM CAR_LIST")).thenReturn(mockResultSet);

        // Run the test
        final ArrayList<Car> result = databaseControlUnderTest.listCars();

        // Verify the results
        verify(mockStatement).close();
        verify(mockResultSet).close();
    }

    @Test
    public void testListCars_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.createStatement()).thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.listCars()).isInstanceOf(SQLException.class);
    }

    @Test
    public void testListCars_StatementThrowsSQLException() throws Exception {
        // Setup
        // Configure Connection.createStatement(...).
        final Statement mockStatement = mock(Statement.class);
        when(databaseControlUnderTest.c.createStatement()).thenReturn(mockStatement);

        when(databaseControlUnderTest.stmt.executeQuery("SELECT * FROM CAR_LIST")).thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.listCars()).isInstanceOf(SQLException.class);
        verify(mockStatement).close();
    }

    @Test
    public void testListClients() throws Exception {
        // Setup
        // Configure Connection.createStatement(...).
        final Statement mockStatement = mock(Statement.class);
        when(databaseControlUnderTest.c.createStatement()).thenReturn(mockStatement);

        // Configure Statement.executeQuery(...).
        final ResultSet mockResultSet = mock(ResultSet.class);
        when(databaseControlUnderTest.stmt.executeQuery("SELECT * FROM CLIENT_LIST")).thenReturn(mockResultSet);

        // Run the test
        final ArrayList<Client> result = databaseControlUnderTest.listClients();

        // Verify the results
        verify(mockStatement).close();
        verify(mockResultSet).close();
    }

    @Test
    public void testListClients_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.createStatement()).thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.listClients()).isInstanceOf(SQLException.class);
    }

    @Test
    public void testListClients_StatementThrowsSQLException() throws Exception {
        // Setup
        // Configure Connection.createStatement(...).
        final Statement mockStatement = mock(Statement.class);
        when(databaseControlUnderTest.c.createStatement()).thenReturn(mockStatement);

        when(databaseControlUnderTest.stmt.executeQuery("SELECT * FROM CLIENT_LIST")).thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.listClients()).isInstanceOf(SQLException.class);
        verify(mockStatement).close();
    }

    @Test
    public void testAddCar() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement(
                "INSERT INTO `CAR_LIST`(Id, Car_type, Brand, Cost, Model) VALUES (NULL, ?, ?, ?, ?)"))
                .thenReturn(mockPreparedStatement);

        // Run the test
        databaseControlUnderTest.addCar("car_type", "brand", 0, "model");

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testAddCar_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "INSERT INTO `CAR_LIST`(Id, Car_type, Brand, Cost, Model) VALUES (NULL, ?, ?, ?, ?)"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.addCar("car_type", "brand", 0, "model"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    public void testAddClient() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement(
                "INSERT INTO `CLIENT_LIST`(Id, Login, Password) VALUES (NULL, ?, ?)"))
                .thenReturn(mockPreparedStatement);

        // Run the test
        databaseControlUnderTest.addClient("login", "password");

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testAddClient_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "INSERT INTO `CLIENT_LIST`(Id, Login, Password) VALUES (NULL, ?, ?)"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.addClient("login", "password"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    public void testEditClient() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement(
                "UPDATE `CLIENT_LIST`SET Login = ?, Password = ? WHERE Id = ?")).thenReturn(mockPreparedStatement);

        // Run the test
        databaseControlUnderTest.editClient(0, "login", "password");

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testEditClient_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "UPDATE `CLIENT_LIST`SET Login = ?, Password = ? WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.editClient(0, "login", "password"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    public void testEditCar() throws Exception {
        // Setup
        final Car car = null;

        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement(
                "UPDATE `CAR_LIST`SET Car_type = ?, Brand = ?, Cost = ?, Model = ? WHERE Id = ?"))
                .thenReturn(mockPreparedStatement);

        // Run the test
        databaseControlUnderTest.editCar(car);

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testEditCar_ConnectionThrowsSQLException() throws Exception {
        // Setup
        final Car car = null;
        when(databaseControlUnderTest.c.prepareStatement(
                "UPDATE `CAR_LIST`SET Car_type = ?, Brand = ?, Cost = ?, Model = ? WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.editCar(car)).isInstanceOf(SQLException.class);
    }

    @Test
    public void testRentCar() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement(
                "UPDATE `RESERVATIONS`SET Rented = TRUE WHERE Id = ?")).thenReturn(mockPreparedStatement);

        // Run the test
        databaseControlUnderTest.rentCar(0);

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testRentCar_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "UPDATE `RESERVATIONS`SET Rented = TRUE WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.rentCar(0)).isInstanceOf(SQLException.class);
    }

    @Test
    public void testAddReservation() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement(
                "INSERT INTO `RESERVATIONS`(Id, DateFrom, DateTo, ClientId, CarId, Rented) VALUES (NULL, ?, ?, ?, ?, FALSE)"))
                .thenReturn(mockPreparedStatement);

        // Run the test
        databaseControlUnderTest.addReservation((java.sql.Date) Date.from(Instant.now()), (java.sql.Date) Date.from(Instant.now()), 0, 0);

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testAddReservation_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "INSERT INTO `RESERVATIONS`(Id, DateFrom, DateTo, ClientId, CarId, Rented) VALUES (NULL, ?, ?, ?, ?, FALSE)"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.addReservation((java.sql.Date) Date.from(Instant.now()), (java.sql.Date) Date.from(Instant.now()), 0, 0));
    }

    @Test
    public void testRemoveReservation() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement("DELETE FROM `RESERVATIONS` WHERE Id = ?"))
                .thenReturn(mockPreparedStatement);

        // Run the test
        databaseControlUnderTest.removeReservation(1);

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testRemoveReservation_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("DELETE FROM `RESERVATIONS` WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.removeReservation(0)).isInstanceOf(SQLException.class);
    }

    @Test
    public void testFindClientById() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM CLIENT_LIST WHERE Id = ?"))
                .thenReturn(mockPreparedStatement);

        // Run the test
        final Client result = databaseControlUnderTest.findClientById(0);

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testFindClientById_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM CLIENT_LIST WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.findClientById(0)).isInstanceOf(SQLException.class);
    }

    @Test
    public void testFindClientByName() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM CLIENT_LIST WHERE Login = ?"))
                .thenReturn(mockPreparedStatement);

        // Run the test
        final Client result = databaseControlUnderTest.findClientByName("clientName");

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testFindClientByName_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM CLIENT_LIST WHERE Login = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.findClientByName("clientName"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    public void testFindCar() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM CAR_LIST WHERE Id = ?"))
                .thenReturn(mockPreparedStatement);

        // Run the test
        final Car result = databaseControlUnderTest.findCar(0);

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testFindCar_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM CAR_LIST WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.findCar(0)).isInstanceOf(SQLException.class);
    }

    @Test
    public void testFindReservation() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM RESERVATIONS WHERE Id = ?"))
                .thenReturn(mockPreparedStatement);

        // Run the test
        final Reservation result = databaseControlUnderTest.findReservation(0);

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testFindReservation_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM RESERVATIONS WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.findReservation(0)).isInstanceOf(SQLException.class);
    }

    @Test
    public void testListComplaints() throws Exception {
        // Setup
        // Configure Connection.createStatement(...).
        final Statement mockStatement = mock(Statement.class);
        when(databaseControlUnderTest.c.createStatement()).thenReturn(mockStatement);

        // Configure Statement.executeQuery(...).
        final ResultSet mockResultSet = mock(ResultSet.class);
        when(databaseControlUnderTest.stmt.executeQuery("SELECT * FROM COMPLAINTS")).thenReturn(mockResultSet);

        // Run the test
        final ArrayList<Complaint> result = databaseControlUnderTest.listComplaints();

        // Verify the results
        verify(mockStatement).close();
        verify(mockResultSet).close();
    }

    @Test
    public void testListComplaints_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.createStatement()).thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.listComplaints()).isInstanceOf(SQLException.class);
    }

    @Test
    public void testListComplaints_StatementThrowsSQLException() throws Exception {
        // Setup
        // Configure Connection.createStatement(...).
        final Statement mockStatement = mock(Statement.class);
        when(databaseControlUnderTest.c.createStatement()).thenReturn(mockStatement);

        when(databaseControlUnderTest.stmt.executeQuery("SELECT * FROM COMPLAINTS")).thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.listComplaints()).isInstanceOf(SQLException.class);
        verify(mockStatement).close();
    }

    @Test
    public void testAddComplaint() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement(
                "INSERT INTO `COMPLAINTS`(Id, ClientId, Text, Resolved) VALUES (NULL, ?, ?, FALSE)"))
                .thenReturn(mockPreparedStatement);

        // Run the test
        databaseControlUnderTest.addComplaint(0, "Text");

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testAddComplaint_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "INSERT INTO `COMPLAINTS`(Id, ClientId, Text, Resolved) VALUES (NULL, ?, ?, FALSE)"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.addComplaint(0, "Text")).isInstanceOf(SQLException.class);
    }

    @Test
    public void testListComplaintsByClientId() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM COMPLAINTS WHERE ClientId = ?"))
                .thenReturn(mockPreparedStatement);
        //databaseControlUnderTest.addComplaint(1, "Text");
        // Run the test
        final ArrayList<Complaint> result = databaseControlUnderTest.listComplaintsByClientId(1);

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testListComplaintsByClientId_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement("SELECT * FROM COMPLAINTS WHERE ClientId = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.listComplaintsByClientId(0)).isInstanceOf(SQLException.class);
    }

    @Test
    public void testEditComplaint() throws Exception {
        // Setup
        // Configure Connection.prepareStatement(...).
        final PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(databaseControlUnderTest.c.prepareStatement(
                "UPDATE `COMPLAINTS` SET ClientId = ?, Text = ?, Resolved = ?  WHERE Id = ?"))
                .thenReturn(mockPreparedStatement);

        // Run the test
        databaseControlUnderTest.editComplaint(0, 0, "Text", false);

        // Verify the results
        verify(mockPreparedStatement).close();
    }

    @Test
    public void testEditComplaint_ConnectionThrowsSQLException() throws Exception {
        // Setup
        when(databaseControlUnderTest.c.prepareStatement(
                "UPDATE `COMPLAINTS` SET ClientId = ?, Text = ?, Resolved = ?  WHERE Id = ?"))
                .thenThrow(SQLException.class);

        // Run the test
        assertThatThrownBy(() -> databaseControlUnderTest.editComplaint(0, 0, "Text", false))
                .isInstanceOf(SQLException.class);
    }
}
