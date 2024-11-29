package controller;

import DAO.VehicleClientDAO;
import DAO.VehicleClientDAO.Vehicle;

import java.util.List;

public class VehicleClientController {
    private VehicleClientDAO vehicleClientDAO;

    public VehicleClientController() {
        this.vehicleClientDAO = new VehicleClientDAO();
    }

    public List<Vehicle> getVehiclesByCpf(String cpf) {
        return vehicleClientDAO.getVehiclesByCpf(cpf);
    }
}