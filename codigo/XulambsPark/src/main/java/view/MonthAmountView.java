package view;

import controller.AdminController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MonthAmountView {
    private AdminController adminController;

    public MonthAmountView(AdminController adminController) {
        this.adminController = adminController;
    }

    public void show() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField monthField = new TextField();
        monthField.setPromptText("Enter month (1-12)");
        TextField yearField = new TextField();
        yearField.setPromptText("Enter year");
        Button monthAmountButton = new Button("Consult collection in a month");
        Label monthAmountLabel = new Label();
        monthAmountButton.setOnAction(e -> {
            int month = Integer.parseInt(monthField.getText());
            int year = Integer.parseInt(yearField.getText());
            double monthAmount = adminController.getAmountRaisedInMonth(month, year);
            monthAmountLabel.setText("Amount Raised in " + month + "/" + year + ": R$" + String.format("%.2f", monthAmount));
        });

        vbox.getChildren().addAll(monthField, yearField, monthAmountButton, monthAmountLabel);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Monthly Amount");
        stage.show();
    }
}