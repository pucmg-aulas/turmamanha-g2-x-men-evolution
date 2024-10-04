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
//idoso: 15% pcd :13% vip:20% mais caro

    // Classe VagaVip
    public class VagaVip extends Vaga {

        // Construtor da classe VagaVip 
        public VagaVip(String codigoDaVaga) {
            super(codigoDaVaga);
        }


        public void IncrementarTarifaVIP(Vaga vaga) {

            System.out.println("Tarifa VIP acrescida em 20% para a vaga: " + vaga.getCodigoDaVaga());
        }
    }

    // Classe VagaIdoso
    public class VagaIdoso extends Vaga {

        // Construtor da classe VagaIdoso
        public VagaIdoso(String codigoDaVaga) {
            super(codigoDaVaga);
        }


        public void IncrementarDescontoIdoso(Vaga vaga) {

            System.out.println("Desconto de 15% aplicado para a vaga: " + vaga.getCodigoDaVaga());
        }
    }

    // Classe VagaPCD
    public class VagaPCD extends Vaga {


        public VagaPCD(String codigoDaVaga) {
            super(codigoDaVaga);
        }


        public void IncrementarDescontoPCD(Vaga vaga) {

            System.out.println("Desconto de 13% aplicado para a vaga: " + vaga.getCodigoDaVaga());
        }
    }



}
