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

import java.time.LocalDate;

public class ClientHistoricalView {
    private ClientHistoricalController clientHistoricalController;

    public ClientHistoricalView(ClientHistoricalController clientHistoricalController) {
        this.clientHistoricalController = clientHistoricalController;
    }

    public void show() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);

        TextField cpfField = new TextField();
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> showHistorical(stage, cpfField.getText(), startDatePicker.getValue(), endDatePicker.getValue()));

        vbox.getChildren().addAll(new Label(" Client CPF:"), cpfField, new Label(" Start Date:"), startDatePicker, new Label(" End Date:"), endDatePicker, searchButton);
        Scene scene = new Scene(vbox, 300, 300);
        stage.setScene(scene);
        stage.setTitle("Search Client Historical");
        stage.show();
    }

    private void showHistorical(Stage stage, String clientCpf, LocalDate startDate, LocalDate endDate) {
        VBox vbox = new VBox(10);

        TableView<ClientHistoricalDAO.ClientHistorical> tableView = new TableView<>();
        ObservableList<ClientHistoricalDAO.ClientHistorical> data = FXCollections.observableArrayList();

        try {
            data.addAll(clientHistoricalController.getClientHistorical(clientCpf, startDate, endDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        TableColumn<ClientHistoricalDAO.ClientHistorical, String> vehiclePlateCol = new TableColumn<>("Plate");
        vehiclePlateCol.setCellValueFactory(new PropertyValueFactory<>("vehiclePlate"));

        TableColumn<ClientHistoricalDAO.ClientHistorical, String> spotIdCol = new TableColumn<>("Spot");
        spotIdCol.setCellValueFactory(new PropertyValueFactory<>("spotId"));

        TableColumn<ClientHistoricalDAO.ClientHistorical, String> parkingLotNameCol = new TableColumn<>("Parking Lot");
        parkingLotNameCol.setCellValueFactory(new PropertyValueFactory<>("parkingLotName"));

        TableColumn<ClientHistoricalDAO.ClientHistorical, java.sql.Timestamp> startTimeCol = new TableColumn<>("Start");
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        TableColumn<ClientHistoricalDAO.ClientHistorical, java.sql.Timestamp> endTimeCol = new TableColumn<>("End");
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        TableColumn<ClientHistoricalDAO.ClientHistorical, Double> amountPaidCol = new TableColumn<>("Amount Paid");
        amountPaidCol.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));

        tableView.setItems(data);
        tableView.getColumns().addAll(vehiclePlateCol, spotIdCol, parkingLotNameCol, startTimeCol, endTimeCol, amountPaidCol);

        vbox.getChildren().add(tableView);
        Scene scene = new Scene(vbox, 800, 400);
        stage.setScene(scene);
        stage.setTitle("Client Historical");
        stage.show();
    }
}