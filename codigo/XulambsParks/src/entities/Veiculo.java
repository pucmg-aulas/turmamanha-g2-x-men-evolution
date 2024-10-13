package entities;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Veiculo {
    private String placa;
    private String modelo;
    private String tipo;
    private String cor;  // Nova propriedade
    private String cpfCliente;

    public Veiculo(String placa, String modelo, String tipo, String cor, String cpfCliente) {
        this.placa = placa;
        this.modelo = modelo;
        this.tipo = tipo;
        this.cor = cor;
        this.cpfCliente = cpfCliente;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCor() {
        return cor;  // Novo método
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public static void cadastrarVeiculo(Scanner scanner, List<Cliente> clientes, List<Veiculo> veiculos) {
        System.out.println("Cadastro de Veículo:");
        System.out.print("Digite a placa: ");
        String placa = scanner.nextLine();
        System.out.print("Digite o modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Digite o tipo (carro/moto): ");
        String tipo = scanner.nextLine();
        System.out.print("Digite a cor: ");
        String cor = scanner.nextLine();  // Solicita a cor
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        boolean clienteExiste = false;
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                clienteExiste = true;
                break;
            }
        }

        if (!clienteExiste) {
            System.out.println("Cliente não encontrado. Cadastre o cliente primeiro.");
            return;
        }

        Veiculo veiculo = new Veiculo(placa, modelo, tipo, cor, cpf);  // Adiciona a cor
        veiculos.add(veiculo);
        System.out.println("Veículo cadastrado com sucesso!");
    }

    public static void salvarVeiculos(List<Veiculo> veiculos, String arquivo) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo));
        for (Veiculo veiculo : veiculos) {
            writer.write(veiculo.placa + ";" + veiculo.modelo + ";" + veiculo.tipo + ";" + veiculo.cor + ";" + veiculo.cpfCliente);
            writer.newLine();
        }
        writer.close();
    }

    public static void carregarVeiculos(List<Veiculo> veiculos, String arquivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length < 5) { // Verifica se há pelo menos 5 campos
                    System.out.println("Linha inválida no arquivo: " + linha);
                    continue; // Ignora a linha e continua com a próxima
                }
                Veiculo veiculo = new Veiculo(dados[0], dados[1], dados[2], dados[3], dados[4]); // Cria o veículo
                veiculos.add(veiculo);
            }
        }
    }

}
