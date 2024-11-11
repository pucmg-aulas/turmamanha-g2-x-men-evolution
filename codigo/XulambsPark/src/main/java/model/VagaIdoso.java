package model;

public class VagaIdoso implements ITipoVaga {
    @Override
    public String getTipo() {
        return "Idoso";
    }

    @Override
    public double calcularTarifa(double tarifaBase) {
        return tarifaBase * 0.85; // 15% de desconto
    }
}