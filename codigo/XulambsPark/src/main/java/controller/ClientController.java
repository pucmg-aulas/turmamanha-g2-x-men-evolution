// Java
package controller;

import DAO.ClientDAO;
import exceptions.ClientRegistrationException;
import exceptions.ClientRetrievalException;
import model.Client;
import view.ClientView;

import java.util.*;

public class ClientController {
    private Map<String, Client> clients;
    private ClientDAO clientDAO;

    public ClientController() {
        this.clientDAO = new ClientDAO();
        this.clients = clientDAO.loadClients();
    }

    public void registerClient(String name, String cpf, boolean isAnonymous) throws ClientRegistrationException {
        try {
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
        } catch (Exception e) {
            throw new ClientRegistrationException("Error registering client", e);
        }
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

    public Client getClientByName(String name) throws ClientRetrievalException {
        try {
            for (Client client : clients.values()) {
                if (client.getName().equals(name)) {
                    return client;
                }
            }
            throw new ClientRetrievalException("Client not found with name: " + name);
        } catch (Exception e) {
            throw new ClientRetrievalException("Error retrieving client by name", e);
        }
    }

    public Client getClientByCpf(String cpf) throws ClientRetrievalException {
        try {
            for (Client client : clients.values()) {
                if (client.getCpf().equals(cpf)) {
                    return client;
                }
            }
            throw new ClientRetrievalException("Client not found with CPF: " + cpf);
        } catch (Exception e) {
            throw new ClientRetrievalException("Error retrieving client by CPF", e);
        }
    }

    public void updateClient(Client client) throws ClientRegistrationException {
        try {
            String shortId = client.getId().toString().substring(0, 8);
            clients.put(shortId, client);
            clientDAO.saveClients(clients);
        } catch (Exception e) {
            throw new ClientRegistrationException("Error updating client", e);
        }
    }

    public Client handleClientInteraction(ClientView view) {
        try {
            boolean isExistingClient = view.showConfirmDialog("Is this vehicle associated with an existing client?", "Yes", "No");
            if (isExistingClient) {
                String clientCpf = view.showInputDialog("Enter client CPF:");
                return getClientByCpf(clientCpf);
            } else {
                boolean registerNewClient = view.showConfirmDialog("Do you want to register a new client?", "Yes", "No");
                if (registerNewClient) {
                    String name = view.showInputDialog("Enter client name:");
                    String cpf = view.showInputDialog("Enter client CPF:");
                    registerClient(name, cpf, false);
                    return getClientByCpf(cpf);
                } else {
                    registerClient("Anonymous", "", true);
                    return getClientByName("Anonymous");
                }
            }
        } catch (ClientRetrievalException | ClientRegistrationException e) {
            view.showAlert(e.getMessage());
            return null;
        }
    }
}