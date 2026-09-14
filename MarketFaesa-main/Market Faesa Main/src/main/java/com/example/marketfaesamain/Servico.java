package com.example.marketfaesamain;

import java.io.Serializable;
import java.util.List;

public abstract class Servico implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String titulo;
    private String categoria;
    private String descricao;
    private double valor;

    private static int contadorId = 1;

    public Servico(String titulo, String categoria, String descricao, double valor) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.categoria = categoria;
        this.descricao = descricao;
        this.valor = valor;
    }

    public static void sincronizarContador(List<Servico> lista) {
        lista.stream()
                .mapToInt(Servico::getId)
                .max()
                .ifPresent(maiorId -> contadorId = maiorId + 1);
    }

    // ====== MÉTODOS POLIMÓRFICOS ======
    // As subclasses serão obrigadas a implementar esses métodos
    public abstract String getTipo();
    public abstract String getDetalhesExtras();

    // Getters e Setters
    public int getId()           { return id; }
    public void setId(int id)    { this.id = id; } // Necessário caso o tipo do serviço seja alterado na edição
    public String getTitulo()    { return titulo; }
    public String getCategoria() { return categoria; }
    public String getDescricao() { return descricao; }
    public double getValor()     { return valor; }

    public void setTitulo(String titulo)       { this.titulo = titulo; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValor(double valor)         { this.valor = valor; }
}
