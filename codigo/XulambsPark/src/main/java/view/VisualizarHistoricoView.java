package view;

import model.SistemaEstacionamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VisualizarHistoricoView {
    private SistemaEstacionamento sistemaEstacionamento;

    public VisualizarHistoricoView(SistemaEstacionamento sistemaEstacionamento) {
        this.sistemaEstacionamento = sistemaEstacionamento;
    }

    public void mostrarHistorico(String cpfCliente) {
        mostrarHistorico(cpfCliente, null, null);
    }

    public void mostrarHistorico(String cpfCliente, Date dataInicio, Date dataFim) {
        // Obter o histórico do cliente
        List<String> historico = sistemaEstacionamento.obterHistoricoPorCliente(cpfCliente);

        // Filtrar o histórico se as datas forem fornecidas
        if (dataInicio != null && dataFim != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            historico = historico.stream()
                    .filter(h -> {
                        try {
                            String[] parts = h.split(",");
                            Date dataEntrada = dateFormat.parse(parts[3]);
                            Date dataSaida = dateFormat.parse(parts[4]);
                            return !dataEntrada.before(dataInicio) && !dataSaida.after(dataFim);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
        }

        // Criar a interface gráfica para exibir o histórico
        JFrame frame = new JFrame("Histórico do Cliente");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        String[] columnNames = {"CPF", "Modelo", "Placa", "Data Entrada", "Data Saída", "Duração", "Valor"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (String h : historico) {
            String[] data = h.split(",");
            tableModel.addRow(data);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setVisible(true);
    }
}