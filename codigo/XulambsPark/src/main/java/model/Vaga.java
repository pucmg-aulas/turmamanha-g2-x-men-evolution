package model;

import java.time.LocalDateTime;

public abstract class Vaga {
    private int numero;
    private boolean ocupada;
    private Veiculo veiculo;
    private LocalDateTime entrada;

    public Vaga(int numero) {
        this.numero = numero;
        this.ocupada = false;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void ocupar(Veiculo veiculo, LocalDateTime entrada) {
        this.veiculo = veiculo;
        this.entrada = entrada;
        this.ocupada = true;
    }

    public void liberar() {
        this.veiculo = null;
        this.entrada = null;
        this.ocupada = false;
    }
}