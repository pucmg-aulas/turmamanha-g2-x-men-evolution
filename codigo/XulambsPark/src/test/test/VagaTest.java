package test;

import model.Vaga;
import model.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class VagaTest {

    private Vaga vaga;
    private Veiculo veiculo;
    private LocalDateTime entrada;

    @BeforeEach
    public void setUp() {
        vaga = new Vaga(1) {};
        veiculo = new Veiculo("ABC1234", "Fusca", "Azul", "Carro", "12345678900");
        entrada = LocalDateTime.now();
    }

    @Test
    public void testVagaConstructor() {
        assertEquals(1, vaga.getNumero());
        assertFalse(vaga.isOcupada());
        assertNull(vaga.getVeiculo());
        assertNull(vaga.getEntrada());
    }

    @Test
    public void testOcuparVaga() {
        vaga.ocupar(veiculo, entrada);
        assertTrue(vaga.isOcupada());
        assertEquals(veiculo, vaga.getVeiculo());
        assertEquals(entrada, vaga.getEntrada());
    }

    @Test
    public void testLiberarVaga() {
        vaga.ocupar(veiculo, entrada);
        vaga.liberar();
        assertFalse(vaga.isOcupada());
        assertNull(vaga.getVeiculo());
        assertNull(vaga.getEntrada());
    }

    @Test
    public void testOcuparVagaTwice() {
        vaga.ocupar(veiculo, entrada);
        Veiculo outroVeiculo = new Veiculo("XYZ9876", "Gol", "Preto", "Carro", "12345678901");
        vaga.liberar();
        vaga.ocupar(outroVeiculo, entrada.plusHours(1));
        assertEquals(outroVeiculo, vaga.getVeiculo());
    }
}
