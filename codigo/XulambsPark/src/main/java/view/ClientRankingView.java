package view;

import DAO.ClientRankingDAO;
import controller.AdminController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ClientRankingView {
    private AdminController adminController;

    public ClientRankingView(AdminController adminController) {
        this.adminController = adminController;
    }

    public void show() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField rankingMonthField = new TextField();
        rankingMonthField.setPromptText("Enter month (1-12)");
        TextField rankingYearField = new TextField();
        rankingYearField.setPromptText("Enter year");
        Button clientRankingButton = new Button("Consultar ranking de clientes");
        Label clientRankingLabel = new Label();
        clientRankingButton.setOnAction(e -> {
            int month = Integer.parseInt(rankingMonthField.getText());
            int year = Integer.parseInt(rankingYearField.getText());
            List<ClientRankingDAO.ClientRanking> ranking = adminController.getClientRanking(month, year);
            StringBuilder rankingMessage = new StringBuilder("Client Ranking:\n");
            for (ClientRankingDAO.ClientRanking client : ranking) {
                rankingMessage.append("Name: ").append(client.getClientName())
                        .append(", CPF: ").append(client.getClientCpf())
                        .append(", Total: ").append(client.getTotalArrecadado()).append("\n");
            }
            clientRankingLabel.setText(rankingMessage.toString());
        });

        vbox.getChildren().addAll(rankingMonthField, rankingYearField, clientRankingButton, clientRankingLabel);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Client Ranking");
        stage.show();
    }
}