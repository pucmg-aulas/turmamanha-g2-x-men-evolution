package app;

import controller.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XulambsPark {
    private static Map<String, Cliente> clientes = new HashMap<>();
    private static Map<String, Veiculo> veiculos = new HashMap<>();
    private static SistemaEstacionamento sistemaEstacionamento = new SistemaEstacionamento();

    public static void main(String[] args) {
        // Carregar dados
        try {
            sistemaEstacionamento.carregarClientes(clientes, "data/clientes.txt");
            sistemaEstacionamento.carregarVeiculos(veiculos, "data/veiculos.txt");
            sistemaEstacionamento.carregarVagas(sistemaEstacionamento.getParque("Parque A"), veiculos, "data/vagas_parque_a.txt");
            sistemaEstacionamento.carregarVagas(sistemaEstacionamento.getParque("Parque B"), veiculos, "data/vagas_parque_b.txt");
            sistemaEstacionamento.carregarVagas(sistemaEstacionamento.getParque("Parque C"), veiculos, "data/vagas_parque_c.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Frame principal da aplicacao
        JFrame frame = new JFrame("XulambsPark - Sistema de Estacionamento");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 800);
        frame.setLayout(new GridLayout(9, 1));

        ImageIcon logoIcon = new ImageIcon(XulambsPark.class.getClassLoader().getResource("logo.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH); // Adjust the dimensions as needed
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        frame.add(logoLabel);

        // Botoes
        JButton btnCadastrarCliente = new JButton("Cadastrar Cliente");
        JButton btnCadastrarVeiculo = new JButton("Cadastrar Veículo");
        JButton btnEstacionarVeiculo = new JButton("Estacionar Veículo");
        JButton btnLiberarVaga = new JButton("Liberar Vaga");
        JButton btnVisualizarVeiculosEstacionados = new JButton("Visualizar Veículos Estacionados");
        JButton btnVisualizarVeiculosPorCliente = new JButton("Visualizar Veículos por Cliente");
        JButton btnVisualizarHistorico = new JButton("Visualizar Histórico");
        JButton btnAcessarComoAdmin = new JButton("Acessar como Administrador");

        // Instanciar Controladores
        CadastrarClienteController cadastrarClienteController = new CadastrarClienteController(clientes);
        CadastrarVeiculoController cadastrarVeiculoController = new CadastrarVeiculoController(clientes, veiculos);
        EstacionarVeiculoController estacionarVeiculoController = new EstacionarVeiculoController(clientes, veiculos, sistemaEstacionamento);
        LiberarVagaController liberarVagaController = new LiberarVagaController(sistemaEstacionamento);
        VisualizarVeiculosEstacionadosController visualizarVeiculosEstacionadosController = new VisualizarVeiculosEstacionadosController(sistemaEstacionamento);
        VisualizarVeiculosPorClienteController visualizarVeiculosPorClienteController = new VisualizarVeiculosPorClienteController(veiculos);
        VisualizarHistoricoController visualizarHistoricoController = new VisualizarHistoricoController(sistemaEstacionamento);
        AcessarComoAdminController acessarComoAdminController = new AcessarComoAdminController(sistemaEstacionamento);

        // Adicionar ActionListeners aos botoes
        btnCadastrarCliente.addActionListener(e -> cadastrarClienteController.cadastrarCliente());
        btnCadastrarVeiculo.addActionListener(e -> cadastrarVeiculoController.cadastrarVeiculo());
        btnEstacionarVeiculo.addActionListener(e -> estacionarVeiculoController.estacionarVeiculo());
        btnLiberarVaga.addActionListener(e -> liberarVagaController.liberarVaga());
        btnVisualizarVeiculosEstacionados.addActionListener(e -> visualizarVeiculosEstacionadosController.visualizarVeiculosEstacionados());
        btnVisualizarVeiculosPorCliente.addActionListener(e -> visualizarVeiculosPorClienteController.visualizarVeiculosPorCliente());
        btnVisualizarHistorico.addActionListener(e -> visualizarHistoricoController.visualizarHistorico());
        btnAcessarComoAdmin.addActionListener(e -> acessarComoAdminController.acessarComoAdministrador());

        // Adicionar botoes ao frame
        frame.add(btnCadastrarCliente);
        frame.add(btnCadastrarVeiculo);
        frame.add(btnEstacionarVeiculo);
        frame.add(btnLiberarVaga);
        frame.add(btnVisualizarVeiculosEstacionados);
        frame.add(btnVisualizarVeiculosPorCliente);
        frame.add(btnVisualizarHistorico);
        frame.add(btnAcessarComoAdmin);
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