package model;

public class VagaRegular implements ITipoVaga {
    @Override
    public String getTipo() {
        return "Regular";
    }

    @Override
    public double calcularTarifa(double tarifaBase) {
        return tarifaBase;
    }
}