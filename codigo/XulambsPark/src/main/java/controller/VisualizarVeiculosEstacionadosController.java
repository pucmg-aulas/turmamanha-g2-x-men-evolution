package controller;

import model.SistemaEstacionamento;

public class VisualizarVeiculosEstacionadosController {
    private SistemaEstacionamento sistemaEstacionamento;

    public VisualizarVeiculosEstacionadosController(SistemaEstacionamento sistemaEstacionamento) {
        this.sistemaEstacionamento = sistemaEstacionamento;
    }

    public void visualizarVeiculosEstacionados() {
        sistemaEstacionamento.visualizarVeiculosEstacionados();
    }
}
