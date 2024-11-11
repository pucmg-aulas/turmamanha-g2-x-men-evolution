package model;

public class ParkingSpot {
    private String id;
    private ITipoVaga type;
    private Vehicle vehicle;

    public ParkingSpot(String id, ITipoVaga type) {
        this.id = id;
        this.type = type;
        this.vehicle = null;
    }

    public String getId() {
        return id;
    }

    public ITipoVaga getType() {
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
    }

    public void vacate() {
        this.vehicle = null;
    }
}