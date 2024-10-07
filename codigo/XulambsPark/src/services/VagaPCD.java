package entities;

public class VagaPCD extends Vaga {

    public VagaPCD(String codigo) {
        super(codigo);
    }

    @Override
    public String getTipo() {
        return "PCD";
    }
}
