package services;

public class Cliente {
	
	// ATRIBUTOS
	private String identificador;
	private String cpf;
	private String codigo;
	private Veiculo veiculos[];
	

	// CONSTRUTOR
	public Cliente(String identificador, String cpf, String codigo, Veiculo[] veiculos) {
		super();
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
	public void cadastrarCliente(){
		
		
	}
	
	
	public void atualizarCliente(){
		
		
		
	}

	public void associarVeiculo(Veiculo veiculo) {
		// Verifica se o veículo já está associado ao cliente.
		for (Veiculo v : veiculos) {
			if (v.getPlaca().equals(veiculo.getPlaca())) {
				System.out.println("Veículo já está associado ao cliente.");
				return;
			}
		}

		// Associa o veículo ao cliente.
		veiculos.add(veiculo);
		System.out.println("Veículo associado com sucesso ao cliente " + identificador);
	}
	
	
	
}
