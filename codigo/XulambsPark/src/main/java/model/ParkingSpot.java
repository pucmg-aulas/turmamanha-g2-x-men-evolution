// src/main/java/model/ParkingSpot.java
package model;

import java.time.LocalDateTime;

public class ParkingSpot {
    private String id;
    private String position;
    private SpotType type;
    private Vehicle vehicle;
    private LocalDateTime startTime;

    public ParkingSpot(String id, String position, SpotType type) {
        this.id = id;
        this.position = position;
        this.type = type;
        this.vehicle = null;
        this.startTime = null;
    }

    public String getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public SpotType getType() {
        return type;
    }

    public boolean isOccupied() {
        return vehicle != null;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void occupy(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.startTime = LocalDateTime.now();
    }

    public void vacate() {
        this.vehicle = null;
        this.startTime = null;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}