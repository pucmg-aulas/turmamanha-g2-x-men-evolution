package DAO;

import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRankingDAO {

    public List<ClientRanking> getClientRanking(int month, int year) {
        String sql = "SELECT client_name, client_cpf, SUM(amount_paid) AS total_arrecadado " +
                "FROM historical " +
                "WHERE EXTRACT(MONTH FROM start_time) = ? " +
                "AND EXTRACT(YEAR FROM start_time) = ? " +
                "GROUP BY client_name, client_cpf " +
                "ORDER BY total_arrecadado DESC";
        List<ClientRanking> ranking = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String clientName = rs.getString("client_name");
                String clientCpf = rs.getString("client_cpf");
                double totalArrecadado = rs.getDouble("total_arrecadado");
                ranking.add(new ClientRanking(clientName, clientCpf, totalArrecadado));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ranking;
    }

    public static class ClientRanking {
        private String clientName;
        private String clientCpf;
        private double totalArrecadado;

        public ClientRanking(String clientName, String clientCpf, double totalArrecadado) {
            this.clientName = clientName;
            this.clientCpf = clientCpf;
            this.totalArrecadado = totalArrecadado;
        }

        public String getClientName() {
            return clientName;
        }

        public String getClientCpf() {
            return clientCpf;
        }

        public double getTotalArrecadado() {
            return totalArrecadado;
        }
    }
}