package com.example.marketfaesamain;

public class ServicoDigital extends Servico {
    private String plataforma;

    public ServicoDigital(String titulo, String categoria, String descricao, double valor, String plataforma) {
        super(titulo, categoria, descricao, valor);
        this.plataforma = plataforma;
    }

    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }

    @Override
    public String getTipo() {
        return "Digital";
    }

    @Override
    public String getDetalhesExtras() {
        return "Plataforma: " + plataforma;
    }
}
