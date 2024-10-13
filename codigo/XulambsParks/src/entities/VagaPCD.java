package entities;

public class VagaPCD extends Vaga {

    @Override
    public double calcularDescontoOuAcrescimo(double valorBase) {
        return valorBase * 0.87; 
    }

    @Override
    public String getTipo() {
        return "PCD";
    }
}
