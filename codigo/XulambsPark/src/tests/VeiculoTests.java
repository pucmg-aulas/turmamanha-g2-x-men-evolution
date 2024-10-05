package services;

import java.util.ArrayList;
import java.util.List;

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
		if (isPlacaValida(placa) && !modelo.isEmpty()) { // Validações simples
			veiculosCadastrados.add(this);
			System.out.println("Veículo cadastrado com sucesso: " + this);
		} else {
			System.out.println("Erro ao cadastrar veículo. Verifique os dados fornecidos.");
		}
	}

	private boolean isPlacaValida(String placa) {
		// Implementar uma validação básica para a placa
		return placa.matches("[A-Z]{3}-[0-9]{4}"); // Exemplo: AAA-0000
	}

	@Override
	public String toString() {
		return "Veiculo [placa=" + placa + ", modelo=" + modelo + ", cor=" + cor + ", tipoDeVeiculo=" + tipoDeVeiculo + "]";
	}

	public static List<Veiculo> getVeiculosCadastrados() {
		return veiculosCadastrados;
	}
}
