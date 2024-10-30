package controller;

import model.Cliente;
import view.CadastrarClienteView;

import javax.swing.*;
import java.util.Map;

public class CadastrarClienteController {
    private Map<String, Cliente> clientes;

    public CadastrarClienteController(Map<String, Cliente> clientes) {
        this.clientes = clientes;
    }

    public void cadastrarCliente() {
        String[] dados = CadastrarClienteView.mostrarFormulario();
        if (dados != null) {
            String nome = dados[0];
            String cpf = dados[1];
            if (!nome.isEmpty() && !cpf.isEmpty()) {
                clientes.put(cpf, new Cliente(nome, cpf));
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
            }
        }
    }
}