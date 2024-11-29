package model;

import controller.AdminController;
import DAO.AmountRaisedDAO;
import DAO.AmountRaisedMonthDAO;
import DAO.AverageAmountRaisedDAO;
import DAO.ClientRankingDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        adminController = new AdminController();
    }

    @Test
    public void testValidateAdmin() {
        assertTrue(adminController.validateAdmin("12345"));
        assertFalse(adminController.validateAdmin("wrongpassword"));
    }

    @Test
    public void testGetTotalAmountRaised() {
        double totalAmountRaised = adminController.getTotalAmountRaised();
        assertTrue(totalAmountRaised >= 0);
    }

    @Test
    public void testGetAmountRaisedInMonth() {
        double amountRaisedInMonth = adminController.getAmountRaisedInMonth(1, 2023);
        assertTrue(amountRaisedInMonth >= 0);
    }

    @Test
    public void testGetAverageAmountRaised() {
        double averageAmountRaised = adminController.getAverageAmountRaised();
        assertTrue(averageAmountRaised >= 0);
    }

    @Test
    public void testGetClientRanking() {
        List<ClientRankingDAO.ClientRanking> ranking = adminController.getClientRanking(1, 2023);
        assertNotNull(ranking);
    }
}