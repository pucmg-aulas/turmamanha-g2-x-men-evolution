package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Veiculo {

	/* ATRIBUTOS */
	private String placa;
	private String modelo;
	private String cor;
	private String tipoDeVeiculo;

	private static List<Veiculo> veiculosCadastrados = new ArrayList<>(); // Lista para armazenar veículos cadastrados

	/* CONSTRUTOR */
	public Veiculo(String placa, String modelo, String cor, String tipoDeVeiculo) {
		this.placa = placa;
		this.modelo = modelo;
		this.cor = cor;
		this.tipoDeVeiculo = tipoDeVeiculo;
	}

	public static Veiculo buscarVeiculoPorPlaca(String placa) {
		for (Veiculo veiculo : veiculosCadastrados) {
			if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
				return veiculo;
			}
		}
		return null; // Return null if no vehicle is found with the given plate
	}
	/* GETS E SETS */
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getTipoDeVeiculo() {
		return tipoDeVeiculo;
	}

	public void setTipoDeVeiculo(String tipoDeVeiculo) {
		this.tipoDeVeiculo = tipoDeVeiculo;
	}

	/* MÉTODOS */
	public void cadastrarVeiculo() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Digite a placa: ");
		this.placa = scanner.nextLine();
		System.out.print("Digite o modelo: ");
		this.modelo = scanner.nextLine();
		System.out.print("Digite a cor: ");
		this.cor = scanner.nextLine();
		System.out.print("Digite o tipo de veículo: ");
		this.tipoDeVeiculo = scanner.nextLine();

		if (isPlacaValida(this.placa) && !this.modelo.isEmpty()) { // Validações simples
			veiculosCadastrados.add(this);
			System.out.println("Veículo cadastrado com sucesso: " + this);
		} else {
			System.out.println("Erro ao cadastrar veículo. Verifique os dados fornecidos.");
		}
	}

	private boolean isPlacaValida(String placa) {
		// Implementar uma validação básica para a placa no formato Mercosul
		return placa.matches("[A-Z]{3}[0-9][A-Z][0-9]{2}"); // Exemplo: ABC1D23
	}

	@Override
	public String toString() {
		return "Veiculo [placa=" + placa + ", modelo=" + modelo + ", cor=" + cor + ", tipoDeVeiculo=" + tipoDeVeiculo + "]";
	}

	public static List<Veiculo> getVeiculosCadastrados() {
		return veiculosCadastrados;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Menu de Veículos:");
			System.out.println("1. Cadastrar Veículo");
			System.out.println("2. Listar Veículos Cadastrados");
			System.out.println("3. Sair");
			System.out.print("Escolha uma opção: ");
			int opcao = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (opcao) {
				case 1:
					Veiculo veiculo = new Veiculo("", "", "", "");
					veiculo.cadastrarVeiculo();
					break;
				case 2:
					listarVeiculos();
					break;
				case 3:
					System.out.println("Saindo...");
					scanner.close();
					return;
				default:
					System.out.println("Opção inválida. Tente novamente.");
			}
		}
	}

	private static void listarVeiculos() {
		System.out.println("Veículos cadastrados:");
		for (Veiculo veiculo : Veiculo.getVeiculosCadastrados()) {
			System.out.println(veiculo);
		}
	}
}
