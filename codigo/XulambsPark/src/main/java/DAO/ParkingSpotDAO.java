package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import model.ITipoVaga;
import model.ParkingSpot;
import model.SpotType;
import model.VagaIdoso;
import model.VagaPCD;
import model.VagaRegular;
import model.VagaVIP;
import model.Vehicle;
import util.DatabaseUtil;

public class ParkingSpotDAO {

    public void save(ParkingSpot parkingSpot) {
        String sql = "INSERT INTO parking_spots (id, position, type, vehicle_placa, start_time) VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET position = EXCLUDED.position, type = EXCLUDED.type, vehicle_placa = EXCLUDED.vehicle_placa, start_time = EXCLUDED.start_time";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, parkingSpot.getId());
            stmt.setString(2, parkingSpot.getPosition());
            stmt.setString(3, parkingSpot.getType().name());
            stmt.setString(4, parkingSpot.getVehicle() != null ? parkingSpot.getVehicle().getPlaca() : null);
            stmt.setTimestamp(5, parkingSpot.getStartTime() != null ? Timestamp.valueOf(parkingSpot.getStartTime()) : null);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ParkingSpot findById(String id) {
        String sql = "SELECT * FROM parking_spots WHERE id = ?";
        ParkingSpot parkingSpot = null;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String position = rs.getString("position");
                String type = rs.getString("type");
                String vehiclePlaca = rs.getString("vehicle_placa");
                Timestamp startTime = rs.getTimestamp("start_time");
                ITipoVaga tipoVaga = convertSpotTypeToITipoVaga(SpotType.valueOf(type.toUpperCase()));
                parkingSpot = new ParkingSpot(id, position, SpotType.valueOf(type.toUpperCase()), tipoVaga);
                if (vehiclePlaca != null) {
                    parkingSpot.occupy(new Vehicle(vehiclePlaca, "", "", "", ""));
                }
                if (startTime != null) {
                    parkingSpot.setStartTime(startTime.toLocalDateTime());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parkingSpot;
    }
// Problemas de arquitetura 5 ParkingLotDAO linha 74- padrão DAO não pode ter lógica de negócio interna, seria recomendado criar uma factory para dividir essa responsabilidade e respeitar os padrões de baixo acoplamento do MVC
    public Map<String, ParkingSpot> findAll() {
        String sql = "SELECT * FROM parking_spots";
        Map<String, ParkingSpot> parkingSpots = new HashMap<>();

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String position = rs.getString("position");
                String type = rs.getString("type");
                String vehiclePlaca = rs.getString("vehicle_placa");
                Timestamp startTime = rs.getTimestamp("start_time");
                ITipoVaga tipoVaga = convertSpotTypeToITipoVaga(SpotType.valueOf(type.toUpperCase()));
                ParkingSpot parkingSpot = new ParkingSpot(id, position, SpotType.valueOf(type.toUpperCase()), tipoVaga);
                if (vehiclePlaca != null) {
                    parkingSpot.occupy(new Vehicle(vehiclePlaca, "", "", "", ""));
                }
                if (startTime != null) {
                    parkingSpot.setStartTime(startTime.toLocalDateTime());
                }
                parkingSpots.put(id, parkingSpot);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parkingSpots;
    }

    public void delete(String id) {
        String sql = "DELETE FROM parking_spots WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//Problemas de arquitetura 1- padrão DAO não pode ter lógica de negócio interna, seria recomendado criar uma factory para dividir essa responsabilidade
    private ITipoVaga convertSpotTypeToITipoVaga(SpotType type) {
        switch (type) {
            case IDOSO:
                return new VagaIdoso();
            case PCD:
                return new VagaPCD();
            case VIP:
                return new VagaVIP();
            case REGULAR:
            default:
                return new VagaRegular();
        }
    }
}