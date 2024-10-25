package test;

import model.ParqueEstacionamento;
import model.Vaga;
import model.VagaPCD;
import model.VagaIdoso;
import model.VagaVIP;
import model.VagaNormal;
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
    public void testParqueEstacionamentoConstructor() {
        assertEquals(10, parque.getVagas().size());
    }

    @Test
    public void testVagasTipos() {
        List<Vaga> vagas = parque.getVagas();
        assertTrue(vagas.get(0) instanceof VagaPCD);
        assertTrue(vagas.get(1) instanceof VagaPCD);
        assertTrue(vagas.get(2) instanceof VagaIdoso);
        assertTrue(vagas.get(3) instanceof VagaIdoso);
        assertTrue(vagas.get(4) instanceof VagaVIP);
        assertTrue(vagas.get(5) instanceof VagaVIP);
        assertTrue(vagas.get(6) instanceof VagaNormal);
        assertTrue(vagas.get(7) instanceof VagaNormal);
        assertTrue(vagas.get(8) instanceof VagaNormal);
        assertTrue(vagas.get(9) instanceof VagaNormal);
    }
}
