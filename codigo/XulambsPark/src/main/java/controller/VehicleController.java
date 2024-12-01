package controller;

import DAO.VehicleDAO;
import model.Vehicle;

import java.util.List;

public class VehicleController {
    private VehicleDAO vehicleDAO;

    public VehicleController() {
        this.vehicleDAO = new VehicleDAO();
    }

    public void registerVehicle(Vehicle vehicle) {
        Vehicle existingVehicle = vehicleDAO.findByPlaca(vehicle.getPlaca());
        if (existingVehicle != null) {
            vehicleDAO.update(vehicle);
            System.out.println("Vehicle updated: " + vehicle.getPlaca());
        } else {
            vehicleDAO.save(vehicle);
            System.out.println("Vehicle registered: " + vehicle.getPlaca());
        }
    }

    public List<Vehicle> getVehicles() {
        return vehicleDAO.findAll();
    }

    public Vehicle getVehicleByPlaca(String placa) {
        return vehicleDAO.findByPlaca(placa);
    }
}