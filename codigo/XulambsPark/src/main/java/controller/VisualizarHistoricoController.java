package controller;

import model.SistemaEstacionamento;
import view.VisualizarHistoricoView;

public class VisualizarHistoricoController {
    private SistemaEstacionamento sistemaEstacionamento;

    public VisualizarHistoricoController(SistemaEstacionamento sistemaEstacionamento) {
        this.sistemaEstacionamento = sistemaEstacionamento;
    }

    public void visualizarHistoricoPorCliente(String cpfCliente) {
        new VisualizarHistoricoView(sistemaEstacionamento).mostrarHistorico(cpfCliente);
    }
}