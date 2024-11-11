package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client {
    private String name;
    private String CPF;
    private UUID id;
    private List<Vehicle> vehicles;

    public Client(String name, UUID id, String CPF) {
        this.name = name;
        this.id = id;
        this.CPF = CPF;
        this.vehicles = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public String getCpf() {
        return CPF;
    }

    public void setCpf(String CPF) {
        this.CPF = CPF;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}