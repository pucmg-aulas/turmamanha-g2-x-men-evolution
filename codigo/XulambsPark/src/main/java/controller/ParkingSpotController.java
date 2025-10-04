package controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import DAO.ParkingSpotDAO;
import model.ParkingSpot;
import model.Vehicle;

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
// Problemas de arquitetura 10 ParkingSpotController linha 33- Lógica de cálculo de tarifa no Controller, deveria estar em uma classe de domínio ou serviço especializado

    public double calcularTarifa(String spotId, LocalDateTime endTime) {
        ParkingSpot spot = parkingSpotDAO.findById(spotId);
        if (spot != null && spot.isOccupied() && spot.getStartTime() != null) {
            long minutes = ChronoUnit.MINUTES.between(spot.getStartTime(), endTime);
            double tarifaBase = 4.0 * Math.ceil(minutes / 15.0);
            tarifaBase = Math.min(tarifaBase, 50.0);

            return spot.getTipoVaga().calcularTarifa(tarifaBase);
        }
        return 0;
    }

    public boolean parkVehicle(String spotId, Vehicle vehicle) {
        ParkingSpot spot = parkingSpotDAO.findById(spotId);
        if (spot != null && !spot.isOccupied()) {
            spot.occupy(vehicle);
            parkingSpotDAO.save(spot);
            return true;
        }
        return false;
    }

    public boolean vacateSpot(String spotId) {
        ParkingSpot spot = parkingSpotDAO.findById(spotId);
        if (spot != null && spot.isOccupied()) {
            spot.vacate();
            parkingSpotDAO.save(spot);
            return true;
        }
        return false;
    }
}