package test;

import model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Cliente cliente;
    private Map<String, Cliente> clientes;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente("João Silva", "12345678900");
        clientes = new HashMap<>();
    }

    @Test
    public void testClienteConstructor() {
        assertEquals("João Silva", cliente.getNome());
        assertEquals("12345678900", cliente.getCpf());
    }

    @Test
    public void testCadastrarCliente_Success() {
        clientes.put(cliente.getCpf(), cliente);
        assertTrue(clientes.containsKey("12345678900"));
        assertEquals(cliente, clientes.get("12345678900"));
    }

    @Test
    public void testCadastrarCliente_Failure() {
        Cliente clienteVazio = new Cliente("", "");
        clientes.put(clienteVazio.getCpf(), clienteVazio);
        assertNotNull(clientes.get(""));
        assertEquals("", clienteVazio.getNome());
    }
}