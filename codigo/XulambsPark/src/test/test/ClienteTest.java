package test;

import model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {
    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente("João Silva", "12345678900");
    }

    @Test
    public void testGetNome() {
        assertEquals("João Silva", cliente.getNome(), "O nome do cliente deve ser 'João Silva'");
    }

    @Test
    public void testGetCpf() {
        assertEquals("12345678900", cliente.getCpf(), "O CPF do cliente deve ser '12345678900'");
    }

    @Test
    public void testClienteCreation() {
        Cliente novoCliente = new Cliente("Maria Souza", "09876543211");
        assertNotNull(novoCliente, "O cliente não deve ser nulo após a criação");
        assertEquals("Maria Souza", novoCliente.getNome(), "O nome do cliente deve ser 'Maria Souza'");
        assertEquals("09876543211", novoCliente.getCpf(), "O CPF do cliente deve ser '09876543211'");
    }
}
