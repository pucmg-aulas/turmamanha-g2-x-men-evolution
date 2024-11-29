package controller;

import DAO.ParkingSpotDAO;
import model.ParkingSpot;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    public double calcularTarifa(String spotId, LocalDateTime endTime) {
        ParkingSpot spot = parkingSpotDAO.findById(spotId);
        if (spot != null && spot.isOccupied() && spot.getStartTime() != null) {
            long minutes = ChronoUnit.MINUTES.between(spot.getStartTime(), endTime);
            double tarifaBase = 4.0 * Math.ceil(minutes / 15.0);
            tarifaBase = Math.min(tarifaBase, 50.0);

            switch (spot.getType()) {
                case REGULAR:
                    break;
                case VIP:
                    tarifaBase *= 1.2;
                    break;
                case IDOSO:
                    tarifaBase *= 0.8;
                    break;
                case PCD:
                    tarifaBase *= 0.7;
                    break;
            }

            return tarifaBase;
        }
        return 0;
    }
}