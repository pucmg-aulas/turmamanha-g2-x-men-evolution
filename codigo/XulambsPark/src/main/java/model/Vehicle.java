package model;

public class Vehicle {
    private String placa;
    private String model;
    private String color;
    private String owner;
    private String cpf;

    public Vehicle(String placa, String model, String color, String owner, String cpf) {
        this.placa = placa;
        this.model = model;
        this.color = color;
        this.owner = owner;
        this.cpf = cpf;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getOwner() {
        return owner;
    }

    public String getCpf() {
        return cpf;
    }
}