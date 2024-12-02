// src/main/java/view/VehicleView.java
package view;

import controller.VehicleController;
import exceptions.VehicleNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Vehicle;

import java.util.Optional;

public class VehicleView {
    private VehicleController vehicleController;

    public VehicleView(VehicleController vehicleController) {
        this.vehicleController = vehicleController;
    }

    public String showPlateInputDialog() {
        return showInputDialog("Enter vehicle plate:");
    }

    public Vehicle showAdditionalInfo(String placa, String owner, String cpf) {
        try {
            Vehicle existingVehicle = vehicleController.getVehicleByPlaca(placa);
            if (existingVehicle != null) {
                return existingVehicle;
            }
        } catch (VehicleNotFoundException e) {
            showAlert("Vehicle not found: " + e.getMessage());
        }

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
        Stage inputStage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label label = new Label(message);
        TextField textField = new TextField();
        Button submitButton = new Button("Submit");

        final String[] input = new String[1];
        submitButton.setOnAction(e -> {
            input[0] = textField.getText();
            inputStage.close();
        });

        vbox.getChildren().addAll(label, textField, submitButton);

        Scene scene = new Scene(vbox, 300, 200);
        inputStage.setScene(scene);
        inputStage.setTitle("XulambsPark");
        inputStage.showAndWait();

        return input[0];
    }
}