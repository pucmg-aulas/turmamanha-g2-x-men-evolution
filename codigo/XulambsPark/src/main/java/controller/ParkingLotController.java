package controller;

import DAO.ParkingLotDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import model.*;
import view.ClientView;
import view.VehicleView;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ParkingLotController {
    private Map<String, ParkingLot> parkingLots;
    private ParkingLotDAO parkingLotDAO;
    private ClientController clientController;
    private VehicleController vehicleController;

    public ParkingLotController() {
        this.parkingLotDAO = new ParkingLotDAO();
        this.parkingLotDAO.loadFromFile();
        this.parkingLots = new LinkedHashMap<>(this.parkingLotDAO.findAll());
        this.clientController = new ClientController();
        this.vehicleController = new VehicleController();
    }

    public void handleButtonAction(ParkingSpot spot, Button button) {
        if (!spot.isOccupied()) {
            String placa = new VehicleView(vehicleController).showPlateInputDialog();
            if (placa != null && !placa.trim().isEmpty()) {
                Client client = new ClientView(clientController).show();
                if (client != null) {
                    Vehicle vehicle = new VehicleView(vehicleController).showAdditionalInfo(placa, client.getName(), client.getCpf());
                    if (vehicle != null) {
                        client.addVehicle(vehicle);
                        clientController.updateClient(client);
                        vehicleController.registerVehicle(vehicle);
                        if (parkVehicle(spot.getId(), vehicle)) {
                            spot.occupy(vehicle);
                            button.setStyle("-fx-background-color: red");
                        } else {
                            showAlert("Failed to park vehicle.");
                        }
                    }
                }
            }
        } else {
            boolean confirm = showConfirmDialog("Vacate this spot?");
            if (confirm) {
                double valor = calcularTarifa(spot.getId());
                showAlert("Total amount due: " + valor);
                if (vacateSpot(spot.getId())) {
                    spot.vacate();
                    button.setStyle("-fx-background-color: " + toHexString(spot.getType().getColor()));
                } else {
                    showAlert("Failed to vacate spot.");
                }
            }
        }
    }

    private boolean showConfirmDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addParkingLot(String name, int numberOfSpots, SpotType tipoVaga) {
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

    public boolean parkVehicle(String spotId, Vehicle vehicle) {
        for (ParkingLot parkingLot : parkingLots.values()) {
            ParkingSpot spot = parkingLot.getSpot(spotId);
            if (spot != null && !spot.isOccupied()) {
                spot.occupy(vehicle);
                parkingLotDAO.save(parkingLot);
                return true;
            }
        }
        return false;
    }

    public boolean vacateSpot(String spotId) {
        for (ParkingLot parkingLot : parkingLots.values()) {
            ParkingSpot spot = parkingLot.getSpot(spotId);
            if (spot != null && spot.isOccupied()) {
                spot.vacate();
                parkingLotDAO.save(parkingLot);
                return true;
            }
        }
        return false;
    }

    public double calcularTarifa(String spotId) {
        for (ParkingLot parkingLot : parkingLots.values()) {
            ParkingSpot spot = parkingLot.getSpot(spotId);
            if (spot != null && spot.isOccupied()) {
                long minutes = ChronoUnit.MINUTES.between(spot.getStartTime(), LocalDateTime.now());
                System.out.println("Minutes parked: " + minutes);
                double tarifaBase = 4.0 * Math.ceil(minutes / 15.0);
                System.out.println("Base tariff before cap: " + tarifaBase);
                tarifaBase = Math.min(tarifaBase, 50.0);
                System.out.println("Final tariff: " + tarifaBase);
                return tarifaBase;
            }
        }
        return 0;
    }

    public Client getClientByName(String name) {
        return clientController.getClientByName(name);
    }

    public void registerClient(String name, String cpf, boolean isAnonymous) {
        clientController.registerClient(name, cpf, isAnonymous);
    }

    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}