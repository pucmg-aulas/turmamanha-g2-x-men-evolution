package services;

public class ParquesDeEstacionamento {
	
	/*ATRIBUTOS*/
	private String codigoDoParque;
	private Vaga vagas[];
	
	
	/*CONSTRUTOR*/
	public ParquesDeEstacionamento(String codigoDoParque, Vaga[] vagas) {
		super();
		this.codigoDoParque = codigoDoParque;
		this.vagas = vagas;
	}

	
	/*GETS E SETS*/
	public String getCodigoDoParque() {
		return codigoDoParque;
	}


	public void setCodigoDoParque(String codigoDoParque) {
		this.codigoDoParque = codigoDoParque;
	}


	public Vaga[] getVagas() {
		return vagas;
	}


	public void setVagas(Vaga[] vagas) {
		this.vagas = vagas;
	}

	
	
	/*MÉTODOS*/
	public void criarVaga() {
		
		
	}
	
	
	public void atualizarVaga() {
		
		
	}
	
	public void darBaixaVaga() {
		
		
	}
	
}
