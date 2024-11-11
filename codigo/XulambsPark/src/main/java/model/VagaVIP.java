package model;

public class VagaVIP implements ITipoVaga {
    @Override
    public String getTipo() {
        return "VIP";
    }

    @Override
    public double calcularTarifa(double tarifaBase) {
        return tarifaBase * 1.20; // 20% mais caro
    }
}