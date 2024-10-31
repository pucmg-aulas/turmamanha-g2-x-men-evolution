package controller;

import model.Cliente;
import java.util.Map;

public class CadastrarClienteController {
    private Map<String, Cliente> clientes;

    public CadastrarClienteController(Map<String, Cliente> clientes) {
        this.clientes = clientes;
    }

    public void cadastrarCliente(String cpf, String nome) {
        Cliente cliente = new Cliente(cpf, nome);
        clientes.put(cpf, cliente);
    }
}