package view;

import controller.ClientController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import model.Client;

import java.util.Optional;

public class ClientView {
    private ClientController clientController;

    public ClientView(ClientController clientController) {
        this.clientController = clientController;
    }

    public Client show() {
        boolean isExistingClient = showConfirmDialog("Is this vehicle associated with an existing client?", "Yes", "No");
        if (isExistingClient) {
            String clientCpf = showInputDialog("Enter client CPF:");
            Client client = clientController.getClientByCpf(clientCpf);
            if (client != null) {
                return client;
            } else {
                showAlert("Client not found.");
                return null;
            }
        } else {
            boolean registerNewClient = showConfirmDialog("Do you want to register a new client?", "Yes", "No");
            if (registerNewClient) {
                String name = showInputDialog("Enter client name:");
                String cpf = showInputDialog("Enter client CPF:");
                clientController.registerClient(name, cpf, false);
                return clientController.getClientByCpf(cpf);
            } else {
                clientController.registerClient("Anonymous", "", true);
                return clientController.getClientByName("Anonymous");
            }
        }
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