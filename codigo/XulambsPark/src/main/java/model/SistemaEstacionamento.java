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
        private List<String> historico = new ArrayList<>();
        private Map<String, Veiculo> veiculos;
        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        public SistemaEstacionamento(Map<String, Veiculo> veiculos) {
            this.veiculos = veiculos;
        }
    
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
                        LocalDateTime entrada = LocalDateTime.now();
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
                                LocalDateTime saida = LocalDateTime.now();
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

                                try {
                                    salvarHistorico(vaga.getVeiculo().getCpfCliente(), vaga.getVeiculo().getPlaca(), vaga.getEntrada(), saida, valor);
                                    vaga.liberar();
                                    JOptionPane.showMessageDialog(null, "Vaga " + vaga.getNumero() + " do " + parqueEscolhido + " foi liberada com sucesso. Valor a pagar: R$ " + String.format("%.2f", valor));
                                } catch (IOException e) {
                                    JOptionPane.showMessageDialog(null, "Erro ao salvar histórico: " + e.getMessage());
                                }return;
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Vaga escolhida não está ocupada ou não existe.");
                    } catch (NumberFormatException e) {
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
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (Veiculo veiculo : veiculos.values()) {
                    bw.write(veiculo.getPlaca() + "," + veiculo.getCpfCliente() + "," + veiculo.getModelo() + "," + veiculo.getCor() + "," + veiculo.getTipo());
                    bw.newLine();
                }
            }
        }

        public void carregarVeiculos(Map<String, Veiculo> veiculos, String filePath) throws IOException {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    Veiculo veiculo = new Veiculo(data[0], data[1], data[2], data[3], data[4]);
                    veiculos.put(data[0], veiculo);
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
            return historico.stream()
                    .mapToDouble(registro -> Double.parseDouble(registro.split(",")[6]))
                    .sum();
        }

        public double calcularValorArrecadadoMes(String mesAno) {
            return historico.stream()
                    .filter(registro -> {
                        String data = registro.split(",")[3];
                        String mesAnoRegistro = data.substring(3, 10); // Assumindo que a data está no formato dd/MM/yyyy
                        return mesAnoRegistro.equals(mesAno);
                    })
                    .mapToDouble(registro -> Double.parseDouble(registro.split(",")[6]))
                    .sum();
        }

        public double calcularValorMedioUtilizacao() {
            return historico.isEmpty() ? 0 : historico.stream()
                    .mapToDouble(registro -> Double.parseDouble(registro.split(",")[6]))
                    .average()
                    .orElse(0);
        }

        public String gerarRankingClientesPorArrecadacao(String mesAno) {
            Map<String, Double> arrecadacaoPorCliente = new HashMap<>();

            historico.stream()
                    .filter(registro -> {
                        String data = registro.split(",")[3];
                        String mesAnoRegistro = data.substring(3, 10); // Assumindo que a data está no formato dd/MM/yyyy
                        return mesAnoRegistro.equals(mesAno);
                    })
                    .forEach(registro -> {
                        String cpfCliente = registro.split(",")[0];
                        double valor = Double.parseDouble(registro.split(",")[6]);
                        arrecadacaoPorCliente.put(cpfCliente, arrecadacaoPorCliente.getOrDefault(cpfCliente, 0.0) + valor);
                    });

            return arrecadacaoPorCliente.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .map(entry -> "CPF: " + entry.getKey() + " - Total: R$ " + String.format("%.2f", entry.getValue()))
                    .collect(Collectors.joining("\n"));
        }


        public void salvarHistorico(String cpfCliente, String placaVeiculo, LocalDateTime entrada, LocalDateTime saida, double valor) throws IOException {
            Veiculo veiculo = veiculos.get(placaVeiculo);
            if (veiculo == null) {
                throw new IllegalArgumentException("Veículo não encontrado para a placa: " + placaVeiculo);
            }

            String modeloVeiculo = veiculo.getModelo();
            String dataEntrada = entrada.format(formatter);
            String dataSaida = saida.format(formatter);
            String duracao = String.valueOf(Duration.between(entrada, saida).toMinutes());

            String novoRegistro = String.join(",", cpfCliente, modeloVeiculo, placaVeiculo, dataEntrada, dataSaida, duracao, String.valueOf(valor));
            historico.add(novoRegistro);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/historico.txt", true))) {
                writer.write(novoRegistro);
                writer.newLine();
            }
        }

        public void carregarHistorico(String filePath) throws IOException {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(",");
                    String cpfCliente = dados[0];
                    String modeloVeiculo = dados[1];
                    String placaVeiculo = dados[2];
                    String dataEntrada = dados[3];
                    String dataSaida = dados[4];
                    String duracao = dados[5];
                    String valor = dados[6];

                    // Adicionar ao histórico
                    historico.add(String.join(",", cpfCliente, modeloVeiculo, placaVeiculo, dataEntrada, dataSaida, duracao, valor));
                }
            }
        }

        public void adicionarRegistroHistorico(String cpfCliente, Veiculo veiculo, String dataEntrada, String dataSaida, String duracao, String valor) {
            String registro = String.join(",", cpfCliente, veiculo.getModelo(), veiculo.getPlaca(), dataEntrada, dataSaida, duracao, valor);
            historico.add(registro);
        }

        public List<String> obterHistoricoPorCliente(String cpfCliente) {
            List<String> historicoCliente = new ArrayList<>();
            for (String registro : historico) {
                if (registro.startsWith(cpfCliente + ",")) {
                    historicoCliente.add(registro);
                }
            }
            return historicoCliente;
        }

        private boolean isVeiculoEstacionado(Veiculo veiculo) {
            return parqueA.isVeiculoEstacionado(veiculo) || parqueB.isVeiculoEstacionado(veiculo) || parqueC.isVeiculoEstacionado(veiculo);
        }
    }
