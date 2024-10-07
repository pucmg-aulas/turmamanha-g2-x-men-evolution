package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Cobrança implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDateTime horarioEntrada;
    private LocalDateTime horarioSaida;
    private double valorCobrado;

    public Cobrança(LocalDateTime horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
        this.horarioSaida = null;
        this.valorCobrado = 0.0;
    }


    public void setHorarioSaida(LocalDateTime horarioSaida) {
        this.horarioSaida = horarioSaida;
        calcularCobrança();
    }


    public void registrarSaida(LocalDateTime horarioSaida) {
        setHorarioSaida(horarioSaida);
    }

    public double getValorCobrado() {
        return valorCobrado;
    }

    private void calcularCobrança() {
        if (horarioSaida != null) {
            long horas = ChronoUnit.HOURS.between(horarioEntrada, horarioSaida);
            if (horas < 1) {
                horas = 1;
            }
            valorCobrado = horas * 5.0;
        }
    }
}
