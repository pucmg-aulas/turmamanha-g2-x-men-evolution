package controller;

import DAO.HistoricalDAO;
import DAO.ParkingLotDAO;
import exceptions.ClientRegistrationException;
import exceptions.ClientRetrievalException;
import exceptions.VehicleNotFoundException;
import exceptions.VehicleRegistrationException;
import exceptions.VehicleUpdateException;
import javafx.scene.control.Button;
import model.*;
import util.ColorUtils;
import util.DialogUtils;
import view.ClientView;
import view.VehicleView;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParkingLotController {
    private Map<String, ParkingLot> parkingLots;
    private ParkingLotDAO parkingLotDAO;
    private HistoricalDAO historicalDAO;
    private ClientController clientController;
    private VehicleController vehicleController;
    private ParkingSpotController parkingSpotController;

    public ParkingLotController() {
        this.parkingLotDAO = new ParkingLotDAO();
        this.historicalDAO = new HistoricalDAO();
        this.parkingLotDAO.loadFromDatabase();
        this.parkingLots = new LinkedHashMap<>(this.parkingLotDAO.findAll());
        this.clientController = new ClientController();
        this.vehicleController = new VehicleController();
        this.parkingSpotController = new ParkingSpotController();
    }

    public Map<String, ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public ParkingLot getParkingLot(String name) {
        return parkingLots.get(name);
    }

    public void handleButtonAction(ParkingSpot spot, Button button) {
        if (!spot.isOccupied()) {
            String placa = new VehicleView(vehicleController).showPlateInputDialog();
            if (placa != null && !placa.trim().isEmpty()) {
                try {
                    Vehicle vehicle = vehicleController.getVehicleByPlaca(placa);
                    Client client = clientController.getClientByCpf(vehicle.getCpf());
                    if (client != null) {
                        parkVehicle(spot, button, vehicle, client);
                    }
                } catch (VehicleNotFoundException e) {
                    DialogUtils.showAlert("Vehicle not found. Please register the vehicle.");
                    Client client = new ClientView(clientController).show();
                    if (client != null) {
                        Vehicle vehicle = new VehicleView(vehicleController).showAdditionalInfo(placa, client.getName(), client.getCpf());
                        if (vehicle != null) {
                            parkVehicle(spot, button, vehicle, client);
                        }
                    }
                } catch (ClientRetrievalException e) {
                    DialogUtils.showAlert("Client not found. Please register the client.");
                    Client client = new ClientView(clientController).show();
                    if (client != null) {
                        Vehicle vehicle = new VehicleView(vehicleController).showAdditionalInfo(placa, client.getName(), client.getCpf());
                        if (vehicle != null) {
                            parkVehicle(spot, button, vehicle, client);
                        }
                    }
                }
            }
        } else {
            boolean confirm = DialogUtils.showConfirmDialog("Vacate this spot?");
            if (confirm) {
                vacateSpot(spot, button);
            }
        }
    }

    private void parkVehicle(ParkingSpot spot, Button button, Vehicle vehicle, Client client) {
        client.addVehicle(vehicle);
        try {
            clientController.updateClient(client);
        } catch (ClientRegistrationException e) {
            DialogUtils.showAlert("Error updating client: " + e.getMessage());
            return;
        }
        try {
            vehicleController.registerVehicle(vehicle);
        } catch (VehicleRegistrationException | VehicleUpdateException e) {
            DialogUtils.showAlert("Error registering/updating vehicle: " + e.getMessage());
            return;
        }
        if (parkingSpotController.parkVehicle(spot.getId(), vehicle)) {
            spot.occupy(vehicle);
            button.setStyle("-fx-background-color: red");
            parkingSpotController.saveParkingSpot(spot);

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
            DialogUtils.showAlert("Failed to park vehicle.");
        }
    }

    private void vacateSpot(ParkingSpot spot, Button button) {
        ParkingSpot updatedSpot = parkingSpotController.findParkingSpotById(spot.getId());
        LocalDateTime endTime = LocalDateTime.now();
        double valor = parkingSpotController.calcularTarifa(updatedSpot.getId(), endTime);
        DialogUtils.showAlert("Total amount due: " + valor);
        Vehicle vehicle = updatedSpot.getVehicle();
        if (vehicle != null) {
            Historical historical = historicalDAO.findBySpotId(updatedSpot.getId());
            if (historical != null) {
                historical.setEndTime(endTime);
                historical.setAmountPaid(valor);
                historicalDAO.update(historical);
            }
        }

        if (parkingSpotController.vacateSpot(updatedSpot.getId())) {
            updatedSpot.vacate();
            button.setStyle("-fx-background-color: " + ColorUtils.toHexString(updatedSpot.getType().getColor()));
            parkingSpotController.saveParkingSpot(updatedSpot);
            spot.vacate(); // Ensure the original spot is also updated
        } else {
            DialogUtils.showAlert("Failed to vacate spot.");
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
}