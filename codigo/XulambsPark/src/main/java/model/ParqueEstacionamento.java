package model;

import java.util.ArrayList;
import java.util.List;

public class ParqueEstacionamento {
    private String nome;
    private List<Vaga> vagas;

    public ParqueEstacionamento(String nome) {
        this.nome = nome;
        this.vagas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i < 2) {
                vagas.add(new VagaPCD(i + 1));
            } else if (i < 4) {
                vagas.add(new VagaIdoso(i + 1));
            } else if (i < 6) {
                vagas.add(new VagaVIP(i + 1));
            } else {
                vagas.add(new VagaNormal(i + 1));
            }
        }
    }


    public String getNome() {
        return nome;
    }

    public List<Vaga> getVagas() {
        return vagas;
    }

    public boolean isVeiculoEstacionado(Veiculo veiculo) {
        for (Vaga vaga : vagas) {
            if (vaga.isOcupada() && vaga.getVeiculo().equals(veiculo)) {
                return true;
            }
        }
        return false;
    }
}