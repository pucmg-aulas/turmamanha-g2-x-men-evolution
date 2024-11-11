package view;

import controller.ParkingLotController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VacateVehicleView extends JFrame {
    private JComboBox<String> spotComboBox;
    private JButton vacateButton;
    private ParkingLotController parkingLotController;
    private String selectedParkingLot;

    public VacateVehicleView(ParkingLotController parkingLotController, String selectedParkingLot) {
        this.parkingLotController = parkingLotController;
        this.selectedParkingLot = selectedParkingLot;

        setTitle("Vacate Vehicle");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel spotLabel = new JLabel("Spot:");
        spotComboBox = new JComboBox<>(parkingLotController.getParkingLot(selectedParkingLot).getSpots().keySet().toArray(new String[0]));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(spotLabel, gbc);
        gbc.gridx = 1;
        panel.add(spotComboBox, gbc);

        vacateButton = new JButton("Vacate Spot");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(vacateButton, gbc);

        vacateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSpot = (String) spotComboBox.getSelectedItem();
                if (selectedSpot != null) {
                    if (parkingLotController.vacateSpot(selectedParkingLot, selectedSpot)) {
                        JOptionPane.showMessageDialog(null, "Spot vacated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to vacate spot. It might already be empty.");
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a spot.");
                }
            }
        });

        add(panel);
        setVisible(true);
    }
}