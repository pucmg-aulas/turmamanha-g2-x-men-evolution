package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Problemas de arquitetura 6- credenciais de banco de dados hardcoded, seria recomendado usar variáveis de ambiente ou um arquivo de configuração externo
public class DatabaseUtil {
    private static final String URL = "jdbc:postgresql://laboratorio.postgres.database.azure.com:5432/XulambsPark";
    private static final String USER = "LPM";
    private static final String PASSWORD = "Laboratorio_10";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void testConnection() {
        System.out.println("Attempting to connect to the database...");
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}