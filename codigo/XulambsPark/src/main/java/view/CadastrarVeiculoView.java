package view;

import controller.CadastrarVeiculoController;

import javax.swing.*;
import java.awt.*;

public class CadastrarVeiculoView extends JPanel {
    private JTextField placaField;
    private JTextField cpfClienteField;
    private JTextField modeloField;
    private JTextField corField;
    private JTextField tipoField;
    private JButton cadastrarButton;

    public CadastrarVeiculoView(CadastrarVeiculoController controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(new JLabel("Placa:"), gbc);

        gbc.gridx = 1;
        placaField = new JTextField(15);
        add(placaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("CPF do Cliente:"), gbc);

        gbc.gridx = 1;
        cpfClienteField = new JTextField(15);
        add(cpfClienteField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Modelo:"), gbc);

        gbc.gridx = 1;
        modeloField = new JTextField(15);
        add(modeloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Cor:"), gbc);

        gbc.gridx = 1;
        corField = new JTextField(15);
        add(corField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Tipo:"), gbc);

        gbc.gridx = 1;
        tipoField = new JTextField(15);
        add(tipoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(e -> {
            String placa = placaField.getText();
            String cpfCliente = cpfClienteField.getText();
            String modelo = modeloField.getText();
            String cor = corField.getText();
            String tipo = tipoField.getText();
            if (placa.isEmpty() || cpfCliente.isEmpty() || modelo.isEmpty() || cor.isEmpty() || tipo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                controller.cadastrarVeiculo(placa, cpfCliente, modelo, cor, tipo);
                JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        add(cadastrarButton, gbc);
    }
}