package view;

import controller.AdminController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RushHourView {
    private AdminController adminController;

    public RushHourView(AdminController adminController) {
        this.adminController = adminController;
    }

    public void show() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        Label label = new Label("  Digite o nome do parque:");
        TextField parkingLotField = new TextField();
        Button button = new Button("Mostrar Horários");
        ListView<String> listView = new ListView<>();

        button.setOnAction(event -> {
            listView.getItems().clear();
            String parkingLotName = parkingLotField.getText();
            try {
                adminController.getRushHours(parkingLotName).forEach(rushHour -> {
                    listView.getItems().add(
                            "Hour: " + rushHour.getHour() + ":00" +
                                    ", Entries: " + rushHour.getEntryCount()
                    );
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        vbox.getChildren().addAll(label, parkingLotField, button, listView);
        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Horários Mais Movimentados por Parque");
        stage.show();
    }
}