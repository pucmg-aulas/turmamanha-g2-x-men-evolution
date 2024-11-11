package view;

import controller.ParkingLotController;
import controller.VehicleController;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParkVehicleView extends JFrame {
    private JComboBox<String> vehicleComboBox;
    private JComboBox<String> spotComboBox;
    private JButton parkButton;
    private ParkingLotController parkingLotController;
    private VehicleController vehicleController;
    private String selectedParkingLot;

    public ParkVehicleView(ParkingLotController parkingLotController, VehicleController vehicleController, String selectedParkingLot) {
        this.parkingLotController = parkingLotController;
        this.vehicleController = vehicleController;
        this.selectedParkingLot = selectedParkingLot;

        setTitle("Park Vehicle");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel vehicleLabel = new JLabel("Vehicle:");
        vehicleComboBox = new JComboBox<>(vehicleController.getVehicles().stream().map(Vehicle::getPlaca).toArray(String[]::new));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(vehicleLabel, gbc);
        gbc.gridx = 1;
        panel.add(vehicleComboBox, gbc);

        JLabel spotLabel = new JLabel("Spot:");
        spotComboBox = new JComboBox<>(parkingLotController.getParkingLot(selectedParkingLot).getSpots().keySet().toArray(new String[0]));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(spotLabel, gbc);
        gbc.gridx = 1;
        panel.add(spotComboBox, gbc);

        parkButton = new JButton("Park Vehicle");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(parkButton, gbc);

        parkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedVehicle = (String) vehicleComboBox.getSelectedItem();
                String selectedSpot = (String) spotComboBox.getSelectedItem();
                if (selectedVehicle != null && selectedSpot != null) {
                    Vehicle vehicle = vehicleController.getVehicles().stream().filter(v -> v.getPlaca().equals(selectedVehicle)).findFirst().orElse(null);
                    if (vehicle != null && parkingLotController.parkVehicle(selectedParkingLot, selectedSpot, vehicle)) {
                        JOptionPane.showMessageDialog(null, "Vehicle parked successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to park vehicle. Spot might be occupied.");
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a vehicle and a spot.");
                }
            }
        });

        add(panel);
        setVisible(true);
    }
}