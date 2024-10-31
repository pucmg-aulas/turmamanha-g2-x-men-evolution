package test;

import app.XulambsPark;
import model.Cliente;
import model.SistemaEstacionamento;
import model.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class XulambsParkTest {
    private Map<String, Veiculo> veiculos;
    private Map<String, Cliente> clientes;
    private SistemaEstacionamento sistemaEstacionamento;

    @BeforeEach
    public void setUp() {
        veiculos = new HashMap<>();
        clientes = new HashMap<>();
        sistemaEstacionamento = new SistemaEstacionamento(veiculos);

        // Criar arquivos de teste com dados para garantir que os testes de carregar funcionem
        try {
            File clientesFile = new File("datatest/clientes.txt");
            clientesFile.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(clientesFile)) {
                writer.write("12345678900,João Silva\n");
            }

            File veiculosFile = new File("datatest/veiculos.txt");
            try (FileWriter writer = new FileWriter(veiculosFile)) {
                writer.write("ABC-1234,12345678900,Carro,Preto,Normal\n");
            }

            File historicoFile = new File("datatest/historico.txt");
            try (FileWriter writer = new FileWriter(historicoFile)) {
                writer.write("12345678900,Carro,ABC-1234,01/01/2024 10:00,01/01/2024 11:00,60,20.0\n");
            }
        } catch (IOException e) {
            fail("Erro ao configurar arquivos de teste: " + e.getMessage());
        }
    }

    @Test
    public void testCarregarClientes() {
        try {
            sistemaEstacionamento.carregarClientes(clientes, "datatest/clientes.txt");
            assertFalse(clientes.isEmpty(), "Clientes devem ser carregados do arquivo");
            assertTrue(clientes.containsKey("12345678900"), "O cliente com CPF 12345678900 deve ser carregado");
        } catch (IOException e) {
            fail("Exceção ao carregar clientes: " + e.getMessage());
        }
    }

    @Test
    public void testCarregarVeiculos() {
        try {
            sistemaEstacionamento.carregarVeiculos(veiculos, "datatest/veiculos.txt");
            assertFalse(veiculos.isEmpty(), "Veículos devem ser carregados do arquivo");
            assertTrue(veiculos.containsKey("ABC-1234"), "O veículo com placa ABC-1234 deve ser carregado");
        } catch (IOException e) {
            fail("Exceção ao carregar veículos: " + e.getMessage());
        }
    }

    @Test
    public void testSalvarClientes() {
        try {
            Cliente cliente = new Cliente("João Silva", "12345678900");
            clientes.put(cliente.getCpf(), cliente);
            sistemaEstacionamento.salvarClientes(clientes, "datatest/clientes_test.txt");
            File file = new File("datatest/clientes_test.txt");
            assertTrue(file.exists() && file.length() > 0, "O arquivo de clientes deve ser salvo corretamente");
        } catch (IOException e) {
            fail("Exceção ao salvar clientes: " + e.getMessage());
        }
    }

    @Test
    public void testSalvarVeiculos() {
        try {
            Veiculo veiculo = new Veiculo("ABC-1234", "Carro", "Preto", "Normal", "12345678900");
            veiculos.put(veiculo.getPlaca(), veiculo);
            sistemaEstacionamento.salvarVeiculos(veiculos, "datatest/veiculos_test.txt");
            File file = new File("datatest/veiculos_test.txt");
            assertTrue(file.exists() && file.length() > 0, "O arquivo de veículos deve ser salvo corretamente");
        } catch (IOException e) {
            fail("Exceção ao salvar veículos: " + e.getMessage());
        }
    }

    @Test
    public void testCarregarHistorico() {
        try {
            sistemaEstacionamento.carregarHistorico("datatest/historico.txt");
            assertNotNull(sistemaEstacionamento.obterHistoricoPorCliente("12345678900"), "O histórico deve ser carregado corretamente");
            assertFalse(sistemaEstacionamento.obterHistoricoPorCliente("12345678900").isEmpty(), "O histórico do cliente não deve estar vazio");
        } catch (IOException e) {
            fail("Exceção ao carregar histórico: " + e.getMessage());
        }
    }
}
