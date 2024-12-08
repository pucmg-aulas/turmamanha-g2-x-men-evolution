package view;

import controller.AdminController;
import controller.ClientHistoricalController;
import controller.ParkingLotController;
import controller.VehicleClientController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.DatabaseUtil;

public class MainView extends Application {
    private ParkingLotController controller;

    public MainView() {
        this.controller = new ParkingLotController();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu Xulambs Park");
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        for (String parkingLotName : controller.getParkingLots().keySet()) {
            Button button = new Button(parkingLotName);
            button.setOnAction(e -> new ParkingLotView(controller, parkingLotName).start(new Stage()));
            vbox.getChildren().add(button);
        }

        Button clientHistoricalButton = new Button("Consult historical of client");
        clientHistoricalButton.setOnAction(e -> new ClientHistoricalView(new ClientHistoricalController()).show());
        vbox.getChildren().add(clientHistoricalButton);

        Button vehicleClientButton = new Button("Consult client vehicles");
        vehicleClientButton.setOnAction(e -> new VehicleClientView(new VehicleClientController()).show());
        vbox.getChildren().add(vehicleClientButton);

        Button adminButton = new Button("Access as administrator");
        adminButton.setOnAction(e -> {
            AdminController adminController = new AdminController();
            AdminView adminView = new AdminView(adminController);
            adminView.showAdminLogin();
        });

        vbox.getChildren().add(adminButton);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        DatabaseUtil.testConnection();
        launch(args);
    }
}