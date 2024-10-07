package entities;

import java.io.*;
import java.util.*;

public class SistemaEstacionamento {
    private ArrayList<ParqueEstacionamento> parques;
    private ArrayList<Cliente> clientes;

    public SistemaEstacionamento() {
        parques = new ArrayList<>();
        clientes = new ArrayList<>();
    }

    public void definirParquesPreDefinidos() {
        parques.add(new ParqueEstacionamento("Parque A", 10));
        parques.add(new ParqueEstacionamento("Parque B", 10));
        parques.add(new ParqueEstacionamento("Parque C", 10));
        System.out.println("");
    }

    public void listarParques() {
        System.out.println("Parques de Estacionamento Disponíveis:");
        for (ParqueEstacionamento parque : parques) {
            System.out.println("- " + parque.getNome());
        }
    }

    public void cadastrarCliente(Scanner scanner) {
        Cliente cliente = new Cliente("", "");
        cliente.cadastrarCliente(scanner);
        clientes.add(cliente);
    }

    public void cadastrarVeiculo(Scanner scanner) {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Cadastre um cliente antes de adicionar um veículo.");
            return;
        }

        System.out.print("Digite o CPF do cliente ao qual deseja adicionar um veículo: ");
        String cpf = scanner.nextLine();
        Cliente cliente = buscarClientePorCpf(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Verifique o CPF e tente novamente.");
            return;
        }

        Veiculo veiculo = Veiculo.cadastrarVeiculo(scanner);
        cliente.adicionarVeiculo(veiculo);
        System.out.println("Veículo cadastrado com sucesso.");
        salvarDadosEmArquivo(); // Salvar os dados após o cadastro do veículo
    }

    public void associarVeiculoAVaga(Scanner scanner) {
        if (parques.isEmpty()) {
            System.out.println("Nenhum parque de estacionamento disponível.");
            return;
        }

        listarParques();  // Chama o método para listar parques

        System.out.print("Digite o nome do parque de estacionamento onde o veículo será estacionado: ");
        String nomeParque = scanner.nextLine();
        ParqueEstacionamento parque = buscarParquePorNome(nomeParque);

        if (parque == null) {
            System.out.println("Parque de estacionamento não encontrado.");
            return;
        }

        System.out.print("Digite a placa do veículo que deseja associar à vaga: ");
        String placaVeiculo = scanner.nextLine();
        Veiculo veiculo = buscarVeiculoPorPlaca(placaVeiculo);

        if (veiculo == null) {
            System.out.println("Veículo não encontrado. Verifique a placa e tente novamente.");
            return;
        }

        System.out.print("Precisa de uma vaga especial (PCD, Idoso, VIP)? Se sim, digite o tipo, caso contrário, digite 'normal': ");
        String tipoVaga = scanner.nextLine();

        parque.associarVeiculoAVaga(veiculo, tipoVaga);
        salvarDadosEmArquivo(); // Salvar os dados após a associação
    }


    


    public void liberarVaga(Scanner scanner) {
        if (parques.isEmpty()) {
            System.out.println("Nenhum parque de estacionamento disponível.");
            return;
        }

        listarParques();  // Chama o método para listar parques

        System.out.print("Digite o nome do parque de estacionamento onde deseja liberar a vaga: ");
        String nomeParque = scanner.nextLine();
        ParqueEstacionamento parque = buscarParquePorNome(nomeParque);

        if (parque == null) {
            System.out.println("Parque de estacionamento não encontrado.");
            return;
        }

        System.out.print("Digite a placa do veículo que deseja liberar a vaga: ");
        String placaVeiculo = scanner.nextLine();
        parque.liberarVagaPorPlaca(placaVeiculo);  // Isso agora calcula e exibe o valor cobrado
        salvarDadosEmArquivo(); // Salvar os dados após liberar a vaga
    }



    public void visualizarVeiculosEstacionados(Scanner scanner) {
        listarParques();  // Chama o método para listar parques

        System.out.print("Digite o nome do parque de estacionamento que deseja visualizar: ");
        String nomeParque = scanner.nextLine();
        ParqueEstacionamento parque = buscarParquePorNome(nomeParque);

        if (parque != null) {
            parque.visualizarVeiculosEstacionados();
        } else {
            System.out.println("Parque de estacionamento não encontrado.");
        }
    }

    private Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    private Veiculo buscarVeiculoPorPlaca(String placa) {
        for (Cliente cliente : clientes) {
            Veiculo veiculo = cliente.buscarVeiculoPorPlaca(placa);
            if (veiculo != null) {
                return veiculo;
            }
        }
        return null;
    }

    private ParqueEstacionamento buscarParquePorNome(String nome) {
        for (ParqueEstacionamento parque : parques) {
            if (parque.getNome().equalsIgnoreCase(nome)) {
                return parque;
            }
        }
        return null;
    }

    public void salvarDadosEmArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sistema.txt"))) {
            // Salvar clientes
            writer.write("Clientes:\n");
            for (Cliente cliente : clientes) {
                writer.write(cliente.getCodigo() + "\n");
                for (Veiculo veiculo : cliente.getVeiculos()) {
                    writer.write("  " + veiculo.toString() + "\n");
                }
            }

            // Salvar parques
            writer.write("Parques:\n");
            for (ParqueEstacionamento parque : parques) {
                writer.write(parque.getNome() + "\n");
                for (Vaga vaga : parque.getVagas()) {
                    if (vaga.isOcupada()) {
                        writer.write("  Vaga " + vaga.getCodigo() + ": " + vaga.getVeiculo().toString() + "\n");
                    } else {
                        writer.write("  Vaga " + vaga.getCodigo() + ": Disponível\n");
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao salvar dados do sistema: " + e.getMessage());
        }
    }
}
