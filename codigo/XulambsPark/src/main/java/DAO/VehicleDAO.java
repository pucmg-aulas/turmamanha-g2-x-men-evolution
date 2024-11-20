package DAO;

import model.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {
    private List<Vehicle> vehicles = new ArrayList<>();
    private static final String FILE_NAME = "data/vehicles.txt";

    public void save(Vehicle vehicle) {
        vehicles.add(vehicle);
        saveToFile();
    }

    public List<Vehicle> findAll() {
        return new ArrayList<>(vehicles);
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Vehicle vehicle : vehicles) {
                writer.write("Placa: " + vehicle.getPlaca());
                writer.newLine();
                writer.write("Model: " + vehicle.getModel());
                writer.newLine();
                writer.write("Color: " + vehicle.getColor());
                writer.newLine();
                writer.write("Owner: " + vehicle.getOwner());
                writer.newLine();
                writer.write("CPF: " + vehicle.getCpf());
                writer.newLine();
                writer.write("---");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Placa: ")) {
                    String placa = line.substring(7);
                    String model = reader.readLine().substring(7);
                    String color = reader.readLine().substring(7);
                    String owner = reader.readLine().substring(7);
                    String cpf = reader.readLine().substring(5);
                    reader.readLine(); // Skip the separator line
                    Vehicle vehicle = new Vehicle(placa, model, color, owner, cpf);
                    vehicles.add(vehicle);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println("Error parsing file: " + e.getMessage());
        }
    }
}