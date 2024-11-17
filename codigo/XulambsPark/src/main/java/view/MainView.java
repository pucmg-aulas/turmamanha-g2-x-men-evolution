package view;

import controller.ParkingLotController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends Application {
    private ParkingLotController controller;

    public MainView() {
        this.controller = new ParkingLotController();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Select Parking Lot");
        VBox vbox = new VBox();

        for (String parkingLotName : controller.getParkingLots()) {
            Button button = new Button(parkingLotName);
            button.setOnAction(e -> new ParkingLotView(controller, parkingLotName).start(new Stage()));
            vbox.getChildren().add(button);
        }

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}