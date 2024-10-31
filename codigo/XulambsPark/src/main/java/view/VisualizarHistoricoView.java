// VisualizarHistoricoView.java
package view;

import model.SistemaEstacionamento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VisualizarHistoricoView {
    private SistemaEstacionamento sistemaEstacionamento;

    public VisualizarHistoricoView(SistemaEstacionamento sistemaEstacionamento) {
        this.sistemaEstacionamento = sistemaEstacionamento;
    }

    public void mostrarHistorico(String cpfCliente) {
        List<String> historico = sistemaEstacionamento.obterHistoricoPorCliente(cpfCliente);
        JFrame frame = new JFrame("Histórico do Cliente");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        for (String registro : historico) {
            textArea.append(registro + "\n");
        }
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}