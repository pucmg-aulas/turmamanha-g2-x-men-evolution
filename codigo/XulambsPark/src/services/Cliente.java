package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cliente {

	// ATRIBUTOS
	private String identificador;
	private String cpf;
	private String codigo;
	private List<Veiculo> veiculos;
	private int veiculoCount;

	// CONSTRUTOR
	public Cliente() {
		this.veiculos = new ArrayList<>();
		this.veiculoCount = 0;
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

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	// MÉTODOS
	public void cadastrarCliente() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Digite o seu nome ou apelido: ");
			this.identificador = scanner.nextLine();

			System.out.print("Digite o CPF: ");
			this.cpf = scanner.nextLine();

			System.out.print("Digite o código: ");
			this.codigo = gerarIdAleatorio();

			salvarClienteEmArquivo();
		}
	}

	private String gerarIdAleatorio() {
		return java.util.UUID.randomUUID().toString().substring(0, 6);
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
			writer.write("Veículos: " + veiculoCount);
			salvarVeiculosEmArquivo(writer);
			writer.write("----------");
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Erro ao salvar cliente: " + e.getMessage());
		}
	}

	private void salvarVeiculosEmArquivo(BufferedWriter writer) throws IOException {
		for (Veiculo veiculo : veiculos) {
			writer.newLine();
			writer.write("  Placa: " + veiculo.getPlaca());
			writer.newLine();
			writer.write("  Modelo: " + veiculo.getModelo());
			writer.newLine();
			writer.write("  Cor: " + veiculo.getCor());
			writer.newLine();
			writer.write("  Tipo de Veículo: " + veiculo.getTipoDeVeiculo());
		}
	}

	public void atualizarCliente() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Digite o novo nome ou apelido: ");
			this.identificador = scanner.nextLine();

			System.out.print("Digite o novo CPF: ");
			this.cpf = scanner.nextLine();

			System.out.print("Digite o novo código: ");
			this.codigo = gerarIdAleatorio();

			salvarClienteEmArquivo();
		}
	}

	public void associarVeiculoAoCliente() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Deseja cadastrar um novo veículo para o cliente? (S/N)");
			String resposta = scanner.nextLine();

			if (resposta.equalsIgnoreCase("S")) {
				Veiculo veiculo = Veiculo.cadastrarVeiculo();
				veiculos.add(veiculo);
				veiculoCount++;
				System.out.println("Veículo " + veiculo.getPlaca() + " associado com sucesso ao cliente.");
			} else {
				System.out.print("Digite a placa do veículo a ser associado: ");
				String placa = scanner.nextLine();

				Veiculo veiculo = Veiculo.buscarVeiculoPorPlaca(placa);
				if (veiculo != null) {
					veiculos.add(veiculo);
					veiculoCount++;
					System.out.println("Veículo com placa " + veiculo.getPlaca() + " associado com sucesso.");
				} else {
					System.out.println("Veículo não encontrado.");
				}
			}

			salvarClienteEmArquivo();
		}
	}

	public static void main(String[] args) {
		Cliente cliente = new Cliente();
		cliente.cadastrarCliente();
		cliente.associarVeiculoAoCliente();
	}
}

