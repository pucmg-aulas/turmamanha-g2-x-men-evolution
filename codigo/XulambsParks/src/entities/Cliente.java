package entities;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Cliente {
    private String nome;
    private String cpf;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public static void cadastrarCliente(Scanner scanner, List<Cliente> clientes) {
        System.out.println("Cadastro de Cliente:");
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        Cliente cliente = new Cliente(nome, cpf);
        clientes.add(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public static void salvarClientes(List<Cliente> clientes, String arquivo) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo));
        for (Cliente cliente : clientes) {
            writer.write(cliente.nome + ";" + cliente.cpf);
            writer.newLine();
        }
        writer.close();
    }

    public static void carregarClientes(List<Cliente> clientes, String arquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");
            Cliente cliente = new Cliente(dados[0], dados[1]);
            clientes.add(cliente);
        }
        reader.close();
    }
} 





