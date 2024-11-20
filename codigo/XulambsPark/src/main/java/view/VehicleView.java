package view;

import controller.VehicleController;
import model.Vehicle;

public class VehicleView {
    private VehicleController vehicleController;

    public VehicleView(VehicleController vehicleController) {
        this.vehicleController = vehicleController;
    }

    public String showPlateInputDialog() {
        return vehicleController.showPlateInputDialog();
    }

    public Vehicle showAdditionalInfo(String placa, String owner, String cpf) {
        return vehicleController.showAdditionalInfo(placa, owner, cpf);
    }
}