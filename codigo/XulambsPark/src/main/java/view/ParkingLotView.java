package view;

import controller.ParkingLotController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.ParkingLot;
import model.ParkingSpot;
import javafx.scene.paint.Color;

import java.util.Map;

public class ParkingLotView extends Application {
    private ParkingLotController controller;
    private String parkingLotName;

    public ParkingLotView(ParkingLotController controller, String parkingLotName) {
        this.controller = controller;
        this.parkingLotName = parkingLotName;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Parking Lot: " + parkingLotName);
        GridPane gridPane = new GridPane();

        ParkingLot parkingLot = controller.getParkingLot(parkingLotName);
        int row = 0;
        int col = 0;
        for (Map.Entry<String, ParkingSpot> entry : parkingLot.getSpots().entrySet()) {
            String spotId = entry.getKey();
            ParkingSpot spot = entry.getValue();
            Button button = new Button(spotId);
            button.setStyle("-fx-background-color: " + (spot.isOccupied() ? "red" : toHexString(spot.getType().getColor())));
            button.setOnAction(e -> controller.handleButtonAction(spot, button));
            gridPane.add(button, col, row);
            col++;
            if (col == 10) {
                col = 0;
                row++;
            }
        }

        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}