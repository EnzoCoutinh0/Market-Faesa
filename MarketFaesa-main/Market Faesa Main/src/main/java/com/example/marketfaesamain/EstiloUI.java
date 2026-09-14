package com.example.marketfaesamain;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

// Classe utilitária de estilo — centraliza todas as cores e fábrica de componentes visuais
// Todos os métodos são estáticos: não precisa criar um objeto EstiloUI para usar
public class EstiloUI {

    // ===== PALETA DE CORES =====
    // Definidas como constantes para facilitar manutenção
    // Se quiser mudar uma cor, muda aqui e reflete em todo o projeto
    public static final String COR_FUNDO         = "#0f1117";
    public static final String COR_PAINEL        = "#1a1d27";
    public static final String COR_ACENTO        = "#4f8ef7";
    public static final String COR_ACENTO_HOVER  = "#3a7ae0";
    public static final String COR_AMARELO       = "#f0a500";
    public static final String COR_AMARELO_HOVER = "#d4920a";
    public static final String COR_VERMELHO      = "#e05260";
    public static final String COR_VERMELHO_HOVER= "#c43d4b";
    public static final String COR_TEXTO         = "#e8eaf0";
    public static final String COR_SUBTEXTO      = "#6b7280";
    public static final String COR_LINHA         = "#23263a";

    // ===== FÁBRICA DE BOTÕES =====
    // Cria um botão já estilizado com cor base e efeito hover
    public static Button criarBotao(String texto, String cor, String corHover) {
        Button btn = new Button(texto);
        String estiloBase = String.format(
                "-fx-background-color: %s; -fx-text-fill: white;" +
                        "-fx-font-size: 12; -fx-padding: 8 18;" +
                        "-fx-background-radius: 6; -fx-cursor: hand; -fx-font-weight: bold;", cor
        );
        String estiloHover = estiloBase.replace(cor, corHover);
        btn.setStyle(estiloBase);
        btn.setOnMouseEntered(e -> btn.setStyle(estiloHover));
        btn.setOnMouseExited(e -> btn.setStyle(estiloBase));
        return btn;
    }

    // ===== FÁBRICA DE CAMPOS DE TEXTO =====
    // Cria um TextField com o estilo escuro padrão do projeto
    public static TextField criarCampo(String placeholder) {
        TextField tf = new TextField();
        tf.setPromptText(placeholder);
        tf.setStyle(estiloCampo());
        return tf;
    }

    // ===== FÁBRICA DE COMBOBOX =====
    // Cria um ComboBox estilizado (usado no campo Categoria do formulário)
    public static ComboBox<String> criarComboBox(String placeholder) {
        ComboBox<String> cb = new ComboBox<>();
        cb.setPromptText(placeholder);
        cb.setPrefWidth(Double.MAX_VALUE);
        cb.setStyle(estiloCampo());
        return cb;
    }

    // ===== FÁBRICA DE RÓTULOS =====
    // Cria um Label de label de campo com estilo secundário
    public static Label rotulo(String texto) {
        Label l = new Label(texto + ":");
        l.setStyle("-fx-text-fill: " + COR_SUBTEXTO + "; -fx-font-size: 12;");
        return l;
    }

    // ===== ESTILO BASE DOS CAMPOS =====
    // Método privado reutilizado internamente por criarCampo e criarComboBox
    public static String estiloCampo() {
        return "-fx-background-color: " + COR_PAINEL + ";" +
                "-fx-text-fill: " + COR_TEXTO + ";" +
                "-fx-prompt-text-fill: " + COR_SUBTEXTO + ";" +
                "-fx-border-color: " + COR_LINHA + ";" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;" +
                "-fx-padding: 8;";
    }
}