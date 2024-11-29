package controller;

import DAO.ClientHistoricalDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ClientHistoricalController {
    private ClientHistoricalDAO clientHistoricalDAO;

    public ClientHistoricalController() {
        this.clientHistoricalDAO = new ClientHistoricalDAO();
    }

    public List<ClientHistoricalDAO.ClientHistorical> getClientHistorical(String clientCpf, LocalDate startDate, LocalDate endDate) throws SQLException {
        return clientHistoricalDAO.getClientHistorical(clientCpf, startDate, endDate);
    }
}