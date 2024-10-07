package services;
import services.Vaga;
import java.util.Scanner;
import java.util.ArrayList;

public class ParquesDeEstacionamento {
	private String codigoDoParque;
	private ArrayList<Vaga> vagas;


	public ParquesDeEstacionamento(String codigoDoParque) {
		this.codigoDoParque = codigoDoParque;
		this.vagas = new ArrayList<>();
	}


	public  void criarVaga(Vaga vaga) {
		System.out.println("Digite um código para a vaga: ");
		Scanner scanner = new Scanner(System.in);
		String alterarCodigoDaVaga = scanner.nextLine();
		vaga.setCodigoDaVaga(alterarCodigoDaVaga);
	}

	public void atualizarVaga(Vaga vaga) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o código da vaga que deseja atualizar: ");
		String codigoDaVaga = scanner.nextLine();
		System.out.println("Digite o novo tipo da vaga (vip, idoso, pcd): ");
		String novoTipo = scanner.nextLine();

		Vaga novaVaga = Vaga.mudarTipo(vaga, novoTipo);
		for (int i = 0; i < vagas.size(); i++) {
			if (vagas.get(i).getCodigoDaVaga().equals(codigoDaVaga)) {
				vagas.set(i, novaVaga);
				System.out.println("Vaga atualizada com sucesso.");
				return;
			}
		}
		System.out.println("Vaga não encontrada.");
	}


	public String getCodigoDoParque() {
		return codigoDoParque;
	}

	public void setCodigoDoParque(String codigoDoParque) {
		this.codigoDoParque = codigoDoParque;
	}

	public ArrayList<Vaga> getVagas() {
		return vagas;
	}
	public static void main(String[] args) {
		ParquesDeEstacionamento parque = new ParquesDeEstacionamento("Parque1");
		boolean continuar = true;
		while (continuar) {
			System.out.println("1 - Criar Vaga");
			System.out.println("2 - Atualizar Vaga");
			System.out.println("3 - Listar Vagas");
			System.out.println("4 - Sair");
			System.out.print("Escolha uma opção: ");

			try (Scanner scanner = new Scanner(System.in)) {
				int opcao = scanner.nextInt();
				scanner.nextLine(); // Consume newline
				switch (opcao) {
					case 1:
						Vaga vaga = new Vaga(""); // Create a new Vaga instance
						parque.criarVaga(vaga);
						break;
					case 2:
						Vaga vagaParaAtualizar = new Vaga(""); // Create a new Vaga instance
						parque.atualizarVaga(vagaParaAtualizar);
						break;
					case 3:
						for (Vaga v : parque.vagas) {
							System.out.println("Vaga: " + v.getCodigoDaVaga());
						}
						break;
					case 4:
						continuar = false;
						break;
					default:
						System.out.println("Opção inválida.");
						break;
				}
			} catch (Exception e) {
				System.err.println("Erro ao ler opção: " + e.getMessage());
			}
		}
	}
}

