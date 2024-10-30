package view;

import javax.swing.*;

public class VisualizarVeiculosEstacionadosView {
    public static String escolherParque() {
        String[] parques = {"Parque A", "Parque B", "Parque C"};
        return (String) JOptionPane.showInputDialog(null, "Escolha um parque de estacionamento:", "Visualizar Veículos Estacionados", JOptionPane.QUESTION_MESSAGE, null, parques, parques[0]);
    }
}