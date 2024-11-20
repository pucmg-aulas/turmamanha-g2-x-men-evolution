package controller;

import DAO.ClientDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import model.Client;

import java.util.*;

public class ClientController {
    private Map<String, Client> clients;
    private ClientDAO clientDAO;

    public ClientController() {
        this.clientDAO = new ClientDAO();
        this.clients = clientDAO.loadClients();
    }

    public void registerClient(String name, String cpf, boolean isAnonymous) {
        Client client;
        UUID uuid = UUID.randomUUID();
        String shortId = uuid.toString().substring(0, 8);
        if (isAnonymous) {
            client = new Client("Anonymous", uuid, "");
        } else {
            client = new Client(name, uuid, cpf);
        }
        clients.put(shortId, client);
        clientDAO.saveClients(clients);
        System.out.println("Client registered: " + client.getName());
    }

    public List<Client> getClients() {
        return new ArrayList<>(clients.values());
    }

    public Collection<String> getClientNames() {
        List<String> clientNames = new ArrayList<>();
        for (Client client : clients.values()) {
            clientNames.add(client.getName());
        }
        return clientNames;
    }

    public Client getClientByName(String name) {
        for (Client client : clients.values()) {
            if (client.getName().equals(name)) {
                return client;
            }
        }
        return null;
    }

    public Client getClientByCpf(String cpf) {
        for (Client client : clients.values()) {
            if (client.getCpf().equals(cpf)) {
                return client;
            }
        }
        return null;
    }

    public void updateClient(Client client) {
        String shortId = client.getId().toString().substring(0, 8);
        clients.put(shortId, client);
        clientDAO.saveClients(clients);
    }

    public Client show() {
        boolean isExistingClient = showConfirmDialog("Is this vehicle associated with an existing client?", "Yes", "No");
        if (isExistingClient) {
            String clientCpf = showInputDialog("Enter client CPF:");
            Client client = getClientByCpf(clientCpf);
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
                registerClient(name, cpf, false);
                return getClientByCpf(cpf);
            } else {
                registerClient("Anonymous", "", true);
                return getClientByName("Anonymous");
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