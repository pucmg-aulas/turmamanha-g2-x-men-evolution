package services;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;


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
		for (int i = 0; i < parques.length; i++) {
			System.out.println((i + 1) + "Parque" + parques[i].getCodigoDoParque());
		}
	}

	/*MÉTODOS*/
	public void listarParques() {
		System.out.println("Parques cadastrados:");
		if (parques == null || parques.length == 0) {
			System.out.println("Nenhum parque cadastrado.");
			return;
		}

		for (int i = 0; i < parques.length; i++) {
			System.out.println((i + 1) + ". Parque Código: " + parques[i].getCodigoDoParque());
		}
	}

	public static void listarVagas() {
		Scanner sc = new Scanner(System.in);

		// Listar os parques disponíveis
		listarParques();

		// Pedir para o usuário selecionar um parque
		System.out.println("Digite o código do parque que deseja listar as vagas:");
		String codigoDoParque = sc.nextLine();

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
			boolean vagasEncontradas = false;
			for (Vaga vaga : vagasDisponiveis) {
				// Verificar se a vaga está disponível
				if (vaga.verificarVaga()) {
					System.out.println("- Código da Vaga: " + vaga.getCodigoDaVaga());
					vagasEncontradas = true;
				}
			}

			// Se nenhuma vaga disponível foi encontrada
			if (!vagasEncontradas) {
				System.out.println("Não há vagas disponíveis no parque " + codigoDoParque + ".");
			}

		} catch (Exception e) {
			System.out.println("Erro ao listar vagas: " + e.getMessage());
		} finally {
			sc.close();
		}
	}

	public static void cadastrarVaga(String codigoDoParque, Vaga novaVaga, Cliente cliente, Veiculo veiculo, Cobrança cobranca) {
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

			// Verifica se a vaga existe no parque
			Vaga[] vagas = parqueSelecionado.getVagas();
			boolean vagaEncontrada = false;
			for (Vaga vaga : vagas) {
				if (vaga.getCodigoDaVaga().equals(novaVaga.getCodigoDaVaga())) {
					vagaEncontrada = true;

					// Verifica se a vaga já está ocupada
					if (!vaga.verificarVaga()) {
						throw new IllegalArgumentException("Vaga já ocupada.");
					}

					// Verifica se a vaga é especial e se o cliente pode usar
					if ((vaga instanceof Vaga.VagaIdoso || vaga instanceof Vaga.VagaPCD || vaga instanceof Vaga.VagaVip) && !cliente.isVagaEspecial()) {
						throw new IllegalArgumentException("Cliente não tem direito a usar vaga especial.");
					}

					// Solicitar horário de entrada e saída ao usuário
					Scanner sc = new Scanner(System.in);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

					System.out.println("Digite o horário de entrada (formato: dd/MM/yyyy HH:mm): ");
					String entradaInput = sc.nextLine();
					LocalDateTime horarioDeEntrada = LocalDateTime.parse(entradaInput, formatter);

					System.out.println("Digite o horário de saída (formato: dd/MM/yyyy HH:mm): ");
					String saidaInput = sc.nextLine();
					LocalDateTime horarioDeSaida = LocalDateTime.parse(saidaInput, formatter);

					// Associar o horário de entrada e saída à cobrança
					cobranca.setHorarioDeEntrada(horarioDeEntrada);
					cobranca.setHorarioDeSaida(horarioDeSaida);

					// Associar o veículo à vaga (exemplo: vaga.setVeiculo(veiculo);)
					System.out.println("Veículo " + veiculo.getPlaca() + " foi associado à vaga " + novaVaga.getCodigoDaVaga() + " no parque " + codigoDoParque);

					// Salvar informações no arquivo de texto
					salvarInformacoesNoArquivo(parqueSelecionado, novaVaga, cliente, veiculo, cobranca);

					break;
				}
			}

			// Se a vaga não foi encontrada
			if (!vagaEncontrada) {
				throw new IllegalArgumentException("Vaga não encontrada: " + novaVaga.getCodigoDaVaga());
			}

		} catch (IllegalArgumentException e) {
			System.out.println("Erro: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro inesperado: " + e.getMessage());
		}
	}

	private void salvarInformacoesNoArquivo(ParquesDeEstacionamento parque, Vaga vaga, Cliente cliente, Veiculo veiculo, Cobrança cobranca) {
		String nomeArquivo = "registro_vagas.txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
			writer.write("Parque: " + parque.getNomeDoParque());
			writer.newLine();
			writer.write("Código do Parque: " + parque.getCodigoDoParque());
			writer.newLine();
			writer.write("Vaga: " + vaga.getCodigoDaVaga());
			writer.newLine();
			writer.write("Cliente: " + cliente.getIdentificador());
			writer.newLine();
			writer.write("Veículo: " + veiculo.getPlaca());
			writer.newLine();
			writer.write("Horário de Entrada: " + cobranca.getHorarioDeEntrada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
			writer.newLine();
			writer.write("Horário de Saída: " + cobranca.getHorarioDeSaida().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
			writer.newLine();
			writer.write("-----------------------------------");
			writer.newLine();
			System.out.println("Informações da vaga salvas no arquivo.");
		} catch (IOException e) {
			System.out.println("Erro ao salvar informações no arquivo: " + e.getMessage());
		}
	}

	public static void liberarVaga(String codigoDoParque, String codigoDaVaga, Cliente cliente, Cobrança cobranca) {
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

			// Verifica se a vaga existe no parque
			Vaga[] vagas = parqueSelecionado.getVagas();
			Vaga vagaSelecionada = null;
			for (Vaga vaga : vagas) {
				if (vaga.getCodigoDaVaga().equals(codigoDaVaga)) {
					vagaSelecionada = vaga;
					break;
				}
			}

			// Verifica se a vaga foi encontrada
			if (vagaSelecionada == null) {
				throw new IllegalArgumentException("Vaga não encontrada: " + codigoDaVaga);
			}

			// Verifica se a vaga está ocupada
			if (vagaSelecionada.verificarVaga()) {
				throw new IllegalArgumentException("A vaga já está livre.");
			}

			// Realizar o pagamento antes de liberar a vaga
			boolean pagamentoRealizado = cobranca.pagarEstacionamento(cliente, vagaSelecionada);
			if (!pagamentoRealizado) {
				throw new IllegalStateException("Pagamento não realizado. A vaga não pode ser liberada.");
			}

			// Liberar a vaga após o pagamento
			// Aqui você implementa a lógica para liberar a vaga.
			System.out.println("Pagamento realizado com sucesso. A vaga " + codigoDaVaga + " no parque " + codigoDoParque + " foi liberada.");

			// Remover informações da vaga do arquivo
			removerInformacoesDoArquivo(codigoDoParque, codigoDaVaga);

		} catch (IllegalArgumentException e) {
			System.out.println("Erro: " + e.getMessage());
		} catch (IllegalStateException e) {
			System.out.println("Erro: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro inesperado: " + e.getMessage());
		}
	}

	private void removerInformacoesDoArquivo(String codigoDoParque, String codigoDaVaga) {
		String nomeArquivo = "registro_vagas.txt";
		StringBuilder novoConteudo = new StringBuilder();

		try (Scanner fileScanner = new Scanner(new java.io.File(nomeArquivo))) {
			boolean ignorarLinhas = false;

			while (fileScanner.hasNextLine()) {
				String linha = fileScanner.nextLine();

				// Verificar se a linha corresponde ao parque e vaga liberada
				if (linha.contains("Código do Parque: " + codigoDoParque) && linha.contains("Vaga: " + codigoDaVaga)) {
					ignorarLinhas = true; // Encontrou a entrada da vaga, começa a ignorar as linhas associadas
				}

				if (!ignorarLinhas) {
					novoConteudo.append(linha).append(System.lineSeparator());
				}

				// Quando chegar à linha separadora (----), parar de ignorar
				if (linha.contains("-----------------------------------")) {
					ignorarLinhas = false;
				}
			}

		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo: " + e.getMessage());
			return;
		}

		// Reescrever o arquivo com o novo conteúdo (sem as linhas da vaga liberada)
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
			writer.write(novoConteudo.toString());
			System.out.println("Informações da vaga " + codigoDaVaga + " removidas do arquivo.");
		} catch (IOException e) {
			System.out.println("Erro ao reescrever o arquivo: " + e.getMessage());
		}
	}
}


