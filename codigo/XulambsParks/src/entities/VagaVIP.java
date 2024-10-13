package entities;

public class VagaVIP extends Vaga {

    @Override
    public double calcularDescontoOuAcrescimo(double valorBase) {
        return valorBase * 1.20; 
    }

    @Override
    public String getTipo() {
        return "VIP";
    }
}
