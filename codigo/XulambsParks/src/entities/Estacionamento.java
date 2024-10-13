package entities;

import java.time.Duration;
import java.time.LocalDateTime;

public class Estacionamento {
    private static final double TARIFA_PADRAO = 5.0; // R$5 a cada 15 minutos
    private static final double MAXIMO_PAGAMENTO = 50.0; // Valor máximo a ser pago

    public double calcularValor(LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida, String tipoVaga) {
        long minutos = Duration.between(dataHoraEntrada, dataHoraSaida).toMinutes();

        if (minutos < 0) {
            throw new IllegalArgumentException("A data e hora de saída não podem ser anteriores à data e hora de entrada.");
        }

        // Limita o tempo para 187 minutos
        if (minutos > 187) {
            minutos = 187;
        }

        // Calcula o custo baseado no tempo
        double valor = (minutos / 15.0) * TARIFA_PADRAO; // Divisão por 15.0 para garantir valor decimal correto

        // Aplica descontos ou acréscimos dependendo do tipo de vaga
        switch (tipoVaga.toLowerCase()) {
            case "idoso":
                valor *= 0.85; // Desconto de 15%
                break;
            case "pcd":
                valor *= 0.87; // Desconto de 13%
                break;
            case "vip":
                valor *= 1.20; // Acréscimo de 20%
                break;
        }

        // Garante que o valor não exceda o máximo permitido
        return Math.min(valor, MAXIMO_PAGAMENTO);
    }
}
