package DAO;

import model.ParkingSpot;

import java.util.HashMap;
import java.util.Map;

public class ParkingSpotDAO {
    private Map<String, ParkingSpot> parkingSpots = new HashMap<>();

    public void save(ParkingSpot parkingSpot) {
        parkingSpots.put(parkingSpot.getId(), parkingSpot);
    }

    public ParkingSpot findById(String id) {
        return parkingSpots.get(id);
    }

    public Map<String, ParkingSpot> findAll() {
        return new HashMap<>(parkingSpots);
    }

    public void delete(String id) {
        parkingSpots.remove(id);
    }
}