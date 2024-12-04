package view;

import controller.ParkingLotController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        gridPane.setHgap(10); // Espaçamento horizontal entre as vagas
        gridPane.setVgap(10); // Espaçamento vertical entre as vagas

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
            button.setPrefWidth(100); // Define a largura preferida
            button.setPrefHeight(100); // Define a altura preferida
            gridPane.add(button, col, row);
            col++;
            if (col == 10) {
                col = 0;
                row++;
            }
        }

        Label legendLabel = new Label("Legenda:");
        legendLabel.setStyle("-fx-padding: 5px;");

        Label occupiedLabel = new Label("Ocupado");
        occupiedLabel.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-padding: 5px;");

        Label regularLabel = new Label("Regular");
        regularLabel.setStyle("-fx-background-color: " + toHexString(Color.WHITE) + "; -fx-text-fill: black; -fx-padding: 5px;");

        Label idosoLabel = new Label("Idoso");
        idosoLabel.setStyle("-fx-background-color: " + toHexString(Color.YELLOW) + "; -fx-text-fill: black; -fx-padding: 5px;");

        Label pcdLabel = new Label("PCD");
        pcdLabel.setStyle("-fx-background-color: " + toHexString(Color.LIGHTGREEN) + "; -fx-text-fill: black; -fx-padding: 5px;");

        Label vipLabel = new Label("VIP");
        vipLabel.setStyle("-fx-background-color: " + toHexString(Color.MAGENTA) + "; -fx-text-fill: white; -fx-padding: 5px;");

        HBox legendBox = new HBox();
        legendBox.setSpacing(10);
        legendBox.getChildren().addAll(legendLabel, occupiedLabel, regularLabel, idosoLabel, pcdLabel, vipLabel);

        VBox mainLayout = new VBox();
        mainLayout.setSpacing(20);
        mainLayout.getChildren().addAll(gridPane, legendBox);

        Scene scene = new Scene(mainLayout, 800, 600);
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