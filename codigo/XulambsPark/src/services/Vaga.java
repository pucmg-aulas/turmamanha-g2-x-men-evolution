package entities;

import java.io.Serializable;

public class Vaga implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codigo;
    private boolean ocupada;
    private Veiculo veiculo;

    public Vaga(String codigo) {
        this.codigo = codigo;
        this.ocupada = false;
        this.veiculo = null;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void ocupar(Veiculo veiculo) {
        this.ocupada = true;
        this.veiculo = veiculo;
    }

    public void liberar() {
        this.ocupada = false;
        this.veiculo = null;
    }
}
