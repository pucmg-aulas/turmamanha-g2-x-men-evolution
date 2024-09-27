package services;

public class Veiculo {
	
	/*ATRIBUTOS*/
	private String placa;
	private String modelo;
	private String cor;
	private String tipoDeVeiculo;
	
	/*CONSTRUTOR*/
	public Veiculo(String placa, String modelo, String cor, String tipoDeVeiculo) {
		super();
		this.placa = placa;
		this.modelo = modelo;
		this.cor = cor;
		this.tipoDeVeiculo = tipoDeVeiculo;
	}
	
	/*GETS E SETS*/
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


	
	
	/*MÉTODOS*/
	public void cadastrarVeiculo() {
		
		
		
	}


	
}
