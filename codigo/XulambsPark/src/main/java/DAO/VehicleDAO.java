// src/main/java/DAO/VehicleDAO.java
package DAO;

import model.Vehicle;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public void save(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (placa, model, color, owner, cpf) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getPlaca());
            stmt.setString(2, vehicle.getModel());
            stmt.setString(3, vehicle.getColor());
            stmt.setString(4, vehicle.getOwner());
            stmt.setString(5, vehicle.getCpf());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Vehicle vehicle) {
        String sql = "UPDATE vehicles SET model = ?, color = ?, owner = ?, cpf = ? WHERE placa = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getModel());
            stmt.setString(2, vehicle.getColor());
            stmt.setString(3, vehicle.getOwner());
            stmt.setString(4, vehicle.getCpf());
            stmt.setString(5, vehicle.getPlaca());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vehicle findByPlaca(String placa) {
        String sql = "SELECT * FROM vehicles WHERE placa = ?";
        Vehicle vehicle = null;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String model = rs.getString("model");
                String color = rs.getString("color");
                String owner = rs.getString("owner");
                String cpf = rs.getString("cpf");
                vehicle = new Vehicle(placa, model, color, owner, cpf);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicle;
    }

    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String placa = rs.getString("placa");
                String model = rs.getString("model");
                String color = rs.getString("color");
                String owner = rs.getString("owner");
                String cpf = rs.getString("cpf");
                vehicles.add(new Vehicle(placa, model, color, owner, cpf));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
}