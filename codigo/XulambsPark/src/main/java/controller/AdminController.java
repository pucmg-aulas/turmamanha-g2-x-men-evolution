package controller;

import DAO.AmountRaisedDAO;
import DAO.AmountRaisedMonthDAO;
import DAO.AverageAmountRaisedDAO;
import DAO.ClientRankingDAO;

import java.util.List;

public class AdminController {
    private static final String ADMIN_PASSWORD = "12345";
    private AmountRaisedDAO amountRaisedDAO;
    private AmountRaisedMonthDAO amountRaisedMonthDAO;
    private AverageAmountRaisedDAO averageAmountRaisedDAO;
    private ClientRankingDAO clientRankingDAO;

    public AdminController() {
        this.amountRaisedDAO = new AmountRaisedDAO();
        this.amountRaisedMonthDAO = new AmountRaisedMonthDAO();
        this.averageAmountRaisedDAO = new AverageAmountRaisedDAO();
        this.clientRankingDAO = new ClientRankingDAO();
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
}