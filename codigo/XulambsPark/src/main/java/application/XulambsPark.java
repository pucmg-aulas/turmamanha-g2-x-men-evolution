// src/main/java/application/XulambsPark.java
package application;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XulambsPark {
    private static Map<String, Cliente> clientes = new HashMap<>();
    private static Map<String, Veiculo> veiculos = new HashMap<>();
    private static SistemaEstacionamento sistemaEstacionamento = new SistemaEstacionamento();

    public static void main(String[] args) {

        try {
            sistemaEstacionamento.carregarClientes(clientes, "clientes.txt");
            sistemaEstacionamento.carregarVeiculos(veiculos, "veiculos.txt");
            sistemaEstacionamento.carregarVagas(sistemaEstacionamento.getParque("Parque A"), veiculos, "vagas_parque_a.txt");
            sistemaEstacionamento.carregarVagas(sistemaEstacionamento.getParque("Parque B"), veiculos, "vagas_parque_b.txt");
            sistemaEstacionamento.carregarVagas(sistemaEstacionamento.getParque("Parque C"), veiculos, "vagas_parque_c.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("XulambsPark - Cadastro de Cliente e Veículo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);
        frame.setLayout(new GridLayout(8, 1));

        // Adicionando a imagem do logo
        ImageIcon logoIcon = new ImageIcon(XulambsPark.class.getClassLoader().getResource("logo.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH); // Adjust the dimensions as needed
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        frame.add(logoLabel);

        JButton btnCadastrarCliente = new JButton("Cadastrar Cliente");
        JButton btnCadastrarVeiculo = new JButton("Cadastrar Veículo");
        JButton btnEstacionarVeiculo = new JButton("Estacionar Veículo");
        JButton btnLiberarVaga = new JButton("Liberar Vaga");
        JButton btnVisualizarVeiculosEstacionados = new JButton("Visualizar Veículos Estacionados");
        JButton btnVisualizarVeiculosPorCliente = new JButton("Visualizar Veículos por Cliente");
        JButton btnVisualizarHistorico = new JButton("Visualizar Histórico");

        btnCadastrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente.cadastrarCliente(clientes);
            }
        });

        btnCadastrarVeiculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Veiculo.cadastrarVeiculo(clientes, veiculos);
            }
        });

        btnEstacionarVeiculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sistemaEstacionamento.estacionarVeiculo(clientes, veiculos);
            }
        });

        btnLiberarVaga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sistemaEstacionamento.liberarVaga();
            }
        });

        btnVisualizarVeiculosEstacionados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sistemaEstacionamento.visualizarVeiculosEstacionados();
            }
        });

        btnVisualizarVeiculosPorCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sistemaEstacionamento.visualizarVeiculosPorCliente(veiculos);
            }
        });

        btnVisualizarHistorico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpfCliente = JOptionPane.showInputDialog("Digite o CPF do cliente:");
                if (cpfCliente != null && !cpfCliente.isEmpty()) {
                    sistemaEstacionamento.visualizarHistorico(cpfCliente);
                }
            }
        });

        frame.add(btnCadastrarCliente);
        frame.add(btnCadastrarVeiculo);
        frame.add(btnEstacionarVeiculo);
        frame.add(btnLiberarVaga);
        frame.add(btnVisualizarVeiculosEstacionados);
        frame.add(btnVisualizarVeiculosPorCliente);
        frame.add(btnVisualizarHistorico);
        frame.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                sistemaEstacionamento.salvarClientes(clientes, "clientes.txt");
                sistemaEstacionamento.salvarVeiculos(veiculos, "veiculos.txt");
                sistemaEstacionamento.salvarVagas(sistemaEstacionamento.getParque("Parque A"), "vagas_parque_a.txt");
                sistemaEstacionamento.salvarVagas(sistemaEstacionamento.getParque("Parque B"), "vagas_parque_b.txt");
                sistemaEstacionamento.salvarVagas(sistemaEstacionamento.getParque("Parque C"), "vagas_parque_c.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}