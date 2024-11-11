package view;

import controller.ClientController;
import controller.VehicleController;
import model.Client;
import model.ParkingLot;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VehicleRegistrationView extends JFrame {
    private JTextField placaField;
    private JTextField modelField;
    private JTextField colorField;
    private JComboBox<String> clientComboBox;
    private JButton registerButton;
    private VehicleController vehicleController;
    private ClientController clientController;
    private ParkingLot selectedParkingLot;

    public VehicleRegistrationView(VehicleController vehicleController, ClientController clientController, ParkingLot selectedParkingLot) {
        this.vehicleController = vehicleController;
        this.clientController = clientController;
        this.selectedParkingLot = selectedParkingLot;

        setTitle("Vehicle Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel placaLabel = new JLabel("Placa:");
        placaField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(placaLabel, gbc);
        gbc.gridx = 1;
        panel.add(placaField, gbc);

        JLabel modelLabel = new JLabel("Model:");
        modelField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(modelLabel, gbc);
        gbc.gridx = 1;
        panel.add(modelField, gbc);

        JLabel colorLabel = new JLabel("Color:");
        colorField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(colorLabel, gbc);
        gbc.gridx = 1;
        panel.add(colorField, gbc);

        JLabel clientLabel = new JLabel("Client:");
        clientComboBox = new JComboBox<>(clientController.getClientNames().toArray(new String[0]));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(clientLabel, gbc);
        gbc.gridx = 1;
        panel.add(clientComboBox, gbc);

        registerButton = new JButton("Register Vehicle");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = placaField.getText();
                String model = modelField.getText();
                String color = colorField.getText();
                String clientName = (String) clientComboBox.getSelectedItem();
                Client client = clientController.getClientByName(clientName);
                Vehicle vehicle = new Vehicle(placa, model, color, client.getName(), client.getCpf());
                client.addVehicle(vehicle);
                vehicleController.registerVehicle(vehicle);
                clientController.updateClient(client);
                selectedParkingLot.addVehicle(vehicle); // Add vehicle to the selected parking lot
                JOptionPane.showMessageDialog(null, "Vehicle registered successfully!");
                dispose();
            }
        });

        add(panel);
        setVisible(true);
    }
}