package entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Cobranca implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDateTime horarioEntrada;
    private LocalDateTime horarioSaida;
    private double valorCobrado;

    public Cobranca(LocalDateTime horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public void registrarSaida(LocalDateTime horarioSaida) {
        this.horarioSaida = horarioSaida;
        calcularValorCobrado();
    }

    public double getValorCobrado() {
        return valorCobrado;
    }

    private void calcularValorCobrado() {
        
        long minutosEstacionados = java.time.Duration.between(horarioEntrada, horarioSaida).toMinutes();
        valorCobrado = minutosEstacionados * 0.5; // R$0,50 por minuto
    }

    public LocalDateTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
    }
}
