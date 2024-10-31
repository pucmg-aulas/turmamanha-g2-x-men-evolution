package app;

import controller.*;
import model.Cliente;
import model.SistemaEstacionamento;
import model.Veiculo;
import view.CadastrarClienteView;
import view.CadastrarVeiculoView;
import view.VisualizarHistoricoView;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XulambsPark {
    private static Map<String, Veiculo> veiculos = new HashMap<>();
    private static Map<String, Cliente> clientes = new HashMap<>();
    private static SistemaEstacionamento sistemaEstacionamento = new SistemaEstacionamento(veiculos);


    public static void main(String[] args) {
        // Carregar dados
        try {
            sistemaEstacionamento.carregarClientes(clientes, "data/clientes.txt");
            sistemaEstacionamento.carregarVeiculos(veiculos, "data/veiculos.txt");
            sistemaEstacionamento.carregarVagas(sistemaEstacionamento.getParque("Parque A"), veiculos, "data/vagas_parque_a.txt");
            sistemaEstacionamento.carregarVagas(sistemaEstacionamento.getParque("Parque B"), veiculos, "data/vagas_parque_b.txt");
            sistemaEstacionamento.carregarVagas(sistemaEstacionamento.getParque("Parque C"), veiculos, "data/vagas_parque_c.txt");
            sistemaEstacionamento.carregarHistorico("data/historico.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Frame principal da aplicacao
        JFrame frame = new JFrame("XulambsPark - Sistema de Estacionamento");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 800);
        frame.setLayout(new GridLayout(8, 1));

        ImageIcon logoIcon = new ImageIcon(XulambsPark.class.getClassLoader().getResource("logo.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        frame.add(logoLabel);

        // Botoes
        JButton btnCadastrarCliente = new JButton("Cadastrar Cliente");
        JButton btnCadastrarVeiculo = new JButton("Cadastrar Veículo");
        JButton btnEstacionarVeiculo = new JButton("Estacionar Veículo");
        JButton btnLiberarVaga = new JButton("Liberar Vaga");
        JButton btnVisualizarVeiculosEstacionados = new JButton("Visualizar Veículos Estacionados");

        JButton btnAcessarComoAdmin = new JButton("Acessar como Administrador");
        JButton btnVisualizarHistoricoPorCliente = new JButton("Visualizar Histórico");

        // Instanciar Controladores
        CadastrarClienteController cadastrarClienteController = new CadastrarClienteController(clientes);
        CadastrarVeiculoController cadastrarVeiculoController = new CadastrarVeiculoController(clientes, veiculos);
        EstacionarVeiculoController estacionarVeiculoController = new EstacionarVeiculoController(clientes, veiculos, sistemaEstacionamento);
        LiberarVagaController liberarVagaController = new LiberarVagaController(sistemaEstacionamento);
        VisualizarVeiculosEstacionadosController visualizarVeiculosEstacionadosController = new VisualizarVeiculosEstacionadosController(sistemaEstacionamento);
        VisualizarHistoricoController visualizarHistoricoController = new VisualizarHistoricoController(sistemaEstacionamento);
        AcessarComoAdminController acessarComoAdminController = new AcessarComoAdminController(sistemaEstacionamento);

        // Adicionar ActionListeners aos botoes
        btnCadastrarCliente.addActionListener(e -> {
            JFrame cadastroFrame = new JFrame("Cadastrar Cliente");
            cadastroFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            cadastroFrame.setSize(300, 200);
            cadastroFrame.add(new CadastrarClienteView(cadastrarClienteController));
            cadastroFrame.setVisible(true);
        });

        btnCadastrarVeiculo.addActionListener(e -> {
            JFrame cadastroFrame = new JFrame("Cadastrar Veículo");
            cadastroFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            cadastroFrame.setSize(300, 300);
            cadastroFrame.add(new CadastrarVeiculoView(cadastrarVeiculoController));
            cadastroFrame.setVisible(true);
        });

        btnEstacionarVeiculo.addActionListener(e -> estacionarVeiculoController.estacionarVeiculo());
        btnLiberarVaga.addActionListener(e -> liberarVagaController.liberarVaga());
        btnVisualizarVeiculosEstacionados.addActionListener(e -> visualizarVeiculosEstacionadosController.visualizarVeiculosEstacionados());
        btnAcessarComoAdmin.addActionListener(e -> acessarComoAdminController.acessarComoAdministrador());
        btnVisualizarHistoricoPorCliente.addActionListener(e -> {
            String cpfCliente = JOptionPane.showInputDialog("Digite o CPF do cliente:");
            if (cpfCliente != null && !cpfCliente.trim().isEmpty()) {
                new VisualizarHistoricoView(sistemaEstacionamento).mostrarHistorico(cpfCliente);
            }
        });

        // Adicionar botoes ao frame
        frame.add(btnCadastrarCliente);
        frame.add(btnCadastrarVeiculo);
        frame.add(btnEstacionarVeiculo);
        frame.add(btnLiberarVaga);
        frame.add(btnVisualizarVeiculosEstacionados);

        frame.add(btnAcessarComoAdmin);
        frame.add(btnVisualizarHistoricoPorCliente);
        frame.setVisible(true);

        // Salvar dados ao encerrar
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                sistemaEstacionamento.salvarClientes(clientes, "data/clientes.txt");
                sistemaEstacionamento.salvarVeiculos(veiculos, "data/veiculos.txt");
                sistemaEstacionamento.salvarVagas(sistemaEstacionamento.getParque("Parque A"), "data/vagas_parque_a.txt");
                sistemaEstacionamento.salvarVagas(sistemaEstacionamento.getParque("Parque B"), "data/vagas_parque_b.txt");
                sistemaEstacionamento.salvarVagas(sistemaEstacionamento.getParque("Parque C"), "data/vagas_parque_c.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}