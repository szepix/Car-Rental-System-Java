package com.pap_car_rental;

import java.sql.*;
import java.util.ArrayList;


public class DatabaseControl {
    Connection c = null;
    Statement stmt = null;

    DatabaseControl() {
        try {
            Class.forName("org.sqlite.JDBC");
            String dir = System.getProperty("user.dir");
            c = DriverManager.getConnection("jdbc:sqlite:file:src/main/resources/com/pap_car_rental/database.db");
            System.out.println("Connected to DB");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ArrayList<Reservation> listReservations() throws SQLException {
        this.stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM RESERVATIONS");
        ArrayList<Reservation> reservation_list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("Id");
            Date dateFrom = rs.getDate("DateFrom");
            Date dateTo = rs.getDate("DateTo");
            int clientId = rs.getInt("ClientId");
            int carId = rs.getInt("CarId");
            boolean rented = rs.getBoolean("Rented");
            Reservation reservation = new Reservation(id, dateFrom, dateTo, clientId, carId, rented);
            reservation_list.add(reservation);
        }
        return reservation_list;
    }

    public ArrayList<Reservation> listClientReservations(int ClientId) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM RESERVATIONS WHERE ClientId = ?");
        pstmt.setInt(1, ClientId);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Reservation> reservation_list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("Id");
            Date dateFrom = rs.getDate("DateFrom");
            Date dateTo = rs.getDate("DateTo");
            int clientId = rs.getInt("ClientId");
            int carId = rs.getInt("CarId");
            boolean rented = rs.getBoolean("Rented");
            Reservation reservation = new Reservation(id, dateFrom, dateTo, clientId, carId, rented);
            reservation_list.add(reservation);
        }
        return reservation_list;
    }

    public ArrayList<Car> listCars() throws SQLException {
        this.stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM CAR_LIST");
        ArrayList<Car> car_list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("Id");
            String Car_type = rs.getString("Car_type");
            String Brand = rs.getString("Brand");
            int Cost = rs.getInt("Cost");
            String Model = rs.getString("Model");
            Car new_car = new Car(id, Car_type, Brand, Cost, Model);
            car_list.add(new_car);
        }
        return car_list;
    }

    public ArrayList<Client> listClients() throws SQLException {
        this.stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM CLIENT_LIST");
        ArrayList<Client> client_list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("Id");
            String login = rs.getString("Login");
            String password = rs.getString("Password");
            Client new_client = new Client(id, login, password);
            client_list.add(new_client);
        }
        return client_list;
    }

    public void addCar(String car_type, String brand, int cost, String model) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("INSERT INTO `CAR_LIST`(Id, Car_type, Brand, Cost, Model) VALUES (NULL, ?, ?, ?, ?)");
        //THAT IS NOT HOW DATES IN A DATABAASE SHOULD BE

        pstmt.setString(1, car_type);
        pstmt.setString(2, brand);
        pstmt.setInt(3, cost);
        pstmt.setString(4, model);
        pstmt.executeUpdate();
    }

    public void addClient(String login, String password) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("INSERT INTO `CLIENT_LIST`(Id, Login, Password) VALUES (NULL, ?, ?)");
        pstmt.setString(1, login);
        pstmt.setString(2, password);
        pstmt.executeUpdate();
    }

    public void editClient(int id, String login, String password) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("UPDATE `CLIENT_LIST`SET Login = ?, Password = ? WHERE Id = ?");
        pstmt.setString(1, login);
        pstmt.setString(2, password);
        pstmt.setInt(3, id);
        pstmt.executeUpdate();
    }

    public void editCar(Car car) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("UPDATE `CAR_LIST`SET Car_type = ?, Brand = ?, Cost = ?, Model = ? WHERE Id = ?");
        pstmt.setString(1, car.Car_type);
        pstmt.setString(2, car.Brand);
        pstmt.setString(3, String.valueOf(car.Cost));
        pstmt.setString(4, car.Model);
        pstmt.setString(5, String.valueOf(car.id));
        pstmt.executeUpdate();
    }
    public void rentCar(int reservationId) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("UPDATE `RESERVATIONS`SET Rented = TRUE WHERE Id = ?");
        pstmt.setInt(1, reservationId);
        pstmt.executeUpdate();
    }

    public void addReservation(Date DateFrom, Date DateTo, int ClientId, int CarId) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("INSERT INTO `RESERVATIONS`(Id, DateFrom, DateTo, ClientId, CarId, Rented) VALUES (NULL, ?, ?, ?, ?, FALSE)");
        pstmt.setDate(1, DateFrom);
        pstmt.setDate(2, DateTo);
        pstmt.setInt(3, ClientId);
        pstmt.setInt(4, CarId);
        pstmt.executeUpdate();
    }
    public void removeReservation(int ReservationId) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("DELETE FROM `RESERVATIONS` WHERE Id = ?");
        pstmt.setInt(1, ReservationId);
        pstmt.executeUpdate();
    }
}
