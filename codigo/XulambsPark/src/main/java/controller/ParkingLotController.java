// src/main/java/controller/ParkingLotController.java
package controller;

import DAO.ParkingLotDAO;
import model.ITipoVaga;
import model.ParkingLot;
import model.ParkingSpot;
import model.Vehicle;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ParkingLotController {
    private Map<String, ParkingLot> parkingLots;
    private ParkingLotDAO parkingLotDAO;

    public ParkingLotController() {
        this.parkingLotDAO = new ParkingLotDAO();
        this.parkingLotDAO.loadFromFile();
        this.parkingLots = new LinkedHashMap<>(this.parkingLotDAO.findAll());
    }

    public void addParkingLot(String name, int numberOfSpots, ITipoVaga tipoVaga) {
        ParkingLot parkingLot = new ParkingLot(name, numberOfSpots, tipoVaga);
        parkingLots.put(name, parkingLot);
        parkingLotDAO.save(parkingLot);
    }

    public ParkingLot getParkingLot(String name) {
        return parkingLots.get(name);
    }

    public Set<String> getParkingLots() {
        return parkingLots.keySet();
    }

    public ParkingSpot getSpot(String parkingLotName, String spotId) {
        ParkingLot parkingLot = parkingLots.get(parkingLotName);
        return parkingLot != null ? parkingLot.getSpot(spotId) : null;
    }

    public boolean parkVehicle(String parkingLotName, String spotId, Vehicle vehicle) {
        ParkingSpot spot = getSpot(parkingLotName, spotId);
        if (spot != null && !spot.isOccupied()) {
            spot.occupy(vehicle);
            parkingLotDAO.save(parkingLots.get(parkingLotName));
            return true;
        }
        return false;
    }

    public boolean vacateSpot(String parkingLotName, String spotId) {
        ParkingSpot spot = getSpot(parkingLotName, spotId);
        if (spot != null && spot.isOccupied()) {
            spot.vacate();
            parkingLotDAO.save(parkingLots.get(parkingLotName));
            return true;
        }
        return false;
    }
}