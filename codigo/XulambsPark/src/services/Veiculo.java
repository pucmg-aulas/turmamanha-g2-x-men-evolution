package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Veiculo {
    private String placa;
    private String modelo;
    private String cor;
    private String tipoDeVeiculo;
    private static List<Veiculo> veiculosRegistrados = new ArrayList<>();

    public Veiculo(String placa, String modelo, String cor, String tipoDeVeiculo) {
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.tipoDeVeiculo = tipoDeVeiculo;
        veiculosRegistrados.add(this);
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCor() {
        return cor;
    }

    public String getTipoDeVeiculo() {
        return tipoDeVeiculo;
    }

    public static Veiculo buscarVeiculoPorPlaca(String placa) {
        for (Veiculo veiculo : veiculosRegistrados) {
            if (veiculo.getPlaca().equals(placa)) {
                return veiculo;
            }
        }
        return null;
    }

    public static Veiculo cadastrarVeiculo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine();
        System.out.print("Digite o modelo do veículo: ");
        String modelo = scanner.nextLine();
        System.out.print("Digite a cor do veículo: ");
        String cor = scanner.nextLine();
        System.out.print("Digite o tipo de veículo: ");
        String tipoDeVeiculo = scanner.nextLine();

        Veiculo veiculo = new Veiculo(placa, modelo, cor, tipoDeVeiculo);
        System.out.println("Veículo cadastrado com sucesso.");
        return veiculo;
    }
}