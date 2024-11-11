package controller;

import DAO.VehicleDAO;
import model.Vehicle;

import java.util.List;

public class VehicleController {
    private VehicleDAO vehicleDAO;

    public VehicleController() {
        this.vehicleDAO = new VehicleDAO();
        this.vehicleDAO.loadFromFile();
    }

    public void registerVehicle(Vehicle vehicle) {
        vehicleDAO.save(vehicle);
        System.out.println("Vehicle registered: " + vehicle.getPlaca());
    }

    public List<Vehicle> getVehicles() {
        return vehicleDAO.findAll();
    }
}