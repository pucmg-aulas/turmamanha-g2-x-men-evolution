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

        Button totalAmountButton = new Button("Consultar arrecadação total");
        totalAmountButton.setOnAction(e -> new TotalAmountView(adminController).show());

        Button monthlyAmountButton = new Button("Consultar arrecadação em um mês");
        monthlyAmountButton.setOnAction(e -> new MonthAmountView(adminController).show());

        Button averageAmountButton = new Button("Consultar valor médio por arrecadação");
        averageAmountButton.setOnAction(e -> new AverageAmountView(adminController).show());

        Button clientRankingButton = new Button("Consultar ranking de clientes");
        clientRankingButton.setOnAction(e -> new ClientRankingView(adminController).show());

        Button mostUsedSpotsButton = new Button("Consultar vagas mais utilizadas");
        mostUsedSpotsButton.setOnAction(e -> new MostUsedSpotsView(adminController).show());

        Button rushHourButton = new Button("Consultar horários mais movimentados");
        rushHourButton.setOnAction(e -> new RushHourView(adminController).show());

        vbox.getChildren().addAll(totalAmountButton, monthlyAmountButton, averageAmountButton, clientRankingButton, mostUsedSpotsButton, rushHourButton);

        Scene scene = new Scene(vbox, 300, 200);
        adminStage.setScene(scene);
        adminStage.setTitle("Admin Interface");
        adminStage.show();
    }
}