package entities;

public class VagaVIP extends Vaga {

    public VagaVIP(String codigo) {
        super(codigo);
    }

    @Override
    public String getTipo() {
        return "VIP";
    }
}
