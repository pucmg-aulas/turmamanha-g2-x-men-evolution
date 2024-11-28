package view;

import controller.AdminController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TotalAmountView {
    private AdminController adminController;

    public TotalAmountView(AdminController adminController) {
        this.adminController = adminController;
    }

    public void show() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Button totalAmountButton = new Button("Consultar arrecadação total");
        Label totalAmountLabel = new Label();
        totalAmountButton.setOnAction(e -> {
            double totalAmount = adminController.getTotalAmountRaised();
            totalAmountLabel.setText("Total Amount Raised: " + totalAmount);
        });

        vbox.getChildren().addAll(totalAmountButton, totalAmountLabel);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Total Amount");
        stage.show();
    }
}