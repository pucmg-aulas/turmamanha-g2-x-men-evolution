import java.time.LocalDate;
import java.time.LocalDateTime;

public class Vaga {
    //atributos
    private String codigoDaVaga;

    //construtor da classe pai
    public Vaga(String codigoDaVaga) {
        super();
        this.codigoDaVaga = codigoDaVaga;
    }

    //get set
    public String getCodigoDaVaga() {
        return codigoDaVaga;
    }

    public void setCodigoDaVaga(String codigoDaVaga) {
        this.codigoDaVaga = codigoDaVaga;
    }

    //metodos

    public boolean verificarVaga(Vaga[] vagas, Vaga vaga) {
        for (Vaga v : vagas) {
            if (v.getCodigoDaVaga().equals(vaga.getCodigoDaVaga())) {
                return true;
            }
        }
        return false;
    }
    public String getCodigoDaVaga(String codigoDaVaga) {
        return this.codigoDaVaga.equals(codigoDaVaga) ? this.codigoDaVaga : null;
    }


}
