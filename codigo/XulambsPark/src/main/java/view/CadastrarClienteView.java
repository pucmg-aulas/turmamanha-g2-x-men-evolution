package view;

import controller.CadastrarClienteController;

import javax.swing.*;
import java.awt.*;

public class CadastrarClienteView extends JPanel {
    private JTextField nomeField;
    private JTextField cpfField;
    private JButton cadastrarButton;

    public CadastrarClienteView(CadastrarClienteController controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        nomeField = new JTextField(15);
        add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("CPF:"), gbc);

        gbc.gridx = 1;
        cpfField = new JTextField(15);
        add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(e -> {
            String nome = nomeField.getText();
            String cpf = cpfField.getText();
            if (nome.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                controller.cadastrarCliente(cpf, nome);
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        add(cadastrarButton, gbc);
    }
}