package controller;

import DAO.ClientHistoricalDAO;
import java.sql.SQLException;
import java.util.List;

public class ClientHistoricalController {
    private ClientHistoricalDAO clientHistoricalDAO;

    public ClientHistoricalController() {
        this.clientHistoricalDAO = new ClientHistoricalDAO();
    }

    public List<ClientHistoricalDAO.ClientHistorical> getClientHistorical(String clientCpf) throws SQLException {
        return clientHistoricalDAO.getClientHistorical(clientCpf);
    }
}