package entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Vaga implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String codigo;
    protected boolean ocupada;
    protected Veiculo veiculo;
    protected LocalDateTime horarioEntrada;

    public Vaga(String codigo) {
        this.codigo = codigo;
        this.ocupada = false;
        this.veiculo = null;
        this.horarioEntrada = null; // Adicionando o horário de entrada
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    // Método para ocupar uma vaga e registrar o horário de entrada
    public void ocupar(Veiculo veiculo) {
        this.ocupada = true;
        this.veiculo = veiculo;
        this.horarioEntrada = LocalDateTime.now();  // Registro de horário de entrada
        System.out.println("Horário de entrada registrado: " + horarioEntrada);
    }

    // Método para liberar uma vaga e calcular o valor a ser cobrado
    public double liberar() {
        if (this.ocupada && this.horarioEntrada != null) {
            LocalDateTime horarioSaida = LocalDateTime.now();  // Registro de horário de saída
            long minutosEstacionados = java.time.Duration.between(horarioEntrada, horarioSaida).toMinutes();
            double valorCobrado = calcularValor(minutosEstacionados);  // Cálculo do valor cobrado
            System.out.println("Veículo " + veiculo.getPlaca() + " liberado. Horário de saída: " + horarioSaida);
            System.out.println("Tempo estacionado: " + minutosEstacionados + " minutos.");
            System.out.println("Valor a pagar: R$ " + valorCobrado);
            this.ocupada = false;
            this.veiculo = null;
            this.horarioEntrada = null;
            return valorCobrado;
        }
        return 0.0;
    }

    // Método para calcular o valor a ser cobrado (R$ 0,50 por minuto)
    private double calcularValor(long minutosEstacionados) {
        return minutosEstacionados * 0.5;
    }

    public String getTipo() {
        return "Normal";
    }
}
