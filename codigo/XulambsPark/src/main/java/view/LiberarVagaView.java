package view;

import javax.swing.*;

public class LiberarVagaView {
    public static String escolherParque() {
        String[] parques = {"Parque A", "Parque B", "Parque C"};
        return (String) JOptionPane.showInputDialog(null, "Escolha um parque de estacionamento:", "Liberar Vaga", JOptionPane.QUESTION_MESSAGE, null, parques, parques[0]);
    }
}
