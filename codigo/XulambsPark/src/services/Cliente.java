package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Cliente {

	// ATRIBUTOS
	private String identificador;
	private String cpf;
	private String codigo;
	private Veiculo[] veiculos;

	// CONSTRUTOR
	public Cliente(String identificador, String cpf, String codigo, Veiculo[] veiculos) {
		this.identificador = identificador;
		this.cpf = cpf;
		this.codigo = codigo;
		this.veiculos = veiculos;
	}

	// GETS E SETS
	public String getIdentificador() {
		return identificador;
	}

	public String getCpf() {
		return cpf;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Veiculo[] getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(Veiculo[] veiculos) {
		this.veiculos = veiculos;
	}

	// MÉTODOS
	public void cadastrarCliente() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Digite o seu nome ou apelido: ");
		this.identificador = scanner.nextLine();

		System.out.print("Digite o CPF: ");
		this.cpf = scanner.nextLine();

		System.out.print("Digite o código: ");
		this.codigo = gerarIdAleatorio();

		salvarClienteEmArquivo();

		scanner.close(); // Fechar o Scanner após o uso
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

	public void atualizarCliente() {
		// Implementação do metodo atualizar cliente
	}

	public static void main(String[] args) {
		Cliente cliente = new Cliente(null, null, null, null);
		cliente.cadastrarCliente();
	}

}


