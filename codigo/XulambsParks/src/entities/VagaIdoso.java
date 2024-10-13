package entities;

public class VagaIdoso extends Vaga {

    @Override
    public double calcularDescontoOuAcrescimo(double valorBase) {
        return valorBase * 0.85; 
    }

    @Override
    public String getTipo() {
        return "Idoso";
    }
}
