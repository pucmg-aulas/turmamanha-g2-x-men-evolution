package aplication;
import java.util.Scanner;
import services.Cliente;
import services.Veiculo;
import services.Vaga;
import services.ParquesDeEstacionamento;
import services.SistemaDeEstacionamento;
import services.Cobrança;

public class XulambsPark {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int opcaomenu = sc.nextInt();

			System.out.println("BEM VINDO AO XULAMBS PARK");
			System.out.println("MENU");
			System.out.println(" 1 -Menu Cliente");
			System.out.println(" 2 -Menu Veiculo");
			System.out.println(" 3 -Menu Vaga");
			System.out.println(" 4 -Menu Parques");
			System.out.println(" 0 -Encerrar operação");


		switch(opcaomenu){
			case 1:
				System.out.println("Menu Cliente");
						Cliente.main(args);
						break;
			case 2:
				System.out.println("Menu Veiculo");
						Veiculo.main(args);
				break;
			case 3:
				System.out.println("Menu Vaga");
						Vaga.main(args);
						break;
				case 4:
				System.out.println("Menu Parques");
						ParquesDeEstacionamento.main(args);
				break;

			case 0:
				System.out.println("Encerrar operação");

				break;
			default:
				System.out.println("Opção inválida");

		}
		
		sc.close();
	}

}
