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
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

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
        List<ParkingSpot> sortedSpots = parkingLot.getSpots().values().stream()
                .sorted((spot1, spot2) -> spot1.getPosition().compareTo(spot2.getPosition()))
                .collect(Collectors.toList());

        int row = 0;
        int col = 0;
        for (ParkingSpot spot : sortedSpots) {
            Button button = new Button(spot.getPosition());
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