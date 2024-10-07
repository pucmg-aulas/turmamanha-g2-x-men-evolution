package entities;

import java.util.ArrayList;

public class ParqueEstacionamento {
    private String nome;
    private ArrayList<Vaga> vagas;

    // Construtor para inicializar o nome do parque e as vagas
    public ParqueEstacionamento(String nome, int totalVagas) {
        this.nome = nome;
        this.vagas = new ArrayList<>();
        
        // Criando diferentes tipos de vagas
        for (int i = 1; i <= totalVagas; i++) {
            if (i <= 2) {
                this.vagas.add(new VagaPCD(String.valueOf(i))); // Vagas PCD
            } else if (i <= 4) {
                this.vagas.add(new VagaIdoso(String.valueOf(i))); // Vagas Idoso
            } else if (i <= 6) {
                this.vagas.add(new VagaVIP(String.valueOf(i))); // Vagas VIP
            } else {
                this.vagas.add(new Vaga(String.valueOf(i))); // Vagas normais
            }
        }
    }

    // Método para obter o nome do parque
    public String getNome() {
        return nome;
    }

    // Método para obter as vagas do parque
    public ArrayList<Vaga> getVagas() {
        return vagas;
    }

    // Método para associar um veículo a uma vaga, baseado no tipo de vaga
    public void associarVeiculoAVaga(Veiculo veiculo, String tipoVaga) {
        for (Vaga vaga : vagas) {
            if (!vaga.isOcupada() && vaga.getTipo().equalsIgnoreCase(tipoVaga)) {
                vaga.ocupar(veiculo);
                return;
            }
        }
        System.out.println("Não há vagas disponíveis do tipo " + tipoVaga + " no parque " + nome);
    }

    // Método para liberar uma vaga e calcular o valor cobrado
    public void liberarVagaPorPlaca(String placa) {
        for (Vaga vaga : vagas) {
            if (vaga.isOcupada() && vaga.getVeiculo().getPlaca().equals(placa)) {
                double valorCobrado = vaga.liberar();  // Libera a vaga e calcula o valor
                System.out.println("Valor a ser pago pelo veículo com placa " + placa + ": R$ " + valorCobrado);
                return;
            }
        }
        System.out.println("Veículo não encontrado ou não está estacionado.");
    }

    // Método para visualizar os veículos estacionados
    public void visualizarVeiculosEstacionados() {
        System.out.println("Veículos estacionados no parque " + nome + ":");
        for (Vaga vaga : vagas) {
            if (vaga.isOcupada()) {
                System.out.println("Vaga " + vaga.getCodigo() + ": " + vaga.getVeiculo().toString() + " (Tipo: " + vaga.getTipo() + ")");
            } else {
                System.out.println("Vaga " + vaga.getCodigo() + ": Disponível (Tipo: " + vaga.getTipo() + ")");
            }
        }
    }
}
