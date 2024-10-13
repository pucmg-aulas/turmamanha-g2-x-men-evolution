package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Vaga {
    private boolean ocupada;
    private Veiculo veiculo;
    private LocalDateTime dataHoraEntrada;

    public Vaga() {
        this.ocupada = false;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public LocalDateTime getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public void estacionarVeiculo(Veiculo veiculo, LocalDateTime dataHoraEntrada) {
        this.ocupada = true;
        this.veiculo = veiculo;
        this.dataHoraEntrada = dataHoraEntrada;
    }

    public void liberarVaga() {
        this.ocupada = false;
        this.veiculo = null;
        this.dataHoraEntrada = null;
    }

    // Método para calcular o valor final da vaga (a ser sobrescrito nas subclasses)
    public double calcularDescontoOuAcrescimo(double valorBase) {
        return valorBase; // Sem desconto/acréscimo por padrão
    }

    // Método para obter o tipo de vaga (a ser sobrescrito nas subclasses)
    public String getTipo() {
        return "Normal";
    }

    // Método para inicializar as vagas
    public static List<Vaga> inicializarVagas() {
        List<Vaga> vagas = new ArrayList<>(); // Criamos a lista dentro do método

        // Adiciona as vagas "Normal", "Idoso", "PCD", e "VIP"
        for (int i = 0; i < 5; i++) {
            vagas.add(new Vaga()); // Normal
        }
        for (int i = 0; i < 3; i++) {
            vagas.add(new VagaIdoso()); // Idoso
        }
        for (int i = 0; i < 2; i++) {
            vagas.add(new VagaPCD()); // PCD
        }
        for (int i = 0; i < 1; i++) {
            vagas.add(new VagaVIP()); // VIP
        }

        return vagas; // Retorna a lista de vagas inicializada
    }


    // Método para salvar vagas em arquivo
    public static void salvarVagas(List<Vaga> vagas, String arquivo) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo));
        for (Vaga vaga : vagas) {
            writer.write(vaga.getTipo() + ";" + vaga.isOcupada());
            if (vaga.isOcupada() && vaga.getVeiculo() != null) {
                writer.write(";" + vaga.getVeiculo().getPlaca() + ";" + vaga.getDataHoraEntrada());
            }
            writer.newLine();
        }
        writer.close();
    }

    // Método para carregar vagas de um arquivo
    public static void carregarVagas(List<Vaga> vagas, String arquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;
        int index = 0;

        while ((linha = reader.readLine()) != null && index < vagas.size()) {
            String[] dados = linha.split(";");
            Vaga vaga = vagas.get(index);

            vaga.liberarVaga(); 

            if (dados[1].equals("true")) {
                vaga.ocupada = true;
                if (dados.length > 2) {
                    
                    String placa = dados[2];
                    LocalDateTime dataHoraEntrada = LocalDateTime.parse(dados[3]);
                    
                    
                    vaga.estacionarVeiculo(new Veiculo(placa, "", "", "", ""), dataHoraEntrada);
                }
            }

            index++;
        }
        reader.close();
    }
}
