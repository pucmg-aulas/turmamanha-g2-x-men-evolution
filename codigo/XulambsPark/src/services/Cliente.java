package entities;

import java.io.*;
import java.util.*;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String identificador;
    private String cpf;
    private String codigo;
    private List<Veiculo> veiculos;

    public Cliente(String identificador, String cpf) {
        this.identificador = identificador;
        this.cpf = cpf;
        this.codigo = gerarIdAleatorio();
        this.veiculos = new ArrayList<>();
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCodigo() {
        return codigo;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void cadastrarCliente(Scanner scanner) {
        System.out.print("Digite o seu nome ou apelido: ");
        this.identificador = scanner.nextLine();

        System.out.print("Digite o CPF: ");
        this.cpf = scanner.nextLine();

        salvarClienteEmArquivo();
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    private String gerarIdAleatorio() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    private void salvarClienteEmArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("clientes.txt", true))) {
            writer.write("----------");
            writer.newLine();
            writer.write("Identificador: " + this.identificador);
            writer.newLine();
            writer.write("CPF: " + this.cpf);
            writer.newLine();
            writer.write("Código: " + this.codigo);
            writer.newLine();
            writer.write("----------");
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equals(placa)) {
                return veiculo;
            }
        }
        return null;
    }
}
