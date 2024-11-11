package model;

public class VagaPCD implements ITipoVaga {
    @Override
    public String getTipo() {
        return "PCD";
    }

    @Override
    public double calcularTarifa(double tarifaBase) {
        return tarifaBase * 0.87; // 13% de desconto
    }
}