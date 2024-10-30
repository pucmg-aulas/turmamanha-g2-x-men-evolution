package controller;

import model.SistemaEstacionamento;
import view.AcessarComoAdminView;

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
                        JOptionPane.showMessageDialog(null, "Valor total arrecadado: R$ " + String.format("%.2f", valorTotal));
                        break;
                    case "Valor Arrecadado em Determinado Mês":
                        String mesAno = JOptionPane.showInputDialog("Digite o mês e o ano (MM/yyyy):");
                        double valorMes = sistemaEstacionamento.calcularValorArrecadadoMes(mesAno);
                        JOptionPane.showMessageDialog(null, "Valor arrecadado em " + mesAno + ": R$ " + String.format("%.2f", valorMes));
                        break;
                    case "Valor Médio por Utilização":
                        double valorMedio = sistemaEstacionamento.calcularValorMedioUtilizacao();
                        JOptionPane.showMessageDialog(null, "Valor médio por utilização: R$ " + String.format("%.2f", valorMedio));
                        break;
                    case "Ranking de Clientes por Arrecadação":
                        String mesAnoRanking = JOptionPane.showInputDialog("Digite o mês e o ano (MM/yyyy):");
                        String ranking = sistemaEstacionamento.gerarRankingClientesPorArrecadacao(mesAnoRanking);
                        JOptionPane.showMessageDialog(null, ranking);
                        break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Senha incorreta!");
        }
    }
}