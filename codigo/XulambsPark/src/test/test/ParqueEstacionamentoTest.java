package test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParqueEstacionamentoTest {
    private ParqueEstacionamento parque;

    @BeforeEach
    public void setUp() {
        parque = new ParqueEstacionamento("Parque A");
    }

    @Test
    public void testGetNome() {
        assertEquals("Parque A", parque.getNome(), "O nome do parque deve ser 'Parque A'");
    }

    @Test
    public void testVagasInicializacao() {
        List<Vaga> vagas = parque.getVagas();
        assertEquals(10, vagas.size(), "O parque deve ter 10 vagas");
        assertTrue(vagas.get(0) instanceof VagaPCD, "As primeiras 2 vagas devem ser do tipo PCD");
        assertTrue(vagas.get(1) instanceof VagaPCD, "As primeiras 2 vagas devem ser do tipo PCD");
        assertTrue(vagas.get(2) instanceof VagaIdoso, "As próximas 2 vagas devem ser do tipo Idoso");
        assertTrue(vagas.get(3) instanceof VagaIdoso, "As próximas 2 vagas devem ser do tipo Idoso");
        assertTrue(vagas.get(4) instanceof VagaVIP, "As próximas 2 vagas devem ser do tipo VIP");
        assertTrue(vagas.get(5) instanceof VagaVIP, "As próximas 2 vagas devem ser do tipo VIP");
        assertTrue(vagas.get(6) instanceof VagaNormal, "As últimas 4 vagas devem ser do tipo Normal");
        assertTrue(vagas.get(9) instanceof VagaNormal, "As últimas 4 vagas devem ser do tipo Normal");
    }


}
