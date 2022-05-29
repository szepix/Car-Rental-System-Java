package com.pap_car_rental;

import javafx.scene.layout.BorderPane;

import java.sql.*;
import java.util.ArrayList;


public class DatabaseControl {
    Connection c = null;
    Statement stmt = null;

    DatabaseControl() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:file:src/main/resources/com/pap_car_rental/database.db");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //function to list all reservations from database
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
    //function to list all reservations of a user identified by ClientId from the database
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
    //function to list all cars from the database
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
    //function to list all clients from the database
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
    //function to add a car to the database
    public void addCar(String car_type, String brand, int cost, String model) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("INSERT INTO `CAR_LIST`(Id, Car_type, Brand, Cost, Model) VALUES (NULL, ?, ?, ?, ?)");
        //THAT IS NOT HOW DATES IN A DATABAASE SHOULD BE

        pstmt.setString(1, car_type);
        pstmt.setString(2, brand);
        pstmt.setInt(3, cost);
        pstmt.setString(4, model);
        pstmt.executeUpdate();
    }
    //function to add a client to the database
    public void addClient(String login, String password) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("INSERT INTO `CLIENT_LIST`(Id, Login, Password) VALUES (NULL, ?, ?)");
        pstmt.setString(1, login);
        pstmt.setString(2, password);
        pstmt.executeUpdate();
    }
    //function to edit selected client info
    public void editClient(int id, String login, String password) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("UPDATE `CLIENT_LIST`SET Login = ?, Password = ? WHERE Id = ?");
        pstmt.setString(1, login);
        pstmt.setString(2, password);
        pstmt.setInt(3, id);
        pstmt.executeUpdate();
    }
    //function to edit selected car info
    public void editCar(Car car) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("UPDATE `CAR_LIST`SET Car_type = ?, Brand = ?, Cost = ?, Model = ? WHERE Id = ?");
        pstmt.setString(1, car.Car_type);
        pstmt.setString(2, car.Brand);
        pstmt.setString(3, String.valueOf(car.Cost));
        pstmt.setString(4, car.Model);
        pstmt.setString(5, String.valueOf(car.id));
        pstmt.executeUpdate();
    }
    //function to mark a car as rented
    public void rentCar(int reservationId) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("UPDATE `RESERVATIONS`SET Rented = TRUE WHERE Id = ?");
        pstmt.setInt(1, reservationId);
        pstmt.executeUpdate();
    }
    //function to add a reservation to the database
    public void addReservation(Date DateFrom, Date DateTo, int ClientId, int CarId) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("INSERT INTO `RESERVATIONS`(Id, DateFrom, DateTo, ClientId, CarId, Rented) VALUES (NULL, ?, ?, ?, ?, FALSE)");
        pstmt.setDate(1, DateFrom);
        pstmt.setDate(2, DateTo);
        pstmt.setInt(3, ClientId);
        pstmt.setInt(4, CarId);
        pstmt.executeUpdate();
    }
    //function to remove reservations from the system
    public void removeReservation(int ReservationId) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("DELETE FROM `RESERVATIONS` WHERE Id = ?");
        pstmt.setInt(1, ReservationId);
        pstmt.executeUpdate();
    }
    //function to find a client by their id
    public Client findClientById(int clientId) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM CLIENT_LIST WHERE Id = ?");
        pstmt.setInt(1, clientId);
        ResultSet rs = pstmt.executeQuery();
        int id = rs.getInt("Id");
        String login = rs.getString("Login");
        String password = rs.getString("Password");
        return new Client(id, login, password);
    }
    //function to find a client by their username
    public Client findClientByName(String clientName) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM CLIENT_LIST WHERE Login = ?");
        pstmt.setString(1, clientName);
        ResultSet rs = pstmt.executeQuery();
        int id = rs.getInt("Id");
        String login = rs.getString("Login");
        String password = rs.getString("Password");
        return new Client(id, login, password);
    }
    //function to find a car using their id
    public Car findCar(int carId) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM CAR_LIST WHERE Id = ?");
        pstmt.setInt(1, carId);
        ResultSet rs = pstmt.executeQuery();
        int id = rs.getInt("Id");
        int cost = rs.getInt("Cost");
        String carType = rs.getString("Car_type");
        String Brand = rs.getString("Brand");
        String Model = rs.getString("Model");
        return new Car(id, carType, Brand, cost, Model);
    }
    //function to find reservations using their id
    public Reservation findReservation(int resId) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM RESERVATIONS WHERE Id = ?");
        pstmt.setInt(1, resId);
        ResultSet rs = pstmt.executeQuery();
        int id = rs.getInt("Id");
        int clientId = rs.getInt("ClientId");
        int carId = rs.getInt("CarId");
        boolean rented = rs.getBoolean("Rented");
        Date dateFrom = rs.getDate("DateFrom");
        Date dateTo = rs.getDate("DateTo");
        return new Reservation(id, dateFrom, dateTo, clientId, carId, rented);
    }


    public ArrayList<Complaint> listComplaints() throws SQLException {
        this.stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM COMPLAINTS");
        ArrayList<Complaint> complaints_list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("Id");
            int ClientId = rs.getInt("ClientId");
            String Text = rs.getString("Text");
            boolean resolved = rs.getBoolean("Resolved");
            Complaint new_complaint = new Complaint(id, ClientId, Text, resolved);
            complaints_list.add(new_complaint);
        }
        return complaints_list;
    }

    public void addComplaint(int ClientId, String Text) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("INSERT INTO `COMPLAINTS`(Id, ClientId, Text, Resolved) VALUES (NULL, ?, ?, FALSE)");
        pstmt.setInt(1, ClientId);
        pstmt.setString(2, Text);
        pstmt.executeUpdate();
    }

    public ArrayList<Complaint> listComplaintsByClientId(int ClientId) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM COMPLAINTS WHERE ClientId = ?");
        pstmt.setInt(1, ClientId);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Complaint> complaints_list = new ArrayList<>();
        while(rs.next()) {
            int id = rs.getInt("Id");
            String Text = rs.getString("Text");
            boolean resolved = rs.getBoolean("Resolved");
            complaints_list.add(new Complaint(id, ClientId, Text, resolved));
        }
        return complaints_list;
    }
    public void editComplaint(int id, int ClientId, String Text, Boolean Resolved) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("UPDATE `COMPLAINTS` SET ClientId = ?, Text = ?, Resolved = ?  WHERE Id = ?");
        pstmt.setInt(1, ClientId);
        pstmt.setString(2, Text);
        pstmt.setBoolean(3, Resolved);
        pstmt.setInt(4, id);
        pstmt.executeUpdate();
    }
}
