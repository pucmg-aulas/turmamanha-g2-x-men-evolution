package view;

import javax.swing.*;

public class AcessarComoAdminView {
    public static String solicitarSenhaAdmin() {
        return JOptionPane.showInputDialog("Digite a senha do administrador:");
    }

    public static String mostrarOpcoesAdmin() {
        String[] opcoes = {"Valor Total Arrecadado", "Valor Arrecadado em Determinado Mês", "Valor Médio por Utilização", "Ranking de Clientes por Arrecadação"};
        return (String) JOptionPane.showInputDialog(null, "Escolha uma opção:", "Administrador", JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
    }
}