package DAO;

import model.ParkingLot;
import model.ParkingSpot;
import model.SpotType;
import model.Vehicle;
import util.DatabaseUtil;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParkingLotDAO {
    private Map<String, ParkingLot> parkingLots = new LinkedHashMap<>();

    public void loadFromDatabase() {
        String sql = "SELECT * FROM parking_lots";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                int numberOfSpots = rs.getInt("number_of_spots");
                ParkingLot parkingLot = new ParkingLot(name, numberOfSpots, SpotType.REGULAR); // Default type

                String spotSql = "SELECT * FROM parking_spots WHERE parking_lot_name = ?";
                try (PreparedStatement spotStmt = conn.prepareStatement(spotSql)) {
                    spotStmt.setString(1, name);
                    ResultSet spotRs = spotStmt.executeQuery();
                    while (spotRs.next()) {
                        String spotId = spotRs.getString("id");
                        SpotType type = SpotType.valueOf(spotRs.getString("type").toUpperCase());
                        ParkingSpot spot = new ParkingSpot(spotId, type);
                        String vehiclePlaca = spotRs.getString("vehicle_placa");
                        if (vehiclePlaca != null) {
                            Vehicle vehicle = new Vehicle(vehiclePlaca, "", "", "", "");
                            spot.occupy(vehicle);
                            parkingLot.addVehicle(vehicle);
                        }
                        parkingLot.getSpots().put(spotId, spot);
                    }
                }
                parkingLots.put(name, parkingLot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(ParkingLot parkingLot) {
        String sql = "INSERT INTO parking_lots (name, number_of_spots) VALUES (?, ?) ON CONFLICT (name) DO UPDATE SET number_of_spots = EXCLUDED.number_of_spots";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, parkingLot.getName());
            stmt.setInt(2, parkingLot.getSpots().size());
            stmt.executeUpdate();

            String spotSql = "INSERT INTO parking_spots (id, type, vehicle_placa, parking_lot_name) VALUES (?, ?, ?, ?) ON CONFLICT (id) DO UPDATE SET type = EXCLUDED.type, vehicle_placa = EXCLUDED.vehicle_placa, parking_lot_name = EXCLUDED.parking_lot_name";
            try (PreparedStatement spotStmt = conn.prepareStatement(spotSql)) {
                for (ParkingSpot spot : parkingLot.getSpots().values()) {
                    spotStmt.setString(1, spot.getId());
                    spotStmt.setString(2, spot.getType().name());
                    spotStmt.setString(3, spot.isOccupied() ? spot.getVehicle().getPlaca() : null);
                    spotStmt.setString(4, parkingLot.getName());
                    spotStmt.addBatch();
                }
                spotStmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, ParkingLot> findAll() {
        return parkingLots;
    }
}