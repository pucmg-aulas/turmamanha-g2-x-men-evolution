package test;

import model.Veiculo;
import model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VeiculoTest {

    private Veiculo veiculo;
    private Map<String, Cliente> clientes;
    private Map<String, Veiculo> veiculos;

    @BeforeEach
    public void setUp() {
        clientes = new HashMap<>();
        veiculos = new HashMap<>();
        Cliente cliente = new Cliente("João Silva", "12345678900");
        clientes.put(cliente.getCpf(), cliente);
        veiculo = new Veiculo("ABC1234", "Fusca", "Azul", "Carro", "12345678900");
    }

    @Test
    public void testVeiculoConstructor() {
        assertEquals("ABC1234", veiculo.getPlaca());
        assertEquals("Fusca", veiculo.getModelo());
        assertEquals("Azul", veiculo.getCor());
        assertEquals("Carro", veiculo.getTipo());
        assertEquals("12345678900", veiculo.getCpfCliente());
    }

    @Test
    public void testCadastrarVeiculo_Success() {
        veiculos.put(veiculo.getPlaca(), veiculo);
        assertTrue(veiculos.containsKey("ABC1234"));
        assertEquals(veiculo, veiculos.get("ABC1234"));
    }

    @Test
    public void testCadastrarVeiculo_ClienteNaoEncontrado() {
        Veiculo veiculoNaoAssociado = new Veiculo("XYZ9876", "Gol", "Preto", "Carro", "99999999999");
        assertFalse(clientes.containsKey(veiculoNaoAssociado.getCpfCliente()));
    }

    @Test
    public void testCadastrarVeiculo_Failure() {
        Veiculo veiculoVazio = new Veiculo("", "", "", "", "");
        assertEquals("", veiculoVazio.getPlaca());
        assertEquals("", veiculoVazio.getModelo());
        assertEquals("", veiculoVazio.getCor());
        assertEquals("", veiculoVazio.getTipo());
        assertEquals("", veiculoVazio.getCpfCliente());
    }
}
