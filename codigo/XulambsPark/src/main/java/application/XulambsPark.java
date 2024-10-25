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
        frame.setSize(400, 800);
        frame.setLayout(new GridLayout(9, 1));

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
        JButton btnAcessarComoAdmin = new JButton("Acessar como Administrador");

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

        btnAcessarComoAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String senha = JOptionPane.showInputDialog("Digite a senha do administrador:");
                if ("admin123".equals(senha)) {
                    acessarComoAdministrador();
                } else {
                    JOptionPane.showMessageDialog(null, "Senha incorreta!");
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
        frame.add(btnAcessarComoAdmin);
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

    private static void acessarComoAdministrador() {
        String[] opcoes = {"Valor Total Arrecadado", "Valor Arrecadado em Determinado Mês", "Valor Médio por Utilização", "Ranking de Clientes por Arrecadação"};
        String escolha = (String) JOptionPane.showInputDialog(null, "Escolha uma opção:", "Administrador", JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

        if (escolha != null) {
            switch (escolha) {
                case "Valor Total Arrecadado":
                    double valorTotal = sistemaEstacionamento.calcularValorTotalArrecadado();
                    JOptionPane.showMessageDialog(null, "Valor total arrecadado: R$ " + String.format("%.2f", valorTotal));
                    break;
                case "Valor Arrecadado em Determinado Mês":
                    String mesAno = JOptionPane.showInputDialog("Digite o mês e o ano (MM/yyyy):");
                    double valorMes = sistemaEstacionamento.calcularValorArrecadadoMes(mesAno);
                    JOptionPane.showMessageDialog(null, "Valor arrecadado em " + mesAno + ": R$ " + String.format("%.2f", valorMes));
                    break;
                case "Valor Médio por Utilização":
                    double valorMedio = sistemaEstacionamento.calcularValorMedioUtilizacao();
                    JOptionPane.showMessageDialog(null, "Valor médio por utilização: R$ " + String.format("%.2f", valorMedio));
                    break;
                case "Ranking de Clientes por Arrecadação":
                    String mesAnoRanking = JOptionPane.showInputDialog("Digite o mês e o ano (MM/yyyy):");
                    String ranking = sistemaEstacionamento.gerarRankingClientesPorArrecadacao(mesAnoRanking);
                    JOptionPane.showMessageDialog(null, ranking);
                    break;
            }
        }
    }
}
