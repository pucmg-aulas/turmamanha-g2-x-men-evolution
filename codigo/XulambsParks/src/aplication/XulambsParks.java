package aplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.Cliente;
import entities.Estacionamento;
import entities.SistemaEstacionamento;
import entities.Vaga;
import entities.Veiculo;

public class XulambsParks {
    public static void main(String[] args) {
        List<Cliente> clientes = new ArrayList<>();
        List<Veiculo> veiculos = new ArrayList<>();
        List<Vaga> vagas = Vaga.inicializarVagas();  
        Estacionamento estacionamento = new Estacionamento();
        SistemaEstacionamento sistema = new SistemaEstacionamento(vagas, estacionamento);

        try {
            sistema.carregarDadosDeArquivo(clientes, veiculos, "clientes.txt", "veiculos.txt", "vagas.txt");
        	} catch (IOException e) {
        }

        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n--- Xulambs Park ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Cadastrar Veículo");
            System.out.println("3. Estacionar Veículo");
            System.out.println("4. Liberar Vaga");
            System.out.println("5. Mostrar Veículos Estacionados");
            System.out.println("6. Visualizar Veículos por cliente");
            System.out.println("7. Salvar e Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    Cliente.cadastrarCliente(scanner, clientes);
                    break;
                case 2:
                    Veiculo.cadastrarVeiculo(scanner, clientes, veiculos);
                    break;
                case 3:
                    sistema.estacionarVeiculo(scanner, clientes, veiculos);
                    break;
                case 4:
                    sistema.liberarVaga(scanner);
                    break;
                case 5:
                    sistema.mostrarVeiculosEstacionados();
                    break;
                case 6:
                    sistema.visualizarVeiculosPorCliente(scanner, veiculos);
                    break;
                case 7:
                    try {
                        sistema.salvarDadosEmArquivo(clientes, veiculos, "clientes.txt", "veiculos.txt", "vagas.txt");
                        System.out.println("Dados salvos com sucesso!");
                    } catch (IOException e) {
                        System.out.println("Erro ao salvar os dados: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 7);

        scanner.close();
    }
}
