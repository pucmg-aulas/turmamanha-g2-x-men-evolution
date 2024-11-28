package DAO;

import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AverageAmountRaisedDAO {

    public double getAverageAmountRaised() {
        String sql = "SELECT AVG(amount_paid) AS valor_medio_utilizacao FROM historical";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("valor_medio_utilizacao");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}