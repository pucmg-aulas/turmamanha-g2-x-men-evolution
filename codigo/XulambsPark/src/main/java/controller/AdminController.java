package controller;

import java.sql.SQLException;
import java.util.List;

import DAO.AmountRaisedDAO;
import DAO.AmountRaisedMonthDAO;
import DAO.AverageAmountRaisedDAO;
import DAO.ClientRankingDAO;
import DAO.MostUsedSpotsDAO;
import DAO.RushHourDAO;

public class AdminController {
    private static final String ADMIN_PASSWORD = "12345";
    private AmountRaisedDAO amountRaisedDAO;
    private AmountRaisedMonthDAO amountRaisedMonthDAO;
    private AverageAmountRaisedDAO averageAmountRaisedDAO;
    private ClientRankingDAO clientRankingDAO;
    private MostUsedSpotsDAO mostUsedSpotsDAO;
    private RushHourDAO rushHourDAO;
    
    // Problemas de arquitetura 11 AdminController linha 22- Controller com múltiplas dependências instanciadas diretamente, dificultando testes e manutenção, seria recomendado usar container de injeção de dependência

    public AdminController() {
        this.amountRaisedDAO = new AmountRaisedDAO();
        this.amountRaisedMonthDAO = new AmountRaisedMonthDAO();
        this.averageAmountRaisedDAO = new AverageAmountRaisedDAO();
        this.clientRankingDAO = new ClientRankingDAO();
        this.mostUsedSpotsDAO = new MostUsedSpotsDAO();
        this.rushHourDAO = new RushHourDAO();
    }

    public boolean validateAdmin(String password) {
        return ADMIN_PASSWORD.equals(password);
    }

    public double getTotalAmountRaised() {
        return amountRaisedDAO.getTotalAmountRaised();
    }

    public double getAmountRaisedInMonth(int month, int year) {
        return amountRaisedMonthDAO.getAmountRaisedInMonth(month, year);
    }

    public double getAverageAmountRaised() {
        return averageAmountRaisedDAO.getAverageAmountRaised();
    }

    public List<ClientRankingDAO.ClientRanking> getClientRanking(int month, int year) {
        return clientRankingDAO.getClientRanking(month, year);
    }

    public List<MostUsedSpotsDAO.MostUsedSpot> getMostUsedSpots() throws SQLException {
        return mostUsedSpotsDAO.getMostUsedSpots();
    }

    public List<RushHourDAO.RushHour> getRushHours(String parkingLotName) {
        return rushHourDAO.getRushHours(parkingLotName);
    }
}