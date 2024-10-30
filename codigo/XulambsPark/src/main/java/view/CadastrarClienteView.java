package view;

import javax.swing.*;
import java.awt.*;

public class CadastrarClienteView {
    public static String[] mostrarFormulario() {
        JTextField nomeField = new JTextField(10);
        JTextField cpfField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Nome: "));
        panel.add(nomeField);
        panel.add(new JLabel("CPF: "));
        panel.add(cpfField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Cadastro de Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return new String[]{nomeField.getText(), cpfField.getText()};
        }
        return null;
    }
}