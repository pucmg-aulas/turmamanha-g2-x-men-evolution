package view;

import controller.VehicleClientController;
import DAO.VehicleClientDAO.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class VehicleClientView {
    private VehicleClientController controller;

    public VehicleClientView(VehicleClientController controller) {
        this.controller = controller;
    }

    public void show() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        Label label = new Label("Digite o CPF:");
        TextField cpfField = new TextField();
        Button searchButton = new Button("Consultar");

        searchButton.setOnAction(e -> {
            String cpf = cpfField.getText();
            List<Vehicle> vehicles = controller.getVehiclesByCpf(cpf);
            showVehicleResults(vehicles, stage);
        });

        vbox.getChildren().addAll(label, cpfField, searchButton);
        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Consulta de Veículos");
        stage.show();
    }

    private void showVehicleResults(List<Vehicle> vehicles, Stage currentStage) {
        Stage resultStage = new Stage();
        VBox vbox = new VBox(10);
        TableView<Vehicle> tableView = new TableView<>();

        TableColumn<Vehicle, String> placaColumn = new TableColumn<>("Placa");
        placaColumn.setCellValueFactory(new PropertyValueFactory<>("placa"));

        TableColumn<Vehicle, String> modelColumn = new TableColumn<>("Modelo");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Vehicle, String> colorColumn = new TableColumn<>("Cor");
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

        tableView.getColumns().addAll(placaColumn, modelColumn, colorColumn);

        ObservableList<Vehicle> data = FXCollections.observableArrayList(vehicles);
        tableView.setItems(data);

        vbox.getChildren().add(tableView);
        Scene scene = new Scene(vbox, 400, 300);
        resultStage.setScene(scene);
        resultStage.setTitle("Resultado da Consulta de Veículos");
        resultStage.show();

        currentStage.close();
    }
}