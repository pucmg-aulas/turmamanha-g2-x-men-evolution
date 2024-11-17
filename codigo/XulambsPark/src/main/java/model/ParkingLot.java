package model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParkingLot {
    private String name;
    private int numberOfSpots;
    private SpotType defaultSpotType;
    private Map<String, ParkingSpot> spots;

    public ParkingLot(String name, int numberOfSpots, SpotType defaultSpotType) {
        this.name = name;
        this.numberOfSpots = numberOfSpots;
        this.defaultSpotType = defaultSpotType;
        this.spots = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getNumberOfSpots() {
        return numberOfSpots;
    }

    public SpotType getDefaultSpotType() {
        return defaultSpotType;
    }

    public Map<String, ParkingSpot> getSpots() {
        return spots;
    }

    public ParkingSpot getSpot(String spotId) {
        return spots.get(spotId);
    }

    public void addVehicle(Vehicle vehicle) {
        // Implementation to add vehicle to the parking lot
    }
}