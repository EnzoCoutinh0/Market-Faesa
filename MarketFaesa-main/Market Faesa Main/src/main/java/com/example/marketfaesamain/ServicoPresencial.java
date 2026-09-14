package com.example.marketfaesamain;

public class ServicoPresencial extends Servico {
    private String local;

    public ServicoPresencial(String titulo, String categoria, String descricao, double valor, String local) {
        super(titulo, categoria, descricao, valor);
        this.local = local;
    }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    @Override
    public String getTipo() {
        return "Presencial";
    }

    @Override
    public String getDetalhesExtras() {
        return "Local: " + local;
    }
}
