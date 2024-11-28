// HistoricalDAO.java
package DAO;

import model.Historical;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoricalDAO {

    public void save(Historical historical) {
        String sql = "INSERT INTO historical (client_cpf, client_name, vehicle_plate, spot_id, parking_lot_name, start_time, end_time, amount_paid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, historical.getClientCpf());
            stmt.setString(2, historical.getClientName());
            stmt.setString(3, historical.getVehiclePlate());
            stmt.setString(4, historical.getSpotId());
            stmt.setString(5, historical.getParkingLotName());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(historical.getStartTime()));
            stmt.setTimestamp(7, historical.getEndTime() != null ? java.sql.Timestamp.valueOf(historical.getEndTime()) : null);
            stmt.setDouble(8, historical.getAmountPaid());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Historical historical) {
        String sql = "UPDATE historical SET end_time = ?, amount_paid = ? WHERE client_cpf = ? AND vehicle_plate = ? AND spot_id = ? AND start_time = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, java.sql.Timestamp.valueOf(historical.getEndTime()));
            stmt.setDouble(2, historical.getAmountPaid());
            stmt.setString(3, historical.getClientCpf());
            stmt.setString(4, historical.getVehiclePlate());
            stmt.setString(5, historical.getSpotId());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(historical.getStartTime()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Historical findBySpotId(String spotId) {
        String sql = "SELECT * FROM historical WHERE spot_id = ? AND end_time IS NULL";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, spotId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Historical(
                        rs.getString("client_cpf"),
                        rs.getString("client_name"),
                        rs.getString("vehicle_plate"),
                        rs.getString("spot_id"),
                        rs.getString("parking_lot_name"),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time") != null ? rs.getTimestamp("end_time").toLocalDateTime() : null,
                        rs.getDouble("amount_paid")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}