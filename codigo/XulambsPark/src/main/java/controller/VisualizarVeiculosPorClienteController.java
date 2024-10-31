package controller;

import model.SistemaEstacionamento;
import model.Veiculo;
import view.VisualizarVeiculosPorClienteView;

import java.util.List;

public class VisualizarVeiculosPorClienteController {
    private SistemaEstacionamento sistemaEstacionamento;

    public VisualizarVeiculosPorClienteController(SistemaEstacionamento sistemaEstacionamento) {
        this.sistemaEstacionamento = sistemaEstacionamento;
    }

    public void visualizarVeiculosPorCliente(String cpfCliente) {
        List<Veiculo> veiculos = sistemaEstacionamento.visualizarVeiculosPorCliente(cpfCliente);
        new VisualizarVeiculosPorClienteView().mostrarVeiculos(veiculos, cpfCliente);
    }
}