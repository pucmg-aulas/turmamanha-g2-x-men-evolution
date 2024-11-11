package model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParkingLot {
    private String name;
    private Map<String, ParkingSpot> spots;
    private Map<String, Vehicle> vehicles; // Adiciona este campo

    public ParkingLot(String name, int numberOfSpots, ITipoVaga tipoVaga) {
        this.name = name;
        this.spots = new LinkedHashMap<>();
        this.vehicles = new LinkedHashMap<>(); // Inicializa o mapa de veículos
        char row = 'A';
        int spotNumber = 1;

        for (int i = 1; i <= numberOfSpots; i++) {
            String spotId = row + String.format("%01d", spotNumber);
            spots.put(spotId, new ParkingSpot(spotId, tipoVaga));
            spotNumber++;
            if (spotNumber > 9) {
                spotNumber = 1;
                row++;
            }
        }
    }

    public String getName() {
        return name;
    }

    public ParkingSpot getSpot(String spotId) {
        return spots.get(spotId);
    }

    public Map<String, ParkingSpot> getSpots() {
        return spots;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getPlaca(), vehicle);
    }

    public Map<String, Vehicle> getVehicles() {
        return vehicles;
    }

    public boolean parkVehicle(String placa, String spotId) {
        Vehicle vehicle = vehicles.get(placa);
        ParkingSpot spot = spots.get(spotId);
        if (vehicle != null && spot != null && !spot.isOccupied()) {
            spot.occupy(vehicle);
            return true;
        }
        return false;
    }
}