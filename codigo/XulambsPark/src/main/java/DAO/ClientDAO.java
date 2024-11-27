package DAO;

import model.Client;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientDAO {
    public void saveClients(Map<String, Client> clients) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String deleteSQL = "DELETE FROM clients";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {
                deleteStmt.executeUpdate();
            }

            String insertSQL = "INSERT INTO clients (id, name, cpf) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
                for (Client client : clients.values()) {
                    insertStmt.setObject(1, client.getId());
                    insertStmt.setString(2, client.getName());
                    insertStmt.setString(3, client.getCpf());
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Client> loadClients() {
        Map<String, Client> clients = new HashMap<>();
        try (Connection conn = DatabaseUtil.getConnection()) {
            String selectSQL = "SELECT id, name, cpf FROM clients";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
                try (ResultSet rs = selectStmt.executeQuery()) {
                    while (rs.next()) {
                        UUID id = UUID.fromString(rs.getString("id"));
                        String name = rs.getString("name");
                        String cpf = rs.getString("cpf");
                        Client client = new Client(name, id, cpf);
                        clients.put(id.toString().substring(0, 8), client);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}