package controller;

import model.Cliente;
import model.Veiculo;
import java.util.Map;

public class CadastrarVeiculoController {
    private Map<String, Veiculo> veiculos;
    private Map<String, Cliente> clientes;

    public CadastrarVeiculoController(Map<String, Cliente> clientes, Map<String, Veiculo> veiculos) {
        this.clientes = clientes;
        this.veiculos = veiculos;
    }

    public void cadastrarVeiculo(String placa, String cpfCliente, String modelo, String cor, String tipo) {
        Veiculo veiculo = new Veiculo(placa, cpfCliente, modelo, cor, tipo);
        veiculos.put(placa, veiculo);
    }
}