package controller;

import model.Cliente;
import model.Veiculo;
import view.CadastrarVeiculoView;

import javax.swing.*;
import java.util.Map;

public class CadastrarVeiculoController {
    private Map<String, Cliente> clientes;
    private Map<String, Veiculo> veiculos;

    public CadastrarVeiculoController(Map<String, Cliente> clientes, Map<String, Veiculo> veiculos) {
        this.clientes = clientes;
        this.veiculos = veiculos;
    }

    public void cadastrarVeiculo() {
        String[] dados = CadastrarVeiculoView.mostrarFormulario();
        if (dados != null) {
            String placa = dados[0];
            String modelo = dados[1];
            String cor = dados[2];
            String tipo = dados[3];
            String cpf = dados[4];

            if (!placa.isEmpty() && !modelo.isEmpty() && !cor.isEmpty() && !cpf.isEmpty()) {
                if (clientes.containsKey(cpf)) {
                    veiculos.put(placa, new Veiculo(placa, modelo, cor, tipo, cpf));
                    JOptionPane.showMessageDialog(null, "Veículo cadastrado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente não encontrado. Cadastre o cliente primeiro.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
            }
        }
    }
}