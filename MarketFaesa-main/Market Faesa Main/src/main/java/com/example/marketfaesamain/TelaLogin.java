package com.example.marketfaesamain;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class TelaLogin {

    private Stage stage;
    private Runnable onLoginSucesso;
    private List<Usuario> listaUsuarios;

    public TelaLogin(Stage stage, Runnable onLoginSucesso) {
        this.stage = stage;
        this.onLoginSucesso = onLoginSucesso;
        this.listaUsuarios = BancoDados.carregarUsuarios();
    }

    public void mostrar() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: " + EstiloUI.COR_FUNDO + ";");

        Label titulo = new Label("MarketFaesa - Login");
        titulo.setFont(new Font("Arial", 22));
        titulo.setStyle("-fx-font-weight: bold; -fx-text-fill: " + EstiloUI.COR_TEXTO + ";");

        TextField campoUsuario = EstiloUI.criarCampo("Nome de Usuário");
        campoUsuario.setMaxWidth(250);

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Senha");
        campoSenha.setMaxWidth(250);
        campoSenha.setStyle("-fx-font-size: 14px; -fx-padding: 8px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        Label lblErro = new Label();
        lblErro.setStyle("-fx-text-fill: " + EstiloUI.COR_VERMELHO + ";");

        Button btnEntrar = EstiloUI.criarBotao("Entrar", EstiloUI.COR_ACENTO, EstiloUI.COR_ACENTO_HOVER);
        btnEntrar.setMaxWidth(250);

        Button btnRegistrar = EstiloUI.criarBotao("Registrar-se", "#4CAF50", "#45A049");
        btnRegistrar.setMaxWidth(250);

        btnEntrar.setOnAction(e -> {
            String login = campoUsuario.getText().trim();
            String senha = campoSenha.getText().trim();

            if (login.isEmpty() || senha.isEmpty()) {
                lblErro.setText("Preencha ambos os campos!");
                return;
            }

            boolean autenticado = listaUsuarios.stream()
                    .anyMatch(u -> u.getLogin().equals(login) && u.getSenha().equals(senha));

            if (autenticado) {
                // Se a senha estiver correta, executa o comando para abrir a tela principal
                onLoginSucesso.run();
            } else {
                lblErro.setText("Usuário ou senha incorretos.");
            }
        });

        btnRegistrar.setOnAction(e -> {
            String login = campoUsuario.getText().trim();
            String senha = campoSenha.getText().trim();

            if (login.isEmpty() || senha.isEmpty()) {
                lblErro.setText("Preencha os campos para registrar.");
                return;
            }

            boolean jaExiste = listaUsuarios.stream().anyMatch(u -> u.getLogin().equals(login));
            if (jaExiste) {
                lblErro.setText("Este usuário já existe.");
            } else {
                listaUsuarios.add(new Usuario(login, senha));
                BancoDados.salvarUsuarios(listaUsuarios);
                lblErro.setStyle("-fx-text-fill: #4CAF50;"); // Verde para sucesso
                lblErro.setText("Usuário registrado com sucesso!");
                campoSenha.clear();
            }
        });

        layout.getChildren().addAll(titulo, campoUsuario, campoSenha, lblErro, btnEntrar, btnRegistrar);

        Scene cena = new Scene(layout, 400, 450);
        stage.setTitle("MarketFaesa - Autenticação");
        stage.setScene(cena);
        stage.show();
    }
}