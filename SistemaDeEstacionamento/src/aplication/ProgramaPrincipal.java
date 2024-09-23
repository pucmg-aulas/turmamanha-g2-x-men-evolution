package aplication;

import java.util.Scanner;

public class ProgramaPrincipal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int opcao;

        do {
            System.out.println("\nSistema de Estacionamento");
            System.out.println("1- Cadastro de Vaga");
            System.out.println("2- Cadastro de Cliente");
            System.out.println("3- Consultar Estacionamento");
            System.out.println("4- Dar Baixa na Vaga");
            System.out.println("5- Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    estacionamento.cadastrarVaga();
                    break;
                case 2:
                    estacionamento.cadastrarCliente();
                    break;
                case 3:
                    estacionamento.consultarEstacionamento();
                    break;
                case 4:
                    estacionamento.darBaixaVaga();
                    break;
                case 5:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 5);

        scanner.close();
    }
}

