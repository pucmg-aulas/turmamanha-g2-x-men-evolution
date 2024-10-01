package services;
import java.util.Scanner;


public class SistemaDeEstacionamento {
		Scanner sc = new Scanner(System.in);
		String escolha;
	/*ATRIBUTOS*/
	private ParquesDeEstacionamento parques[];
	
	
	/*CONSTRUTOR*/
	public SistemaDeEstacionamento(ParquesDeEstacionamento[] parques) {
		super();
		this.parques = parques;
	}

	
	/*GETS E SETS*/
	public void getparques(ParquesDeEstacionamento[] parques) {
		System.out.println("Imprimindo parques cadastrados...");
		for(int i = 0; i< parques.length; i++){
			System.out.println((i + 1) + "Parque"+ parques[i].getCodigoDoParque());
		}
	}


	public void setParques(ParquesDeEstacionamento[] parques) {
		this.parques = parques;
	}


	
	
	/*MÉTODOS*/
	
	public void cadastrarVaga(String codigoDoParque, Vaga novaVaga) {
		System.out.println("Escolha um parque para registrar uma vaga :");

		for (ParquesDeEstacionamento parque : parques) {
			System.out.println("Parque: " + parque.getCodigoDoParque());
		}
		String codigoDoParqueEscolido = sc.next();
		ParquesDeEstacionamento parqueEscolhido = null;
		for(ParquesDeEstacionamento parque : parques) {
			if (parque.getCodigoDoParque().equals(codigoDoParqueEscolido)) {
				parqueEscolhido = parque;
				break;
			}
			if (parqueEscolhido == null) {
				throw new IllegalArgumentException("Parque não encontrado"+ codigoDoParqueEscolido);

			}
		}
		System.out.println("Digite a vaga para ser cadastrada:");
		for (Vaga vaga: ) {
			System.out.println("Parque: " + parque.getCodigoDoParque());
		}


	}
	public void liberarVaga() {
		
		
	}

	public void associarVeiculoAoCliente(Cliente cliente, String codigoDoParque) {
		Scanner sc = new Scanner(System.in);

		try {
			// Buscar o parque pelo código fornecido
			ParquesDeEstacionamento parqueSelecionado = null;
			for (ParquesDeEstacionamento parque : parques) {
				if (parque.getCodigoDoParque().equals(codigoDoParque)) {
					parqueSelecionado = parque;
					break;
				}
			}

			// Verifica se o parque foi encontrado
			if (parqueSelecionado == null) {
				throw new IllegalArgumentException("Parque não encontrado: " + codigoDoParque);
			}

			// Listar as vagas disponíveis no parque
			Vaga[] vagasDisponiveis = parqueSelecionado.getVagas();
			System.out.println("Vagas disponíveis no parque " + parqueSelecionado.getCodigoDoParque() + ":");
			for (Vaga vaga : vagasDisponiveis) {
				System.out.println("- Código da Vaga: " + vaga.getCodigoDaVaga());
			}

			// Listar os veículos do cliente
			Veiculo[] veiculos = cliente.getVeiculos();
			System.out.println("Veículos do cliente " + cliente.getIdentificador() + ":");
			for (Veiculo veiculo : veiculos) {
				System.out.println("- Placa: " + veiculo.getPlaca() + ", Modelo: " + veiculo.getModelo() + ", Cor: " + veiculo.getCor());
			}

			// Solicitar ao usuário a escolha do veículo e da vaga
			System.out.println("Digite a placa do veículo que deseja associar:");
			String placaDoVeiculo = sc.nextLine();
			System.out.println("Digite o código da vaga que deseja usar:");
			String codigoDaVaga = sc.nextLine();

			// Verifica se o veículo existe
			Veiculo veiculoSelecionado = null;
			for (Veiculo veiculo : veiculos) {
				if (veiculo.getPlaca().equals(placaDoVeiculo)) {
					veiculoSelecionado = veiculo;
					break;
				}
			}

			// Verifica se o veículo foi encontrado
			if (veiculoSelecionado == null) {
				throw new IllegalArgumentException("Veículo com placa " + placaDoVeiculo + " não encontrado para o cliente.");
			}

			// Verifica se a vaga existe no parque
			Vaga vagaSelecionada = null;
			for (Vaga vaga : vagasDisponiveis) {
				if (vaga.getCodigoDaVaga().equals(codigoDaVaga)) {
					vagaSelecionada = vaga;
					break;
				}
			}

			// Verifica se a vaga foi encontrada
			if (vagaSelecionada == null) {
				throw new IllegalArgumentException("Vaga não encontrada: " + codigoDaVaga);
			}

			// Associar o veículo à vaga
			// Aqui você pode implementar a lógica que define o veículo na vaga
			// Exemplo: vagaSelecionada.setVeiculo(veiculoSelecionado);
			System.out.println("Veículo com placa " + placaDoVeiculo + " foi associado à vaga " + codigoDaVaga + " no parque " + codigoDoParque);

		} catch (IllegalArgumentException e) {
			System.out.println("Erro: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro inesperado: " + e.getMessage());
		} finally {
			sc.close();
		}
	}
}
 
