package DAO;

import model.Client;
import java.io.*;
import java.util.*;

public class ClientDAO {
    private static final String FILE_PATH = "data/clients.txt";

    public void saveClients(Map<String, Client> clients) {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Client client : clients.values()) {
                    writer.write("ID: " + client.getId().toString().substring(0, 8) + "\n");
                    writer.write("Nome: " + client.getName() + "\n");
                    writer.write("CPF: " + client.getCpf() + "\n");
                    writer.write("\n"); // Add a blank line between clients
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Client> loadClients() {
        Map<String, Client> clients = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ")) {
                    String shortId = line.substring(4);
                    UUID id = UUID.fromString(shortId + "00000000-0000-0000-0000-000000000000".substring(shortId.length()));
                    String nome = reader.readLine().substring(6);
                    String cpf = reader.readLine().substring(5);
                    Client client = new Client(nome, id, cpf);
                    clients.put(shortId, client);
                    reader.readLine(); // Skip the blank line
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }
}