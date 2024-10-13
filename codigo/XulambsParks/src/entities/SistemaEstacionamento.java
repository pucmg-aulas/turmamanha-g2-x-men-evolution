package entities;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class SistemaEstacionamento {
    private List<Vaga> vagas;
    private Estacionamento estacionamento;

    public SistemaEstacionamento(List<Vaga> vagas, Estacionamento estacionamento) {
        this.vagas = vagas;
        this.estacionamento = estacionamento;
    }

    
    public void estacionarVeiculo(Scanner scanner, List<Cliente> clientes, List<Veiculo> veiculos) {
        System.out.println("Estacionar Veículo:");
        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine();

        Veiculo veiculoEstacionar = null;
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                veiculoEstacionar = veiculo;
                break;
            }
        }

        if (veiculoEstacionar == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        
        System.out.println("Vagas disponíveis:");
        boolean vagasDisponiveis = false;
        for (int i = 0; i < vagas.size(); i++) {
            if (!vagas.get(i).isOcupada()) {
                System.out.println((i + 1) + ". Vaga " + vagas.get(i).getTipo());
                vagasDisponiveis = true;
            }
        }

        if (!vagasDisponiveis) {
            System.out.println("Não há vagas disponíveis.");
            return;
        }

        System.out.print("Escolha a vaga (digite o número): ");
        int escolhaVaga;
        try {
            escolhaVaga = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, insira um número válido.");
            return;
        }

        if (escolhaVaga < 1 || escolhaVaga > vagas.size() || vagas.get(escolhaVaga - 1).isOcupada()) {
            System.out.println("Escolha inválida! Vaga inexistente ou já ocupada.");
            return;
        }

        Vaga vagaEscolhida = vagas.get(escolhaVaga - 1);

        
        System.out.print("Digite a data e hora de entrada (dd/MM/yyyy HH:mm): ");
        String dataHoraEntradaStr = scanner.nextLine();

        try {
            LocalDateTime dataHoraEntrada = LocalDateTime.parse(dataHoraEntradaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            vagaEscolhida.estacionarVeiculo(veiculoEstacionar, dataHoraEntrada);
            System.out.println("Veículo estacionado com sucesso!");
        } catch (Exception e) {
            System.out.println("Formato de data e hora inválido.");
        }
    }

    
    public void liberarVaga(Scanner scanner) {
        System.out.println("Liberar Vaga:");

        
        System.out.println("Vagas ocupadas:");
        for (int i = 0; i < vagas.size(); i++) {
            if (vagas.get(i).isOcupada()) {
                System.out.println(i + 1 + ". Vaga " + vagas.get(i).getTipo() + " - Veículo: " + vagas.get(i).getVeiculo().getPlaca());
            }
        }

        System.out.print("Escolha a vaga (digite o número): ");
        int escolhaVaga = scanner.nextInt();
        scanner.nextLine();
        Vaga vagaLiberada = vagas.get(escolhaVaga - 1);

        if (!vagaLiberada.isOcupada()) {
            System.out.println("A vaga já está vazia!");
            return;
        }

        System.out.print("Digite a data e hora de saída (dd/MM/yyyy HH:mm): ");
        String dataHoraSaidaStr = scanner.nextLine();

        try {
            LocalDateTime dataHoraSaida = LocalDateTime.parse(dataHoraSaidaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            double valor = estacionamento.calcularValor(vagaLiberada.getDataHoraEntrada(), dataHoraSaida, vagaLiberada.getTipo());
            System.out.println("Valor a pagar: R$ " + valor);
            vagaLiberada.liberarVaga();
            System.out.println("Vaga liberada com sucesso!");
        } catch (Exception e) {
            System.out.println("Formato de data e hora inválido.");
        }
    }

    
    public void mostrarVeiculosEstacionados() {
        System.out.println("Veículos Estacionados:");
        for (int i = 0; i < vagas.size(); i++) {
            Vaga vaga = vagas.get(i);
            if (vaga.isOcupada() && vaga.getVeiculo() != null) {
                System.out.println("Vaga " + (i + 1) + " - " + vaga.getTipo() + " - Veículo: " +
                    vaga.getVeiculo().getPlaca() + " (" + vaga.getVeiculo().getModelo() + ") - Cor: " + vaga.getVeiculo().getCor());
            }
        }
    }

    
    public void visualizarVeiculosPorCliente(Scanner scanner, List<Veiculo> veiculos) {
        System.out.print("Digite o CPF do cliente: ");
        String cpfCliente = scanner.nextLine();
        System.out.println("Veículos do Cliente (" + cpfCliente + "):");

        boolean encontrado = false;
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getCpfCliente().equals(cpfCliente)) {
                System.out.println("Placa: " + veiculo.getPlaca() + ", Modelo: " + veiculo.getModelo() + ", Cor: " + veiculo.getCor());
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("Nenhum veículo encontrado para o CPF informado.");
        }
    }

    public void salvarDadosEmArquivo(List<Cliente> clientes, List<Veiculo> veiculos, String arquivoClientes, String arquivoVeiculos, String arquivoVagas) throws IOException {
        Cliente.salvarClientes(clientes, arquivoClientes);
        Veiculo.salvarVeiculos(veiculos, arquivoVeiculos);
        Vaga.salvarVagas(vagas, arquivoVagas);
    }

    public void carregarDadosDeArquivo(List<Cliente> clientes, List<Veiculo> veiculos, String arquivoClientes, String arquivoVeiculos, String arquivoVagas) throws IOException {
        Cliente.carregarClientes(clientes, arquivoClientes);
        Veiculo.carregarVeiculos(veiculos, arquivoVeiculos);
        Vaga.carregarVagas(vagas, arquivoVagas);
    }
}
