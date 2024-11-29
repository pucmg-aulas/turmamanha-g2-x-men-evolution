package DAO;

import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RushHourDAO {

    public List<RushHour> getRushHours(String parkingLotName) {
        String sql = "SELECT DATE_PART('hour', start_time) AS hora_movimentada, COUNT(*) AS quantidade_entradas " +
                "FROM historical " +
                "WHERE parking_lot_name = ? " +
                "GROUP BY DATE_PART('hour', start_time) " +
                "ORDER BY quantidade_entradas DESC";

        List<RushHour> rushHours = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, parkingLotName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RushHour rushHour = new RushHour(
                            rs.getInt("hora_movimentada"),
                            rs.getInt("quantidade_entradas")
                    );
                    rushHours.add(rushHour);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rushHours;
    }

    public static class RushHour {
        private int hour;
        private int entryCount;

        public RushHour(int hour, int entryCount) {
            this.hour = hour;
            this.entryCount = entryCount;
        }

        public int getHour() {
            return hour;
        }

        public int getEntryCount() {
            return entryCount;
        }
    }
}