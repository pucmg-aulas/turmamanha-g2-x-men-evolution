package view;

import controller.AdminController;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MostUsedSpotsView {
    private AdminController adminController;

    public MostUsedSpotsView(AdminController adminController) {
        this.adminController = adminController;
    }

    public void show() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        ListView<String> listView = new ListView<>();

        try {
            adminController.getMostUsedSpots().forEach(spot -> {
                listView.getItems().add(
                        "Vaga: " + spot.getSpotId() +
                                "  ---  Número de ocupações: " + spot.getOcupacoes()
                );
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        vbox.getChildren().add(listView);
        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Vagas Mais Utilizadas");
        stage.show();
    }
}