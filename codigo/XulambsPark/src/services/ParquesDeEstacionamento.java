public class Vaga {
    private String idVaga;


    public Vaga(String idVaga) {
        this.idVaga = idVaga;
    }


    public String getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(String idVaga) {
        this.idVaga = idVaga;
    }
}


import java.util.ArrayList;

public class ParquesDeEstacionamento {
    private String codigoDoParque;
    private ArrayList<Vaga> vagas;


    public ParquesDeEstacionamento(String codigoDoParque) {
        this.codigoDoParque = codigoDoParque;
        this.vagas = new ArrayList<>();
    }


    public void criarVaga(Vaga vaga) {
        vagas.add(vaga);
    }

    public void atualizarVaga(Vaga vaga) {
        for (int i = 0; i < vagas.size(); i++) {
            if (vagas.get(i).getIdVaga().equals(vaga.getIdVaga())) {
                vagas.set(i, vaga);
                break;
            }
        }
    }


    public String getCodigoDoParque() {
        return codigoDoParque;
    }

    public void setCodigoDoParque(String codigoDoParque) {
        this.codigoDoParque = codigoDoParque;
    }

    public ArrayList<Vaga> getVagas() {
        return vagas;
    }
}

import java.util.ArrayList;

public class SistemaDeEstacionamento {
    private ArrayList<ParquesDeEstacionamento> parques;


    public SistemaDeEstacionamento() {
        parques = new ArrayList<>();
    }


    public void cadastrarVaga(Veiculo veiculo, Cliente cliente) {

    }

    public void liberarVaga(Veiculo veiculo, Cliente cliente) {

    }

    public void consultarParques(ArrayList<ParquesDeEstacionamento> parques) {

    }

    public void listarVagas(Vaga vaga, ParquesDeEstacionamento parque) {

    }

    public void getVeiculos(Veiculo veiculo) {

    }

    public ArrayList<Vaga> getVagas(Vaga vaga) {

        return null;
    }
}


// testes Unitario

//teste 1

//Parques de Estacionamento
import org.junit.Test;
import static org.junit.Assert.*;
        import java.util.ArrayList;

public class ParquesDeEstacionamentoTest {

    @Test
    public void testCriarVaga() {
        ParquesDeEstacionamento parque = new ParquesDeEstacionamento("P001");
        Vaga vaga = new Vaga("V001");


        parque.criarVaga(vaga);


        assertEquals(1, parque.getVagas().size());
        assertEquals("V001", parque.getVagas().get(0).getIdVaga());
    }
}

//teste 2

//cadastrar vaga
public class SistemaDeEstacionamento {
    private ArrayList<ParquesDeEstacionamento> parques;

    public SistemaDeEstacionamento() {
        parques = new ArrayList<>();
    }


    public void cadastrarVaga(ParquesDeEstacionamento parque, Vaga vaga) {
        parque.criarVaga(vaga);
        parques.add(parque);
    }

    public ArrayList<ParquesDeEstacionamento> getParques() {
        return parques;
    }
}

// retornar esse :

import org.junit.Test;
import static org.junit.Assert.*;

public class SistemaDeEstacionamentoTest {

    @Test
    public void testCadastrarVaga() {
        SistemaDeEstacionamento sistema = new SistemaDeEstacionamento();
        ParquesDeEstacionamento parque = new ParquesDeEstacionamento("P002");
        Vaga vaga = new Vaga("V002");


        sistema.cadastrarVaga(parque, vaga);


        assertEquals(1, sistema.getParques().size());


        assertEquals(1, sistema.getParques().get(0).getVagas().size());
        assertEquals("V002", sistema.getParques().get(0).getVagas().get(0).getIdVaga());
    }
}

// para executar o teste coloque na bash "mavem": mvn test
