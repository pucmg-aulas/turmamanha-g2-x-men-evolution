package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DatabaseUtil;

public class VehicleClientDAO {

    public List<Vehicle> getVehiclesByCpf(String cpf) {
        String sql = "SELECT placa, model, color FROM vehicles WHERE cpf = ?";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vehicle vehicle = new Vehicle(
                            rs.getString("placa"),
                            rs.getString("model"),
                            rs.getString("color")
                    );
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
// Problemas de arquitetura 16 VehicleClientDAO linha 37- Classe interna Vehicle duplicando funcionalidade do modelo principal, violando DRY e causando confusão, seria recomendado usar DTOs
    public static class Vehicle {
        private String placa;
        private String model;
        private String color;

        public Vehicle(String placa, String model, String color) {
            this.placa = placa;
            this.model = model;
            this.color = color;
        }

        public String getPlaca() {
            return placa;
        }

        public String getModel() {
            return model;
        }

        public String getColor() {
            return color;
        }
    }
}