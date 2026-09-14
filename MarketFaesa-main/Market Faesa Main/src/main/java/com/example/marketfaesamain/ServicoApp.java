package com.example.marketfaesamain;

import javafx.application.Application;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ServicoApp extends Application {

    private ObservableList<Servico> listaServicos = FXCollections.observableArrayList();
    private TableView<Servico> tabela;

    @Override
    public void start(Stage primaryStage) {
        // Inicializa a Tela de Login passando a ação (Runnable) a ser executada após o sucesso
        TelaLogin telaLogin = new TelaLogin(primaryStage, () -> {
            // Este bloco de código só roda quando o login for efetuado com sucesso
            abrirTelaPrincipal(primaryStage);
        });

        // Mostra a tela de login primeiro
        telaLogin.mostrar();
    }

    // Método que reconstrói a sua tela principal com todas as funcionalidades originais
    private void abrirTelaPrincipal(Stage primaryStage) {

        // ===== CARREGAR DADOS =====
        List<Servico> dadosSalvos = BancoDados.carregar();
        listaServicos.addAll(dadosSalvos);
        Servico.sincronizarContador(dadosSalvos); // Garante IDs corretos após carregar

        // ===== CABEÇALHO =====
        Label titulo = new Label("MarketFaesa");
        titulo.setFont(new Font("Arial", 26));
        titulo.setStyle("-fx-font-weight: bold; -fx-text-fill: " + EstiloUI.COR_TEXTO + ";");

        Label badge = new Label("Serviços");
        badge.setStyle(
                "-fx-background-color: " + EstiloUI.COR_ACENTO + "22;" +
                        "-fx-text-fill: " + EstiloUI.COR_ACENTO + ";" +
                        "-fx-font-size: 12;" +
                        "-fx-padding: 3 10;" +
                        "-fx-background-radius: 20;"
        );

        HBox tituloCabecalho = new HBox(10, titulo, badge);
        tituloCabecalho.setAlignment(Pos.CENTER_LEFT);

        Label subtitulo = new Label("Gerencie os serviços publicados na plataforma");
        subtitulo.setStyle("-fx-text-fill: " + EstiloUI.COR_SUBTEXTO + "; -fx-font-size: 12;");

        VBox cabecalho = new VBox(6, tituloCabecalho, subtitulo);

        // ===== BUSCA =====
        FilteredList<Servico> listaFiltrada = new FilteredList<>(listaServicos, s -> true);

        TextField campoBusca = EstiloUI.criarCampo("Buscar por título, categoria ou descrição...");
        campoBusca.setPrefWidth(400);

        campoBusca.textProperty().addListener((obs, anterior, atual) -> {
            listaFiltrada.setPredicate(servico -> {
                if (atual == null || atual.trim().isEmpty()) return true;
                String busca = atual.toLowerCase();
                return servico.getTitulo().toLowerCase().contains(busca)
                        || servico.getCategoria().toLowerCase().contains(busca)
                        || servico.getDescricao().toLowerCase().contains(busca);
            });
        });

        // ===== TABELA =====
        tabela = TabelaServicos.criar(listaFiltrada);

        // ===== FORMULÁRIO =====
        FormularioServico formulario = new FormularioServico(tabela, listaServicos);

        // ===== BOTÕES =====
        Button btnNovo    = EstiloUI.criarBotao("+ Novo Serviço", EstiloUI.COR_ACENTO,   EstiloUI.COR_ACENTO_HOVER);
        Button btnEditar  = EstiloUI.criarBotao("Editar",         EstiloUI.COR_AMARELO,  EstiloUI.COR_AMARELO_HOVER);
        Button btnRemover = EstiloUI.criarBotao("Remover",        EstiloUI.COR_VERMELHO, EstiloUI.COR_VERMELHO_HOVER);

        btnEditar.setDisable(true);
        btnRemover.setDisable(true);

        tabela.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, atual) -> {
                    boolean temSelecao = atual != null;
                    btnEditar.setDisable(!temSelecao);
                    btnRemover.setDisable(!temSelecao);
                }
        );

        btnNovo.setOnAction(e -> {
            formulario.abrir(null, primaryStage);
            BancoDados.salvar(listaServicos); // Salva após cadastro
        });

        btnEditar.setOnAction(e -> {
            Servico selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                formulario.abrir(selecionado, primaryStage);
                BancoDados.salvar(listaServicos); // Salva após edição
            }
        });

        btnRemover.setOnAction(e -> {
            Servico selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                confirmarRemocao(selecionado);
                BancoDados.salvar(listaServicos); // Salva após remoção
            }
        });

        HBox painelBotoes = new HBox(10, btnNovo, btnEditar, btnRemover);
        painelBotoes.setAlignment(Pos.CENTER_LEFT);

        Separator divisor = new Separator();
        divisor.setStyle("-fx-background-color: " + EstiloUI.COR_LINHA + ";");

        // ===== LAYOUT PRINCIPAL =====
        VBox layout = new VBox(14, cabecalho, campoBusca, painelBotoes, divisor, tabela);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: " + EstiloUI.COR_FUNDO + ";");
        VBox.setVgrow(tabela, Priority.ALWAYS);

        primaryStage.setScene(new Scene(layout, 800, 540));
        primaryStage.setTitle("MarketFaesa - Serviços");
        primaryStage.show();

        // Garante o salvamento caso fechem a janela direto pelo "X"
        primaryStage.setOnCloseRequest(e -> BancoDados.salvar(listaServicos));
    }

    private void confirmarRemocao(Servico servico) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar Remoção");
        alerta.setHeaderText("Remover serviço?");
        alerta.setContentText("\"" + servico.getTitulo() + "\" será removido permanentemente.");
        alerta.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) listaServicos.remove(servico);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}