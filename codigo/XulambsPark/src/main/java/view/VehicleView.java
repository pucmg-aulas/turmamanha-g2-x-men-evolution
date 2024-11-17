package view;

import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import model.Vehicle;

import java.util.Optional;

public class VehicleView {
    public Vehicle show() {
        String placa = showInputDialog("Enter vehicle plate:");
        if (placa != null && !placa.trim().isEmpty()) {
            return new Vehicle(placa, "", "", "", "");
        }
        return null;
    }

    private String showInputDialog(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input");
        dialog.setHeaderText(null);
        dialog.setContentText(message);

        ButtonType confirmButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel");
        dialog.getDialogPane().getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}