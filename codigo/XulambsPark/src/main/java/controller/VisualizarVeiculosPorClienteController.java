package controller;

import model.Veiculo;
import view.VisualizarVeiculosPorClienteView;

import javax.swing.*;
import java.util.Map;

public class VisualizarVeiculosPorClienteController {
    private Map<String, Veiculo> veiculos;

    public VisualizarVeiculosPorClienteController(Map<String, Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public void visualizarVeiculosPorCliente() {
        String cpfCliente = VisualizarVeiculosPorClienteView.solicitarCpfCliente();
        if (cpfCliente != null && !cpfCliente.isEmpty()) {
            StringBuilder veiculosDoCliente = new StringBuilder("Veículos do cliente (CPF: " + cpfCliente + "):\n");
            boolean encontrouVeiculo = false;
            for (Veiculo veiculo : veiculos.values()) {
                if (veiculo.getCpfCliente().equals(cpfCliente)) {
                    veiculosDoCliente.append("Placa: ").append(veiculo.getPlaca())
                            .append(", Modelo: ").append(veiculo.getModelo())
                            .append(", Cor: ").append(veiculo.getCor())
                            .append(", Tipo: ").append(veiculo.getTipo()).append("\n");
                    encontrouVeiculo = true;
                }
            }
            if (!encontrouVeiculo) {
                veiculosDoCliente.append("Nenhum veículo encontrado para este cliente.");
            }
            JOptionPane.showMessageDialog(null, veiculosDoCliente.toString());
        }
    }
}
