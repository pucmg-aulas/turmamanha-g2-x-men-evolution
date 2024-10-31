package view;

import javax.swing.*;
import java.awt.*;

public class ResultadoView extends JFrame {
    public ResultadoView(String titulo, String resultado) {
        setTitle(titulo);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(resultado);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);
    }
}