package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ParqueEstacionamento implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private ArrayList<Vaga> vagas;

    public ParqueEstacionamento(String nome, int totalVagas) {
        this.nome = nome;
        this.vagas = new ArrayList<>();
        for (int i = 1; i <= totalVagas; i++) {
            this.vagas.add(new Vaga(String.valueOf(i)));
        }
    }

    public String getNome() {
        return nome;
    }
    
    public ArrayList<Vaga> getVagas() {
        return vagas;
    }


    public void associarVeiculoAVaga(Veiculo veiculo) {
        for (Vaga vaga : vagas) {
            if (!vaga.isOcupada()) {
                vaga.ocupar(veiculo);
                System.out.println("Veículo " + veiculo.getPlaca() + " estacionado na vaga " + vaga.getCodigo());
                return;
            }
        }
        System.out.println("Não há vagas disponíveis no parque " + nome);
    }

    public void liberarVagaPorPlaca(String placa) {
        for (Vaga vaga : vagas) {
            if (vaga.isOcupada() && vaga.getVeiculo().getPlaca().equals(placa)) {
                vaga.liberar();
                System.out.println("Vaga liberada para o veículo " + placa);
                return;
            }
        }
        System.out.println("Veículo não encontrado ou não está estacionado.");
    }

    public void visualizarVeiculosEstacionados() {
        System.out.println("Veículos estacionados no parque " + nome + ":");
        for (Vaga vaga : vagas) {
            if (vaga.isOcupada()) {
                System.out.println("Vaga " + vaga.getCodigo() + ": " + vaga.getVeiculo().toString());
            } else {
                System.out.println("Vaga " + vaga.getCodigo() + ": Disponível");
            }
        }
    }
}
