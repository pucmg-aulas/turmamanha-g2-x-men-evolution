package controller;

import model.SistemaEstacionamento;
import view.AcessarComoAdminView;
import view.ResultadoView;

import javax.swing.*;

public class AcessarComoAdminController {
    private SistemaEstacionamento sistemaEstacionamento;

    public AcessarComoAdminController(SistemaEstacionamento sistemaEstacionamento) {
        this.sistemaEstacionamento = sistemaEstacionamento;
    }

    public void acessarComoAdministrador() {
        String senha = AcessarComoAdminView.solicitarSenhaAdmin();
        if ("admin123".equals(senha)) {
            String escolha = AcessarComoAdminView.mostrarOpcoesAdmin();
            if (escolha != null) {
                switch (escolha) {
                    case "Valor Total Arrecadado":
                        double valorTotal = sistemaEstacionamento.calcularValorTotalArrecadado();
                        new ResultadoView("Valor Total Arrecadado", "Valor total arrecadado: R$ " + String.format("%.2f", valorTotal)).setVisible(true);
                        break;
                    case "Valor Arrecadado em Determinado Mês":
                        String mesAno = JOptionPane.showInputDialog("Digite o mês e o ano (MM/yyyy):");
                        double valorMes = sistemaEstacionamento.calcularValorArrecadadoMes(mesAno);
                        new ResultadoView("Valor Arrecadado em " + mesAno, "Valor arrecadado em " + mesAno + ": R$ " + String.format("%.2f", valorMes)).setVisible(true);
                        break;
                    case "Valor Médio por Utilização":
                        double valorMedio = sistemaEstacionamento.calcularValorMedioUtilizacao();
                        new ResultadoView("Valor Médio por Utilização", "Valor médio por utilização: R$ " + String.format("%.2f", valorMedio)).setVisible(true);
                        break;
                    case "Ranking de Clientes por Arrecadação":
                        String mesAnoRanking = JOptionPane.showInputDialog("Digite o mês e o ano (MM/yyyy):");
                        String ranking = sistemaEstacionamento.gerarRankingClientesPorArrecadacao(mesAnoRanking);
                        new ResultadoView("Ranking de Clientes por Arrecadação", ranking).setVisible(true);
                        break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Senha incorreta!");
        }
    }
}