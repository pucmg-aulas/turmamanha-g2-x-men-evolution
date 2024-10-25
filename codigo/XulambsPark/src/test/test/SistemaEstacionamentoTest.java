package test;

import model.SistemaEstacionamento;
import model.Cliente;
import model.Veiculo;
import model.ParqueEstacionamento;
import model.Vaga;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SistemaEstacionamentoTest {

    private SistemaEstacionamento sistemaEstacionamento;
    private Map<String, Cliente> clientes;
    private Map<String, Veiculo> veiculos;
    private ParqueEstacionamento parque;

    @BeforeEach
    public void setUp() {
        sistemaEstacionamento = new SistemaEstacionamento();
        clientes = new HashMap<>();
        veiculos = new HashMap<>();
        parque = new ParqueEstacionamento("Parque A");
        clientes.put("12345678900", new Cliente("João Silva", "12345678900"));
        veiculos.put("ABC1234", new Veiculo("ABC1234", "Fusca", "Azul", "Carro", "12345678900"));
    }

    @Test
    public void testSalvarClientes() throws IOException {
        String filePath = "test_clientes.txt";
        sistemaEstacionamento.salvarClientes(clientes, filePath);
        // Verifica se o arquivo foi salvo corretamente
        assertTrue(new java.io.File(filePath).exists());
    }

    @Test
    public void testCarregarClientes() throws IOException {
        String filePath = "test_clientes.txt";
        sistemaEstacionamento.salvarClientes(clientes, filePath);
        Map<String, Cliente> loadedClientes = new HashMap<>();
        sistemaEstacionamento.carregarClientes(loadedClientes, filePath);
        assertEquals(clientes.size(), loadedClientes.size());
        assertTrue(loadedClientes.containsKey("12345678900"));
    }

    @Test
    public void testSalvarVeiculos() throws IOException {
        String filePath = "test_veiculos.txt";
        sistemaEstacionamento.salvarVeiculos(veiculos, filePath);
        // Verifica se o arquivo foi salvo corretamente
        assertTrue(new java.io.File(filePath).exists());
    }

    @Test
    public void testCarregarVeiculos() throws IOException {
        String filePath = "test_veiculos.txt";
        sistemaEstacionamento.salvarVeiculos(veiculos, filePath);
        Map<String, Veiculo> loadedVeiculos = new HashMap<>();
        sistemaEstacionamento.carregarVeiculos(loadedVeiculos, filePath);
        assertEquals(veiculos.size(), loadedVeiculos.size());
        assertTrue(loadedVeiculos.containsKey("ABC1234"));
    }

    @Test
    public void testSalvarVagas() throws IOException {
        String filePath = "test_vagas.txt";
        Vaga vaga = parque.getVagas().get(0);
        vaga.ocupar(veiculos.get("ABC1234"), LocalDateTime.now());
        sistemaEstacionamento.salvarVagas(parque, filePath);
        // Verifica se o arquivo foi salvo corretamente
        assertTrue(new java.io.File(filePath).exists());
    }

    @Test
    public void testCarregarVagas() throws IOException {
        String filePath = "test_vagas.txt";
        Vaga vaga = parque.getVagas().get(0);
        vaga.ocupar(veiculos.get("ABC1234"), LocalDateTime.now());
        sistemaEstacionamento.salvarVagas(parque, filePath);
        sistemaEstacionamento.carregarVagas(parque, veiculos, filePath);
        assertTrue(parque.getVagas().get(0).isOcupada());
        assertEquals("ABC1234", parque.getVagas().get(0).getVeiculo().getPlaca());
    }
}
