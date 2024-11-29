package view;

import controller.AdminController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AverageAmountView {
    private AdminController adminController;

    public AverageAmountView(AdminController adminController) {
        this.adminController = adminController;
    }

    public void show() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Button averageAmountButton = new Button("Consultar valor médio por arrecadação");
        Label averageAmountLabel = new Label();
        averageAmountButton.setOnAction(e -> {
            double averageAmount = adminController.getAverageAmountRaised();
            averageAmountLabel.setText("Average Amount Raised: R$" + String.format("%.2f", averageAmount));
        });

        vbox.getChildren().addAll(averageAmountButton, averageAmountLabel);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Average Amount");
        stage.show();
    }
}