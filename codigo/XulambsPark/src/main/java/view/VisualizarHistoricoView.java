package view;

import javax.swing.*;

public class VisualizarHistoricoView {
    public static String solicitarCpfCliente() {
        return JOptionPane.showInputDialog("Digite o CPF do cliente para visualizar o histórico:");
    }
}
