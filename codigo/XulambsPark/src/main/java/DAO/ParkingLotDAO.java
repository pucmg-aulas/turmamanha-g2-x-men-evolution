// DAO/ParkingLotDAO.java
package DAO;

import model.*;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParkingLotDAO {
    private static final String FILE_NAME = "data/parkingLots.txt";
    private Map<String, ParkingLot> parkingLots = new LinkedHashMap<>();

    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ParkingLot: ")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int numberOfSpots = Integer.parseInt(parts[1].split(": ")[1]);
                    ParkingLot parkingLot = new ParkingLot(name, numberOfSpots, new VagaRegular()); // Default type
                    for (int i = 0; i < numberOfSpots; i++) {
                        line = reader.readLine();
                        parts = line.split(", ");
                        String spotId = parts[0].split(": ")[1];
                        String typeName = parts[1].split(": ")[1];
                        ITipoVaga type = getTypeByName(typeName);
                        if (type == null) {
                            throw new IllegalArgumentException("Unknown type: " + typeName);
                        }
                        ParkingSpot spot = new ParkingSpot(spotId, type);
                        if (!"empty".equals(parts[2].split(": ")[1])) {
                            String placa = parts[2].split(": ")[1];
                            Vehicle vehicle = new Vehicle(placa, "", "", "", "");
                            spot.occupy(vehicle);
                            parkingLot.addVehicle(vehicle); // Adiciona o veículo ao estacionamento
                        }
                        parkingLot.getSpots().put(spotId, spot);
                    }
                    parkingLots.put(name, parkingLot);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ITipoVaga getTypeByName(String typeName) {
        switch (typeName) {
            case "Regular":
                return new VagaRegular();
            case "VIP":
                return new VagaVIP();
            case "Idoso":
                return new VagaIdoso();
            case "PCD":
                return new VagaPCD();
            default:
                return null;
        }
    }

    public void save(ParkingLot parkingLot) {
        parkingLots.put(parkingLot.getName(), parkingLot);
        saveToFile();
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (ParkingLot parkingLot : parkingLots.values()) {
                writer.write("ParkingLot: " + parkingLot.getName() + ", Spots: " + parkingLot.getSpots().size());
                writer.newLine();
                for (ParkingSpot spot : parkingLot.getSpots().values()) {
                    writer.write("  Spot: " + spot.getId() + ", Type: " + spot.getType().getTipo() + ", Vehicle: " + (spot.isOccupied() ? spot.getVehicle().getPlaca() : "empty"));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, ParkingLot> findAll() {
        return parkingLots;
    }
}