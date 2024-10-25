package model;

import javax.swing.*;
import java.io.*;
import java.util.Map;

public class Cliente {
    private String nome;
    private String cpf;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public static void cadastrarCliente(Map<String, Cliente> clientes) {
        JTextField nomeField = new JTextField(10);
        JTextField cpfField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Nome: "));
        panel.add(nomeField);
        panel.add(Box.createHorizontalStrut(15));
        panel.add(new JLabel("CPF: "));
        panel.add(cpfField);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Cadastro de Cliente", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String cpf = cpfField.getText();
            if (!nome.isEmpty() && !cpf.isEmpty()) {
                clientes.put(cpf, new Cliente(nome, cpf));
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
            }
        }
    }

}
