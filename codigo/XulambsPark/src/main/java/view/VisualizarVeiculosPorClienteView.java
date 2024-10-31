package view;

import model.Veiculo;

import javax.swing.*;
import java.util.List;

public class VisualizarVeiculosPorClienteView {

    public void mostrarVeiculos(List<Veiculo> veiculos, String cpfCliente) {
        StringBuilder veiculosDoCliente = new StringBuilder("Veículos do cliente (CPF: " + cpfCliente + "):\n");
        if (veiculos.isEmpty()) {
            veiculosDoCliente.append("Nenhum veículo encontrado para este cliente.");
        } else {
            for (Veiculo veiculo : veiculos) {
                veiculosDoCliente.append("Placa: ").append(veiculo.getPlaca())
                        .append(", Modelo: ").append(veiculo.getModelo())
                        .append(", Cor: ").append(veiculo.getCor())
                        .append(", Tipo: ").append(veiculo.getTipo()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, veiculosDoCliente.toString());
    }
}