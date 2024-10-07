package aplication;

import java.util.Scanner;
import entities.SistemaEstacionamento;

public class XulambsPark {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaEstacionamento sistema = new SistemaEstacionamento();

        // Parques pré-definidos
        sistema.definirParquesPreDefinidos();

        int opcao = 0;

        do {
            System.out.println("\n--- Menu XulambsPark ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Cadastrar Veículo");
            System.out.println("3. Associar à Vaga");
            System.out.println("4. Liberar Vaga");
            System.out.println("5. Visualizar Veículos Estacionados");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir a nova linha

            switch (opcao) {
                case 1:
                    sistema.cadastrarCliente(scanner);
                    break;

                case 2:
                    sistema.cadastrarVeiculo(scanner);
                    break;

                case 3:
                    sistema.associarVeiculoAVaga(scanner);
                    break;

                case 4:
                    sistema.liberarVaga(scanner);
                    break;

                case 5:
                    sistema.visualizarVeiculosEstacionados(scanner);
                    break;

                case 6:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }

        } while (opcao != 6);

        scanner.close();
    }
} 
