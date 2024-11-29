package DAO;

import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MostUsedSpotsDAO {

    public List<MostUsedSpot> getMostUsedSpots() {
        String sql = "SELECT spot_id, COUNT(*) AS ocupacoes " +
                "FROM historical " +
                "WHERE end_time IS NOT NULL " +
                "GROUP BY spot_id " +
                "ORDER BY ocupacoes DESC";

        List<MostUsedSpot> mostUsedSpots = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MostUsedSpot spot = new MostUsedSpot(
                        rs.getString("spot_id"),
                        rs.getInt("ocupacoes")
                );
                mostUsedSpots.add(spot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostUsedSpots;
    }

    public static class MostUsedSpot {
        private String spotId;
        private int ocupacoes;

        public MostUsedSpot(String spotId, int ocupacoes) {
            this.spotId = spotId;
            this.ocupacoes = ocupacoes;
        }

        public String getSpotId() {
            return spotId;
        }

        public int getOcupacoes() {
            return ocupacoes;
        }
    }
}