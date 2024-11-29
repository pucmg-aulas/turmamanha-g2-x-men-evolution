package view;

import controller.ClientHistoricalController;
import DAO.ClientHistoricalDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientHistoricalView {
    private ClientHistoricalController clientHistoricalController;

    public ClientHistoricalView(ClientHistoricalController clientHistoricalController) {
        this.clientHistoricalController = clientHistoricalController;
    }

    public void show() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);

        TextField cpfField = new TextField();
        Button searchButton = new Button("Buscar");
        searchButton.setOnAction(event -> showHistorical(stage, cpfField.getText()));

        vbox.getChildren().addAll(new Label("  CPF do Cliente:"), cpfField, searchButton);
        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Buscar Histórico de Cliente");
        stage.show();
    }

    private void showHistorical(Stage stage, String clientCpf) {
        VBox vbox = new VBox(10);

        TableView<ClientHistoricalDAO.ClientHistorical> tableView = new TableView<>();
        ObservableList<ClientHistoricalDAO.ClientHistorical> data = FXCollections.observableArrayList();

        try {
            data.addAll(clientHistoricalController.getClientHistorical(clientCpf));
        } catch (Exception e) {
            e.printStackTrace();
        }

        TableColumn<ClientHistoricalDAO.ClientHistorical, String> vehiclePlateCol = new TableColumn<>("Placa");
        vehiclePlateCol.setCellValueFactory(new PropertyValueFactory<>("vehiclePlate"));

        TableColumn<ClientHistoricalDAO.ClientHistorical, String> spotIdCol = new TableColumn<>("Vaga");
        spotIdCol.setCellValueFactory(new PropertyValueFactory<>("spotId"));

        TableColumn<ClientHistoricalDAO.ClientHistorical, String> parkingLotNameCol = new TableColumn<>("Estacionamento");
        parkingLotNameCol.setCellValueFactory(new PropertyValueFactory<>("parkingLotName"));

        TableColumn<ClientHistoricalDAO.ClientHistorical, java.sql.Timestamp> startTimeCol = new TableColumn<>("Início");
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        TableColumn<ClientHistoricalDAO.ClientHistorical, java.sql.Timestamp> endTimeCol = new TableColumn<>("Fim");
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        TableColumn<ClientHistoricalDAO.ClientHistorical, Double> amountPaidCol = new TableColumn<>("Valor Pago");
        amountPaidCol.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));

        tableView.setItems(data);
        tableView.getColumns().addAll(vehiclePlateCol, spotIdCol, parkingLotNameCol, startTimeCol, endTimeCol, amountPaidCol);

        vbox.getChildren().add(tableView);
        Scene scene = new Scene(vbox, 800, 400);
        stage.setScene(scene);
        stage.setTitle("Histórico do Cliente");
        stage.show();
    }
}