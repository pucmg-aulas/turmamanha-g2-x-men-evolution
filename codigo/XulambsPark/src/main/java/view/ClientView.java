// Java
package view;

import controller.ClientController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Client;

import java.util.Optional;

public class ClientView {
    private ClientController clientController;

    public ClientView(ClientController clientController) {
        this.clientController = clientController;
    }

    public Client show() {
        return clientController.handleClientInteraction(this);
    }

    public boolean showConfirmDialog(String message, String positiveButtonText, String negativeButtonText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType positiveButton = new ButtonType(positiveButtonText);
        ButtonType negativeButton = new ButtonType(negativeButtonText);
        alert.getButtonTypes().setAll(positiveButton, negativeButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == positiveButton;
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public String showInputDialog(String message) {
        Stage inputStage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label label = new Label(message);
        TextField textField = new TextField();
        Button submitButton = new Button("Submit");

        final String[] input = new String[1];
        submitButton.setOnAction(e -> {
            input[0] = textField.getText();
            inputStage.close();
        });

        vbox.getChildren().addAll(label, textField, submitButton);

        Scene scene = new Scene(vbox, 300, 200);
        inputStage.setScene(scene);
        inputStage.setTitle("XulambsPark");
        inputStage.showAndWait();

        return input[0];
    }
}