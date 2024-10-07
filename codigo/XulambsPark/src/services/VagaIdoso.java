package entities;

public class VagaIdoso extends Vaga {

    public VagaIdoso(String codigo) {
        super(codigo);
    }

    @Override
    public String getTipo() {
        return "Idoso";
    }
}
