package DAO;

import util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientHistoricalDAO {
    private Connection connection;

    public ClientHistoricalDAO() {
        try {
            this.connection = DatabaseUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public List<ClientHistorical> getClientHistorical(String clientCpf, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<ClientHistorical> historicalList = new ArrayList<>();
        String sql = "SELECT vehicle_plate, spot_id, parking_lot_name, start_time, end_time, amount_paid FROM public.historical WHERE client_cpf = ? AND start_time >= ? AND end_time <= ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, clientCpf);
            stmt.setDate(2, java.sql.Date.valueOf(startDate));
            stmt.setDate(3, java.sql.Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ClientHistorical historical = new ClientHistorical(
                        rs.getString("vehicle_plate"),
                        rs.getString("spot_id"),
                        rs.getString("parking_lot_name"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time"),
                        rs.getDouble("amount_paid")
                );
                historicalList.add(historical);
            }
        }
        return historicalList;
    }

    public static class ClientHistorical {
        private String vehiclePlate;
        private String spotId;
        private String parkingLotName;
        private java.sql.Timestamp startTime;
        private java.sql.Timestamp endTime;
        private double amountPaid;

        public ClientHistorical(String vehiclePlate, String spotId, String parkingLotName, java.sql.Timestamp startTime, java.sql.Timestamp endTime, double amountPaid) {
            this.vehiclePlate = vehiclePlate;
            this.spotId = spotId;
            this.parkingLotName = parkingLotName;
            this.startTime = startTime;
            this.endTime = endTime;
            this.amountPaid = amountPaid;
        }

        // Getters
        public String getVehiclePlate() { return vehiclePlate; }
        public String getSpotId() { return spotId; }
        public String getParkingLotName() { return parkingLotName; }
        public java.sql.Timestamp getStartTime() { return startTime; }
        public java.sql.Timestamp getEndTime() { return endTime; }
        public double getAmountPaid() { return amountPaid; }
    }
}