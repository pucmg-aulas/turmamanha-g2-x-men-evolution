package controller;

import DAO.ParkingSpotDAO;
import model.ParkingSpot;

import java.util.Map;

public class ParkingSpotController {
    private ParkingSpotDAO parkingSpotDAO;

    public ParkingSpotController() {
        this.parkingSpotDAO = new ParkingSpotDAO();
    }

    public void saveParkingSpot(ParkingSpot parkingSpot) {
        parkingSpotDAO.save(parkingSpot);
    }

    public ParkingSpot findParkingSpotById(String id) {
        return parkingSpotDAO.findById(id);
    }

    public Map<String, ParkingSpot> findAllParkingSpots() {
        return parkingSpotDAO.findAll();
    }

    public void deleteParkingSpot(String id) {
        parkingSpotDAO.delete(id);
    }
}