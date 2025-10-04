package view;

import controller.AdminController;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MostUsedSpotsView {
    private AdminController adminController;
// Problemas de arquitetura 20 MostUsedSpotsView, RushHourView - Views com tratamento de exceções genérico apenas imprimindo stack trace, dificultando diagnóstico e experiência do usuário
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
                        "Spot: " + spot.getSpotId() +
                                "  ---  Number of occupations: " + spot.getOcupacoes()
                );
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        vbox.getChildren().add(listView);
        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Most Used Spots");
        stage.show();
    }
}