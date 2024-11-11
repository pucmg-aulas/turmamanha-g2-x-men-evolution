// src/main/java/main/Main.java
package main;

import controller.ParkingLotController;
import controller.VehicleController;
import controller.ClientController;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        ParkingLotController parkingLotController = new ParkingLotController();
        VehicleController vehicleController = new VehicleController();
        ClientController clientController = new ClientController();

        new MainView(parkingLotController, vehicleController, clientController);
    }
}