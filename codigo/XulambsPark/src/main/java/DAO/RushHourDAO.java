package DAO;

import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RushHourDAO {

    public List<RushHour> getRushHours() {
        String sql = "SELECT parking_lot_name, DATE_PART('hour', start_time) AS hora_movimentada, COUNT(*) AS quantidade_entradas " +
                "FROM historical " +
                "GROUP BY parking_lot_name, DATE_PART('hour', start_time) " +
                "ORDER BY parking_lot_name ASC, quantidade_entradas DESC";

        List<RushHour> rushHours = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RushHour rushHour = new RushHour(
                        rs.getString("parking_lot_name"),
                        rs.getInt("hora_movimentada"),
                        rs.getInt("quantidade_entradas")
                );
                rushHours.add(rushHour);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rushHours;
    }

    public static class RushHour {
        private String parkingLotName;
        private int hour;
        private int entryCount;

        public RushHour(String parkingLotName, int hour, int entryCount) {
            this.parkingLotName = parkingLotName;
            this.hour = hour;
            this.entryCount = entryCount;
        }

        public String getParkingLotName() {
            return parkingLotName;
        }

        public int getHour() {
            return hour;
        }

        public int getEntryCount() {
            return entryCount;
        }
    }
}