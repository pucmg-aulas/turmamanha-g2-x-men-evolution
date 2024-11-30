package view;

import DAO.ClientRankingDAO;
import controller.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
        Button clientRankingButton = new Button("Consult Client Ranking");

        clientRankingButton.setOnAction(e -> {
            int month = Integer.parseInt(rankingMonthField.getText());
            int year = Integer.parseInt(rankingYearField.getText());
            List<ClientRankingDAO.ClientRanking> ranking = adminController.getClientRanking(month, year);
            showClientRankingResults(ranking);
            stage.close();
        });

        vbox.getChildren().addAll(rankingMonthField, rankingYearField, clientRankingButton);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Client Ranking");
        stage.show();
    }

    private void showClientRankingResults(List<ClientRankingDAO.ClientRanking> ranking) {
        Stage resultStage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TableView<ClientRankingDAO.ClientRanking> tableView = new TableView<>();

        TableColumn<ClientRankingDAO.ClientRanking, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));

        TableColumn<ClientRankingDAO.ClientRanking, String> cpfColumn = new TableColumn<>("CPF");
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("clientCpf"));

        TableColumn<ClientRankingDAO.ClientRanking, Double> totalColumn = new TableColumn<>("Total Raised");
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalArrecadado"));

        tableView.getColumns().addAll(nameColumn, cpfColumn, totalColumn);

        ObservableList<ClientRankingDAO.ClientRanking> data = FXCollections.observableArrayList(ranking);
        tableView.setItems(data);

        vbox.getChildren().add(tableView);
        Scene scene = new Scene(vbox, 400, 300);
        resultStage.setScene(scene);
        resultStage.setTitle("Client Ranking Results");
        resultStage.show();
    }
}