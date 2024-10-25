package model;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Map;

public class Veiculo {
    private String placa;
    private String modelo;
    private String cor;
    private String tipo;
    private String cpfCliente;

    public Veiculo(String placa, String modelo, String cor, String tipo, String cpfCliente) {
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.tipo = tipo;
        this.cpfCliente = cpfCliente;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCor() {
        return cor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public static void cadastrarVeiculo(Map<String, Cliente> clientes, Map<String, Veiculo> veiculos) {
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

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Cadastro de Veículo", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String placa = placaField.getText();
            String modelo = modeloField.getText();
            String cor = corField.getText();
            String tipo = (String) tipoBox.getSelectedItem();
            String cpf = cpfField.getText();

            if (!placa.isEmpty() && !modelo.isEmpty() && !cor.isEmpty() && !cpf.isEmpty()) {
                if (clientes.containsKey(cpf)) {
                    veiculos.put(placa, new Veiculo(placa, modelo, cor, tipo, cpf));
                    JOptionPane.showMessageDialog(null, "Veículo cadastrado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente não encontrado. Cadastre o cliente primeiro.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
            }
        }
    }


}