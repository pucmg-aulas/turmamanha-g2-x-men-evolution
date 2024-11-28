package DAO;

import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AmountRaisedMonthDAO {

    public double getAmountRaisedInMonth(int month, int year) {
        String sql = "SELECT SUM(amount_paid) AS arrecadacao_mes FROM historical WHERE EXTRACT(MONTH FROM start_time) = ? AND EXTRACT(YEAR FROM start_time) = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("arrecadacao_mes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}