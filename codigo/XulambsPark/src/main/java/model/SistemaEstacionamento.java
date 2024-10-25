package model;

import javax.swing.*;
import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SistemaEstacionamento {
    private ParqueEstacionamento parqueA = new ParqueEstacionamento("Parque A");
    private ParqueEstacionamento parqueB = new ParqueEstacionamento("Parque B");
    private ParqueEstacionamento parqueC = new ParqueEstacionamento("Parque C");
    private Map<String, Double> historicoArrecadacao = new HashMap<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void estacionarVeiculo(Map<String, Cliente> clientes, Map<String, Veiculo> veiculos) {
        String placa = JOptionPane.showInputDialog("Digite a placa do veículo:");
        Veiculo veiculo = veiculos.get(placa);

        if (veiculo == null) {
            JOptionPane.showMessageDialog(null, "Veículo não encontrado.");
            return;
        }

        if (isVeiculoEstacionado(veiculo)) {
            JOptionPane.showMessageDialog(null, "O veículo já está estacionado.");
            return;
        }

        String[] parques = {"Parque A", "Parque B", "Parque C"};
        String nomeParque = (String) JOptionPane.showInputDialog(null, "Escolha um parque de estacionamento:", "Estacionar Veículo", JOptionPane.QUESTION_MESSAGE, null, parques, parques[0]);
        ParqueEstacionamento parque = getParque(nomeParque);

        if (parque == null) {
            JOptionPane.showMessageDialog(null, "Parque não encontrado.");
            return;
        }

        String vagasDisponiveis = "Vagas disponíveis no " + nomeParque + ":\n";
        for (Vaga vaga : parque.getVagas()) {
            if (!vaga.isOcupada()) {
                vagasDisponiveis += "Vaga " + vaga.getNumero() + " (Tipo: " + vaga.getClass().getSimpleName() + ")\n";
            }
        }

        String vagaEscolhidaStr = JOptionPane.showInputDialog(vagasDisponiveis + "\nDigite o número da vaga que deseja ocupar:");
        try {
            int vagaEscolhida = Integer.parseInt(vagaEscolhidaStr);
            for (Vaga vaga : parque.getVagas()) {
                if (vaga.getNumero() == vagaEscolhida && !vaga.isOcupada()) {
                    String entradaStr = JOptionPane.showInputDialog("Digite a data e hora de entrada (dd/MM/yyyy HH:mm):");
                    LocalDateTime entrada = LocalDateTime.parse(entradaStr, formatter);
                    vaga.ocupar(veiculo, entrada);
                    JOptionPane.showMessageDialog(null, "Veículo estacionado com sucesso.");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Vaga escolhida não está disponível.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, insira um número de vaga válido.");
        }
    }

    public void liberarVaga() {
        String[] parques = {"Parque A", "Parque B", "Parque C"};
        String parqueEscolhido = (String) JOptionPane.showInputDialog(null, "Escolha um parque de estacionamento:", "Liberar Vaga", JOptionPane.QUESTION_MESSAGE, null, parques, parques[0]);

        if (parqueEscolhido != null) {
            ParqueEstacionamento parqueSelecionado = getParque(parqueEscolhido);
            if (parqueSelecionado != null) {
                String vagasOcupadas = "Vagas ocupadas no " + parqueEscolhido + ":\n";
                for (Vaga vaga : parqueSelecionado.getVagas()) {
                    if (vaga.isOcupada()) {
                        vagasOcupadas += "Vaga " + vaga.getNumero() + " - Veículo: " + vaga.getVeiculo().getPlaca() + "\n";
                    }
                }

                String vagaEscolhidaStr = JOptionPane.showInputDialog(vagasOcupadas + "\nDigite o número da vaga que deseja liberar:");
                try {
                    int vagaEscolhida = Integer.parseInt(vagaEscolhidaStr);
                    for (Vaga vaga : parqueSelecionado.getVagas()) {
                        if (vaga.getNumero() == vagaEscolhida && vaga.isOcupada()) {
                            String saidaStr = JOptionPane.showInputDialog("Digite a data e hora de saída (dd/MM/yyyy HH:mm):");
                            LocalDateTime saida = LocalDateTime.parse(saidaStr, formatter);
                            if (saida.isBefore(vaga.getEntrada())) {
                                JOptionPane.showMessageDialog(null, "Erro: A data e hora de saída não podem ser anteriores à data e hora de entrada.");
                                return;
                            }
                            Duration duracao = Duration.between(vaga.getEntrada(), saida);
                            long minutos = duracao.toMinutes();
                            double valor = Math.min((minutos / 15) * 4.0, 50.0);

                            if (vaga instanceof VagaVIP) {
                                valor *= 1.2;
                            } else if (vaga instanceof VagaIdoso) {
                                valor *= 0.85;
                            } else if (vaga instanceof VagaPCD) {
                                valor *= 0.87;
                            }

                            String key = formatter.format(vaga.getEntrada()) + "_" + vaga.getVeiculo().getCpfCliente();
                            historicoArrecadacao.put(key, valor);

                            salvarHistorico(vaga.getVeiculo().getCpfCliente(), vaga.getVeiculo().getPlaca(), vaga.getEntrada(), saida, valor);
                            vaga.liberar();
                            JOptionPane.showMessageDialog(null, "Vaga " + vaga.getNumero() + " do " + parqueEscolhido + " foi liberada com sucesso. Valor a pagar: R$ " + String.format("%.2f", valor));
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Vaga escolhida não está ocupada ou não existe.");
                } catch (NumberFormatException | IOException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, insira um número de vaga válido.");
                }
            }
        }
    }

    public void visualizarVeiculosEstacionados() {
        String[] parques = {"Parque A", "Parque B", "Parque C"};
        String parqueEscolhido = (String) JOptionPane.showInputDialog(null, "Escolha um parque de estacionamento:", "Visualizar Veículos Estacionados", JOptionPane.QUESTION_MESSAGE, null, parques, parques[0]);

        if (parqueEscolhido != null) {
            ParqueEstacionamento parqueSelecionado = getParque(parqueEscolhido);
            if (parqueSelecionado != null) {
                String veiculosEstacionados = "Veículos estacionados no " + parqueEscolhido + ":\n";
                for (Vaga vaga : parqueSelecionado.getVagas()) {
                    if (vaga.isOcupada()) {
                        veiculosEstacionados += "Vaga " + vaga.getNumero() + " - Veículo: " + vaga.getVeiculo().getPlaca() + "\n";
                    }
                }
                JOptionPane.showMessageDialog(null, veiculosEstacionados);
            }
        }
    }

    public void visualizarHistorico(String cpfCliente) {
        try (BufferedReader reader = new BufferedReader(new FileReader("historico.txt"))) {
            String line;
            StringBuilder historico = new StringBuilder("Histórico de uso do cliente (CPF: " + cpfCliente + "):\n");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(cpfCliente)) {
                    historico.append("Placa: ").append(parts[1])
                            .append(", Entrada: ").append(parts[2])
                            .append(", Saída: ").append(parts[3])
                            .append(", Valor: R$ ").append(parts[4]).append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, historico.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar o histórico.");
        }
    }

    public void visualizarVeiculosPorCliente(Map<String, Veiculo> veiculos) {
        String cpfCliente = JOptionPane.showInputDialog("Digite o CPF do cliente:");
        if (cpfCliente != null && !cpfCliente.isEmpty()) {
            StringBuilder veiculosDoCliente = new StringBuilder("Veículos do cliente (CPF: " + cpfCliente + "):\n");
            boolean encontrouVeiculo = false;
            for (Veiculo veiculo : veiculos.values()) {
                if (veiculo.getCpfCliente().equals(cpfCliente)) {
                    veiculosDoCliente.append("Placa: ").append(veiculo.getPlaca())
                            .append(", Modelo: ").append(veiculo.getModelo())
                            .append(", Cor: ").append(veiculo.getCor())
                            .append(", Tipo: ").append(veiculo.getTipo()).append("\n");
                    encontrouVeiculo = true;
                }
            }
            if (!encontrouVeiculo) {
                veiculosDoCliente.append("Nenhum veículo encontrado para este cliente.");
            }
            JOptionPane.showMessageDialog(null, veiculosDoCliente.toString());
        }
    }

    public ParqueEstacionamento getParque(String nome) {
        switch (nome) {
            case "Parque A":
                return parqueA;
            case "Parque B":
                return parqueB;
            case "Parque C":
                return parqueC;
            default:
                return null;
        }
    }

    public void salvarClientes(Map<String, Cliente> clientes, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Cliente cliente : clientes.values()) {
                writer.write(cliente.getCpf() + "," + cliente.getNome() + "\n");
            }
        }
    }

    public void carregarClientes(Map<String, Cliente> clientes, String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                clientes.put(parts[0], new Cliente(parts[0], parts[1]));
            }
        }
    }

    public void salvarVeiculos(Map<String, Veiculo> veiculos, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Veiculo veiculo : veiculos.values()) {
                writer.write(veiculo.getPlaca() + "," + veiculo.getCpfCliente() + "," + veiculo.getModelo() + "," + veiculo.getCor() + "," + veiculo.getTipo() + "\n");
            }
        }
    }

    public void carregarVeiculos(Map<String, Veiculo> veiculos, String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    veiculos.put(parts[0], new Veiculo(parts[0], parts[1], parts[2], parts[3], parts[4]));
                } else {
                    System.err.println("Linha inválida: " + line);
                }
            }
        }
    }

    public void salvarVagas(ParqueEstacionamento parque, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Vaga vaga : parque.getVagas()) {
                if (vaga.isOcupada()) {
                    writer.write(vaga.getNumero() + "," + vaga.getVeiculo().getPlaca() + "," + vaga.getEntrada().format(formatter) + "\n");
                }
            }
        }
    }

    public void carregarVagas(ParqueEstacionamento parque, Map<String, Veiculo> veiculos, String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int numero = Integer.parseInt(parts[0]);
                Veiculo veiculo = veiculos.get(parts[1]);
                LocalDateTime entrada = LocalDateTime.parse(parts[2], formatter);
                for (Vaga vaga : parque.getVagas()) {
                    if (vaga.getNumero() == numero) {
                        vaga.ocupar(veiculo, entrada);
                        break;
                    }
                }
            }
        }
    }

    public double calcularValorTotalArrecadado() {
        return historicoArrecadacao.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public double calcularValorArrecadadoMes(String mesAno) {
        System.out.println("Calculando valor arrecadado para o mês: " + mesAno);
        return historicoArrecadacao.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(mesAno))
                .mapToDouble(Map.Entry::getValue).sum();
    }

    public double calcularValorMedioUtilizacao() {
        return historicoArrecadacao.isEmpty() ? 0 : historicoArrecadacao.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public String gerarRankingClientesPorArrecadacao(String mesAno) {
        System.out.println("Gerando ranking para o mês: " + mesAno);
        Map<String, Double> arrecadacaoPorCliente = new HashMap<>();

        historicoArrecadacao.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(mesAno))
                .forEach(entry -> {
                    String[] parts = entry.getKey().split("_");
                    String cpfCliente = parts[1];
                    arrecadacaoPorCliente.put(cpfCliente, arrecadacaoPorCliente.getOrDefault(cpfCliente, 0.0) + entry.getValue());
                });

        return arrecadacaoPorCliente.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> "CPF: " + entry.getKey() + " - Total: R$ " + String.format("%.2f", entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    private void salvarHistorico(String cpfCliente, String placa, LocalDateTime entrada, LocalDateTime saida, double valor) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historico.txt", true))) {
            writer.write(cpfCliente + "," + placa + "," + entrada.format(formatter) + "," + saida.format(formatter) + "," + String.format("%.2f", valor) + "\n");
        }
    }

    private boolean isVeiculoEstacionado(Veiculo veiculo) {
        return parqueA.isVeiculoEstacionado(veiculo) || parqueB.isVeiculoEstacionado(veiculo) || parqueC.isVeiculoEstacionado(veiculo);
    }
}
