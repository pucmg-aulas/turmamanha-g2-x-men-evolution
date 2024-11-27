package controller;

import DAO.ClientDAO;
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
}