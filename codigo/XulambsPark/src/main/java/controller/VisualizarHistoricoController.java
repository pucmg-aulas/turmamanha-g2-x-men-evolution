package controller;

import model.SistemaEstacionamento;
import view.VisualizarHistoricoView;

public class VisualizarHistoricoController {
    private SistemaEstacionamento sistemaEstacionamento;

    public VisualizarHistoricoController(SistemaEstacionamento sistemaEstacionamento) {
        this.sistemaEstacionamento = sistemaEstacionamento;
    }

    public void visualizarHistorico() {
        String cpfCliente = VisualizarHistoricoView.solicitarCpfCliente();
        if (cpfCliente != null && !cpfCliente.isEmpty()) {
            sistemaEstacionamento.visualizarHistorico(cpfCliente);
        }
    }
}