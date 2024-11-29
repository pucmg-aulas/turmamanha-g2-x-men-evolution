package view;

import controller.VehicleClientController;
import DAO.VehicleClientDAO.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class VehicleClientView {
    private VehicleClientController controller;

    public VehicleClientView(VehicleClientController controller) {
        this.controller = controller;
    }

    public void show() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        TableView<Vehicle> tableView = new TableView<>();

        TableColumn<Vehicle, String> placaColumn = new TableColumn<>("Placa");
        placaColumn.setCellValueFactory(new PropertyValueFactory<>("placa"));

        TableColumn<Vehicle, String> modelColumn = new TableColumn<>("Modelo");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Vehicle, String> colorColumn = new TableColumn<>("Cor");
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

        tableView.getColumns().addAll(placaColumn, modelColumn, colorColumn);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Consultar Veículos");
        dialog.setHeaderText(null);
        dialog.setContentText("Digite o CPF:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(cpf -> {
            List<Vehicle> vehicles = controller.getVehiclesByCpf(cpf);
            ObservableList<Vehicle> data = FXCollections.observableArrayList(vehicles);
            tableView.setItems(data);
        });

        vbox.getChildren().add(tableView);
        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Veículos do Cliente");
        stage.show();
    }
}