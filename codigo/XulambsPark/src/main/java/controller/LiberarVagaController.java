package controller;

import model.SistemaEstacionamento;

public class LiberarVagaController {
    private SistemaEstacionamento sistemaEstacionamento;

    public LiberarVagaController(SistemaEstacionamento sistemaEstacionamento) {
        this.sistemaEstacionamento = sistemaEstacionamento;
    }

    public void liberarVaga() {
        sistemaEstacionamento.liberarVaga();
    }
}