package controller;

import DAO.VehicleDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import model.Vehicle;

import java.util.List;
import java.util.Optional;

public class VehicleController {
    private VehicleDAO vehicleDAO;

    public VehicleController() {
        this.vehicleDAO = new VehicleDAO();
    }

    public void registerVehicle(Vehicle vehicle) {
        vehicleDAO.save(vehicle);
        System.out.println("Vehicle registered: " + vehicle.getPlaca());
    }

    public List<Vehicle> getVehicles() {
        return vehicleDAO.findAll();
    }

    public String showPlateInputDialog() {
        return showInputDialog("Enter vehicle plate:");
    }

    public Vehicle showAdditionalInfo(String placa, String owner, String cpf) {
        boolean addAdditionalInfo = showConfirmDialog("Do you want to add additional vehicle information?", "Yes", "No");
        if (addAdditionalInfo) {
            String model = showInputDialog("Enter vehicle model:");
            String color = showInputDialog("Enter vehicle color:");

            if (model != null && !model.trim().isEmpty() &&
                    color != null && !color.trim().isEmpty()) {
                return new Vehicle(placa, model, color, owner, cpf);
            }
        }
        return new Vehicle(placa, "", "", owner, cpf);
    }

    private boolean showConfirmDialog(String message, String positiveButtonText, String negativeButtonText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType positiveButton = new ButtonType(positiveButtonText);
        ButtonType negativeButton = new ButtonType(negativeButtonText);
        alert.getButtonTypes().setAll(positiveButton, negativeButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == positiveButton;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String showInputDialog(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input");
        dialog.setHeaderText(null);
        dialog.setContentText(message);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}