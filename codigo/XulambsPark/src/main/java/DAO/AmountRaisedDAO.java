package DAO;

import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AmountRaisedDAO {

    public double getTotalAmountRaised() {
        String sql = "SELECT SUM(amount_paid) AS total_arrecadado FROM historical";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total_arrecadado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}