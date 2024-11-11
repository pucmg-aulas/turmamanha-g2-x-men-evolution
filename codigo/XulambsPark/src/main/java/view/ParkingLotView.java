package view;

import model.ParkingLot;
import model.ParkingSpot;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class ParkingLotView extends JFrame {
    private ParkingLot parkingLot;

    public ParkingLotView(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;

        setTitle("Vizualizar Estacionamento");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Estacionamento: " + parkingLot.getName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        int row = 1;
        Map<String, ParkingSpot> sortedSpots = new TreeMap<>(parkingLot.getSpots());
        for (Map.Entry<String, ParkingSpot> entry : sortedSpots.entrySet()) {
            String spotId = entry.getKey();
            ParkingSpot spot = entry.getValue();

            JLabel spotLabel = new JLabel("Vaga " + spotId + ": " + (spot.isOccupied() ? "Ocupada" : "Livre") + " (" + spot.getType().getTipo() + ")");
            spotLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            gbc.gridx = 0;
            gbc.gridy = row++;
            gbc.gridwidth = 2;
            panel.add(spotLabel, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);
        setVisible(true);
    }
}