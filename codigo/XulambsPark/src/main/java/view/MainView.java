package view;

import controller.ParkingLotController;
import controller.VehicleController;
import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JComboBox<String> parkingLotComboBox;
    private JButton openParkingLotViewButton;
    private JButton openVehicleViewButton;
    private JButton openClientViewButton;
    private JButton registerVehicleButton;
    private JButton parkVehicleButton;
    private JButton vacateVehicleButton;
    private ParkingLotController parkingLotController;
    private VehicleController vehicleController;
    private ClientController clientController;

    public MainView(ParkingLotController parkingLotController, VehicleController vehicleController, ClientController clientController) {
        this.parkingLotController = parkingLotController;
        this.vehicleController = vehicleController;
        this.clientController = clientController;

        setTitle("Main Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel selectParkingLotLabel = new JLabel("Select Parking Lot:");
        selectParkingLotLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(selectParkingLotLabel, gbc);

        parkingLotComboBox = new JComboBox<>(parkingLotController.getParkingLots().toArray(new String[0]));
        parkingLotComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(parkingLotComboBox, gbc);

        openParkingLotViewButton = new JButton("View Parking Lot");
        openParkingLotViewButton.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(openParkingLotViewButton, gbc);

        openClientViewButton = new JButton("Register Client");
        openClientViewButton.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(openClientViewButton, gbc);

        registerVehicleButton = new JButton("Register Vehicle");
        registerVehicleButton.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(registerVehicleButton, gbc);

        parkVehicleButton = new JButton("Park Vehicle");
        parkVehicleButton.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(parkVehicleButton, gbc);

        vacateVehicleButton = new JButton("Vacate Vehicle");
        vacateVehicleButton.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(vacateVehicleButton, gbc);

        openParkingLotViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedParkingLot = (String) parkingLotComboBox.getSelectedItem();
                new ParkingLotView(parkingLotController.getParkingLot(selectedParkingLot));
            }
        });

        openClientViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientView(clientController);
            }
        });

        registerVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedParkingLot = (String) parkingLotComboBox.getSelectedItem();
                new VehicleRegistrationView(vehicleController, clientController, parkingLotController.getParkingLot(selectedParkingLot));
            }
        });

        parkVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedParkingLot = (String) parkingLotComboBox.getSelectedItem();
                new ParkVehicleView(parkingLotController, vehicleController, selectedParkingLot);
            }
        });

        vacateVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedParkingLot = (String) parkingLotComboBox.getSelectedItem();
                new VacateVehicleView(parkingLotController, selectedParkingLot);
            }
        });

        add(panel);
        setVisible(true);
    }
}