package view;

import controller.AdminController;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
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
        ListView<String> listView = new ListView<>();

        try {
            adminController.getRushHours().forEach(rushHour -> {
                listView.getItems().add(
                        "Parking Lot: " + rushHour.getParkingLotName() +
                                ", Hour: " + rushHour.getHour() +
                                ", Entries: " + rushHour.getEntryCount()
                );
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        vbox.getChildren().add(listView);
        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Horários Mais Movimentados por Parque");
        stage.show();
    }
}