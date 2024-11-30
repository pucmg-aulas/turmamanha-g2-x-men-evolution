package view;

import controller.AdminController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        vbox.getChildren().addAll(totalAmountButton, monthlyAmountButton, averageAmountButton, clientRankingButton, mostUsedSpotsButton, rushHourButton);

        Scene scene = new Scene(vbox, 300, 200);
        adminStage.setScene(scene);
        adminStage.setTitle("Menu Admin Xulambs Park");
        adminStage.show();
    }
}