package view;

import javax.swing.*;
import java.awt.*;

public class CadastrarVeiculoView {
    public static String[] mostrarFormulario() {
        JTextField placaField = new JTextField(10);
        JTextField modeloField = new JTextField(10);
        JTextField corField = new JTextField(10);
        String[] tipos = {"Carro", "Moto"};
        JComboBox<String> tipoBox = new JComboBox<>(tipos);
        JTextField cpfField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Placa: "));
        panel.add(placaField);
        panel.add(new JLabel("Modelo: "));
        panel.add(modeloField);
        panel.add(new JLabel("Cor: "));
        panel.add(corField);
        panel.add(new JLabel("Tipo: "));
        panel.add(tipoBox);
        panel.add(new JLabel("CPF do Cliente: "));
        panel.add(cpfField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Cadastro de Veículo", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return new String[]{placaField.getText(), modeloField.getText(), corField.getText(), (String) tipoBox.getSelectedItem(), cpfField.getText()};
        }
        return null;
    }
}