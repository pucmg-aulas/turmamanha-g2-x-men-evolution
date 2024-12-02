package controller;

import DAO.VehicleDAO;
import exceptions.VehicleNotFoundException;
import exceptions.VehicleRegistrationException;
import exceptions.VehicleUpdateException;
import model.Vehicle;

import java.util.List;

public class VehicleController {
    private VehicleDAO vehicleDAO;

    public VehicleController() {
        this.vehicleDAO = new VehicleDAO();
    }

    public void registerVehicle(Vehicle vehicle) throws VehicleRegistrationException, VehicleUpdateException {
        Vehicle existingVehicle = vehicleDAO.findByPlaca(vehicle.getPlaca());
        if (existingVehicle != null) {
            try {
                vehicleDAO.update(vehicle);
                System.out.println("Vehicle updated: " + vehicle.getPlaca());
            } catch (Exception e) {
                throw new VehicleUpdateException("Error updating vehicle: " + vehicle.getPlaca(), e);
            }
        } else {
            try {
                vehicleDAO.save(vehicle);
                System.out.println("Vehicle registered: " + vehicle.getPlaca());
            } catch (Exception e) {
                throw new VehicleRegistrationException("Error registering vehicle: " + vehicle.getPlaca(), e);
            }
        }
    }

    public List<Vehicle> getVehicles() {
        return vehicleDAO.findAll();
    }

    public Vehicle getVehicleByPlaca(String placa) throws VehicleNotFoundException {
        Vehicle vehicle = vehicleDAO.findByPlaca(placa);
        if (vehicle == null) {
            throw new VehicleNotFoundException("Vehicle not found with plate: " + placa);
        }
        return vehicle;
    }
}