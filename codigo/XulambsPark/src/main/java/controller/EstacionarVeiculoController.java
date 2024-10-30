package controller;

import model.SistemaEstacionamento;
import model.Cliente;
import model.Veiculo;

import javax.swing.*;
import java.util.Map;

public class EstacionarVeiculoController {
    private Map<String, Cliente> clientes;
    private Map<String, Veiculo> veiculos;
    private SistemaEstacionamento sistemaEstacionamento;

    public EstacionarVeiculoController(Map<String, Cliente> clientes, Map<String, Veiculo> veiculos, SistemaEstacionamento sistemaEstacionamento) {
        this.clientes = clientes;
        this.veiculos = veiculos;
        this.sistemaEstacionamento = sistemaEstacionamento;
    }

    public void estacionarVeiculo() {
        sistemaEstacionamento.estacionarVeiculo(clientes, veiculos);
    }
}