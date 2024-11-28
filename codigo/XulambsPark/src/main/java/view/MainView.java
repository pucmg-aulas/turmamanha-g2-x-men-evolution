package view;

import controller.AdminController;
import controller.ParkingLotController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.SpotType;
import util.DatabaseUtil;

import java.util.*;

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

        Button newParkingLotButton = new Button("Create New Parking Lot");
        newParkingLotButton.setOnAction(e -> {
            TextInputDialog nameDialog = new TextInputDialog();
            nameDialog.setTitle("New Parking Lot");
            nameDialog.setHeaderText(null);
            nameDialog.setContentText("Enter parking lot name:");

            Optional<String> nameResult = nameDialog.showAndWait();
            nameResult.ifPresent(name -> {
                Map<String, SpotType> spots = new LinkedHashMap<>();
                boolean addMoreSpots = true;
                final int[] spotCounter = {0};

                while (addMoreSpots) {
                    TextInputDialog spotsDialog = new TextInputDialog();
                    spotsDialog.setTitle("Number of Spots");
                    spotsDialog.setHeaderText(null);
                    spotsDialog.setContentText("Enter number of spots:");

                    Optional<String> spotsResult = spotsDialog.showAndWait();
                    spotsResult.ifPresent(spotsStr -> {
                        int numberOfSpots = Integer.parseInt(spotsStr);

                        List<SpotType> spotTypes = Arrays.asList(SpotType.values());
                        ChoiceDialog<SpotType> typeDialog = new ChoiceDialog<>(SpotType.REGULAR, spotTypes);
                        typeDialog.setTitle("Spot Type");
                        typeDialog.setHeaderText(null);
                        typeDialog.setContentText("Choose spot type:");

                        Optional<SpotType> typeResult = typeDialog.showAndWait();
                        typeResult.ifPresent(type -> {
                            for (int i = 0; i < numberOfSpots; i++) {
                                String spotId = generateSpotId(spotCounter[0]++);
                                spots.put(spotId, type);
                            }
                        });
                    });

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Add More Spots");
                    alert.setHeaderText(null);
                    alert.setContentText("Do you want to add more spots?");
                    Optional<ButtonType> result = alert.showAndWait();
                    addMoreSpots = result.isPresent() && result.get() == ButtonType.OK;
                }

                controller.registerParkingLot(name, spots);
                vbox.getChildren().add(new Button(name));
            });
        });

        vbox.getChildren().add(newParkingLotButton);

        // Adicionar botão "Acessar como administrador"
        Button adminButton = new Button("Acessar como administrador");
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

    private String generateSpotId(int counter) {
        char letter = (char) ('A' + counter / 6);
        int number = counter % 6;
        return String.format("%c%d", letter, number);
    }

    public static void main(String[] args) {
        DatabaseUtil.testConnection();
        launch(args);
    }
}