// src/main/java/model/Historical.java
package model;

import java.time.LocalDateTime;

public class Historical {
    private String clientCpf;
    private String clientName;
    private String vehiclePlate;
    private String spotId;
    private String parkingLotName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double amountPaid;

    public Historical(String clientCpf, String clientName, String vehiclePlate, String spotId, String parkingLotName, LocalDateTime startTime, LocalDateTime endTime, double amountPaid) {
        this.clientCpf = clientCpf;
        this.clientName = clientName;
        this.vehiclePlate = vehiclePlate;
        this.spotId = spotId;
        this.parkingLotName = parkingLotName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.amountPaid = amountPaid;
    }

    public String getClientCpf() {
        return clientCpf;
    }

    public String getClientName() {
        return clientName;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public String getSpotId() {
        return spotId;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }
}