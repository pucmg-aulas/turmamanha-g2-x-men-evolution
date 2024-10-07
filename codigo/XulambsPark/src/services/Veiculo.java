package entities;

import java.util.Scanner;

public class Veiculo {
    private String placa;
    private String cor;
    private String modelo;
    private String tipo;

    // Construtor
    public Veiculo(String placa, String cor, String modelo, String tipo) {
        this.placa = placa;
        this.cor = cor;
        this.modelo = modelo;
        this.tipo = tipo;
    }

    // Getters
    public String getPlaca() {
        return placa;
    }

    // Método estático para cadastrar veículo
    public static Veiculo cadastrarVeiculo(Scanner scanner) {
        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine();

        System.out.print("Digite a cor do veículo: ");
        String cor = scanner.nextLine();

        System.out.print("Digite o modelo do veículo: ");
        String modelo = scanner.nextLine();

        System.out.print("Digite o tipo do veículo: ");
        String tipo = scanner.nextLine();

        // Cria uma nova instância de Veiculo
        return new Veiculo(placa, cor, modelo, tipo);
    }
    
    @Override
    public String toString() {
        return "Placa: " + placa + ", Cor: " + cor + ", Modelo: " + modelo + ", Tipo: " + tipo;
    }

}
