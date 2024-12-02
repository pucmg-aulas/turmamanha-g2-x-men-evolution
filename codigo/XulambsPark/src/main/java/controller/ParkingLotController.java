// src/main/java/controller/ParkingLotController.java
package controller;

import DAO.HistoricalDAO;
import DAO.ParkingLotDAO;
import DAO.ParkingSpotDAO;
import exceptions.ClientRegistrationException;
import exceptions.ClientRetrievalException;
import exceptions.VehicleRegistrationException;
import exceptions.VehicleUpdateException;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import model.*;
import view.ClientView;
import view.VehicleView;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ParkingLotController {
    private Map<String, ParkingLot> parkingLots;
    private ParkingLotDAO parkingLotDAO;
    private ParkingSpotDAO parkingSpotDAO;
    private HistoricalDAO historicalDAO;
    private ClientController clientController;
    private VehicleController vehicleController;
    private ParkingSpotController parkingSpotController;

    public ParkingLotController() {
        this.parkingLotDAO = new ParkingLotDAO();
        this.parkingSpotDAO = new ParkingSpotDAO();
        this.historicalDAO = new HistoricalDAO();
        this.parkingLotDAO.loadFromDatabase();
        this.parkingLots = new LinkedHashMap<>(this.parkingLotDAO.findAll());
        this.clientController = new ClientController();
        this.vehicleController = new VehicleController();
        this.parkingSpotController = new ParkingSpotController(); // Initialize the ParkingSpotController
    }

    public void registerParkingLot(String name, Map<String, SpotType> spots) {
        ParkingLot parkingLot = new ParkingLot(name);
        for (Map.Entry<String, SpotType> entry : spots.entrySet()) {
            String position = entry.getKey();
            SpotType type = entry.getValue();
            String uniqueSpotId = name + "_" + position;
            parkingLot.getSpots().put(uniqueSpotId, new ParkingSpot(uniqueSpotId, position, type));
        }
        parkingLotDAO.save(parkingLot);
        parkingLots.put(name, parkingLot);
        System.out.println("Parking lot registered: " + name);
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
                        try {
                            clientController.updateClient(client);
                        } catch (ClientRegistrationException e) {
                            showAlert("Error updating client: " + e.getMessage());
                            return;
                        }
                        try {
                            vehicleController.registerVehicle(vehicle);
                        } catch (VehicleRegistrationException | VehicleUpdateException e) {
                            showAlert("Error registering/updating vehicle: " + e.getMessage());
                            return;
                        }
                        if (parkVehicle(spot.getId(), vehicle)) {
                            spot.occupy(vehicle);
                            button.setStyle("-fx-background-color: red");
                            parkingSpotDAO.save(spot);

                            Historical historical = new Historical(
                                    vehicle.getCpf(),
                                    vehicle.getOwner(),
                                    vehicle.getPlaca(),
                                    spot.getId(),
                                    getParkingLotNameBySpotId(spot.getId()),
                                    spot.getStartTime(),
                                    null,
                                    0.0
                            );
                            historicalDAO.save(historical);
                        } else {
                            showAlert("Failed to park vehicle.");
                        }
                    }
                }
            }
        } else {
            boolean confirm = showConfirmDialog("Vacate this spot?");
            if (confirm) {
                // Retrieve the updated ParkingSpot from the database
                ParkingSpot updatedSpot = parkingSpotController.findParkingSpotById(spot.getId());
                LocalDateTime endTime = LocalDateTime.now();
                double valor = parkingSpotController.calcularTarifa(updatedSpot.getId(), endTime);
                showAlert("Total amount due: " + valor);
                Vehicle vehicle = updatedSpot.getVehicle();
                if (vehicle != null) {
                    Historical historical = historicalDAO.findBySpotId(updatedSpot.getId());
                    if (historical != null) {
                        historical.setEndTime(endTime);
                        historical.setAmountPaid(valor);
                        historicalDAO.update(historical);
                    }
                }

                if (vacateSpot(updatedSpot.getId())) {
                    updatedSpot.vacate();
                    button.setStyle("-fx-background-color: " + toHexString(updatedSpot.getType().getColor()));
                    parkingSpotDAO.save(updatedSpot);
                } else {
                    showAlert("Failed to vacate spot.");
                }
            }
        }
    }

    private String getParkingLotNameBySpotId(String spotId) {
        for (ParkingLot parkingLot : parkingLots.values()) {
            if (parkingLot.getSpots().containsKey(spotId)) {
                return parkingLot.getName();
            }
        }
        return null;
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
                parkingSpotDAO.save(spot); // Save the spot to the database
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
                parkingSpotDAO.save(spot); // Update the spot in the database
                return true;
            }
        }
        return false;
    }

    public Client getClientByName(String name) {
        try {
            return clientController.getClientByName(name);
        } catch (ClientRetrievalException e) {
            showAlert("Error retrieving client by name: " + e.getMessage());
            return null;
        }
    }

    public void registerClient(String name, String cpf, boolean isAnonymous) {
        try {
            clientController.registerClient(name, cpf, isAnonymous);
        } catch (ClientRegistrationException e) {
            showAlert("Error registering client: " + e.getMessage());
        }
    }

    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}