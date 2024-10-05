package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Cobrança implements Serializable {
	
    private LocalDateTime horarioEntrada;
    private LocalDateTime horarioSaida;

    public Cobrança(LocalDateTime entrada, LocalDateTime saida) {
        this.horarioEntrada = entrada;
        this.horarioSaida = saida;
    }

    public LocalDateTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
    }

	public void setHorarioEntrada(LocalDateTime horarioEntrada) {
		this.horarioEntrada = horarioEntrada;
	}

	public void setHorarioSaida(LocalDateTime horarioSaida) {
		this.horarioSaida = horarioSaida;
	}
	
	public double calcularPreco() {
        long minutos = ChronoUnit.MINUTES.between(horarioEntrada, horarioSaida);
        double total = (minutos / 15) * 4;
        return Math.min(total, 50); 
    }
	
}
