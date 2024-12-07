package view;

import controller.AdminController;
import controller.ParkingLotController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.SpotType;

import java.util.*;

public class AdminView {
    private AdminController adminController;

    public AdminView(AdminController adminController) {
        this.adminController = adminController;
    }

    public void showAdminLogin() {
        Stage adminLoginStage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label label = new Label("Enter Admin Password:");
        TextField passwordField = new TextField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String password = passwordField.getText();
            if (adminController.validateAdmin(password)) {
                showAdminInterface();
                adminLoginStage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid password!").showAndWait();
            }
        });

        vbox.getChildren().addAll(label, passwordField, loginButton);

        Scene scene = new Scene(vbox, 300, 200);
        adminLoginStage.setScene(scene);
        adminLoginStage.setTitle("Admin Login");
        adminLoginStage.show();
    }

    public void showAdminInterface() {
        Stage adminStage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Button totalAmountButton = new Button("Consult total collection");
        totalAmountButton.setOnAction(e -> new TotalAmountView(adminController).show());

        Button monthlyAmountButton = new Button("Consult collection in a months");
        monthlyAmountButton.setOnAction(e -> new MonthAmountView(adminController).show());

        Button averageAmountButton = new Button("Consult average value per collection");
        averageAmountButton.setOnAction(e -> new AverageAmountView(adminController).show());

        Button clientRankingButton = new Button("Consult client ranking");
        clientRankingButton.setOnAction(e -> new ClientRankingView(adminController).show());

        Button mostUsedSpotsButton = new Button("Consult the most used spots");
        mostUsedSpotsButton.setOnAction(e -> new MostUsedSpotsView(adminController).show());

        Button rushHourButton = new Button("Consult the most rush hour");
        rushHourButton.setOnAction(e -> new RushHourView(adminController).show());

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

                new ParkingLotController().registerParkingLot(name, spots);
            });
        });

        vbox.getChildren().addAll(totalAmountButton, monthlyAmountButton, averageAmountButton, clientRankingButton, mostUsedSpotsButton, rushHourButton, newParkingLotButton);

        Scene scene = new Scene(vbox, 300, 200);
        adminStage.setScene(scene);
        adminStage.setTitle("Menu Admin Xulambs Park");
        adminStage.show();
    }

    private String generateSpotId(int counter) {
        char letter = (char) ('A' + counter / 6);
        int number = counter % 6;
        return String.format("%c%d", letter, number);
    }
}