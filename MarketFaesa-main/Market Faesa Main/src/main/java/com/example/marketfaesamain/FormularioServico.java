package com.example.marketfaesamain;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FormularioServico {

    private TableView<Servico> tabela;
    private ObservableList<Servico> listaServicos;

    public FormularioServico(TableView<Servico> tabela, ObservableList<Servico> listaServicos) {
        this.tabela = tabela;
        this.listaServicos = listaServicos;
    }

    public void abrir(Servico servicoExistente, Stage owner) {
        Stage janela = new Stage();
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.initOwner(owner);
        janela.setTitle(servicoExistente == null ? "Novo Serviço" : "Editar Serviço");

        Label tituloForm = new Label(servicoExistente == null ? "Publicar Serviço" : "Editar Serviço");
        tituloForm.setFont(new Font("Arial", 17));
        tituloForm.setStyle("-fx-font-weight: bold; -fx-text-fill: " + EstiloUI.COR_TEXTO + ";");

        ComboBox<String> campTipoModalidade = EstiloUI.criarComboBox("Selecione a modalidade");
        campTipoModalidade.getItems().addAll("Digital", "Presencial");
        
        Label rotuloExtra = EstiloUI.rotulo("Detalhes");
        TextField campExtra = EstiloUI.criarCampo("Plataforma ou Local");

        campTipoModalidade.valueProperty().addListener((obs, antigo, atual) -> {
            if ("Digital".equals(atual)) {
                rotuloExtra.setText("Plataforma:");
                campExtra.setPromptText("Ex: Zoom, Discord...");
            } else if ("Presencial".equals(atual)) {
                rotuloExtra.setText("Local:");
                campExtra.setPromptText("Ex: Biblioteca, Bloco B...");
            }
        });

        TextField campTitulo    = EstiloUI.criarCampo("Ex: Aula de Cálculo I");
        TextField campDescricao = EstiloUI.criarCampo("Ex: Aulas semanais de 1h");
        TextField campValor     = EstiloUI.criarCampo("Ex: 50.00");

        ComboBox<String> campCategoria = EstiloUI.criarComboBox("Selecione uma categoria");
        campCategoria.getItems().addAll("Monitoria", "Programação", "Design", "Idiomas", "Música", "Outro");

        TextField campCategoriaCustom = EstiloUI.criarCampo("Digite a categoria");
        Label rotuloCustom = EstiloUI.rotulo("Qual categoria?");
        campCategoriaCustom.setVisible(false); campCategoriaCustom.setManaged(false);
        rotuloCustom.setVisible(false); rotuloCustom.setManaged(false);

        campCategoria.valueProperty().addListener((obs, ant, atual) -> {
            boolean ehOutro = "Outro".equals(atual);
            campCategoriaCustom.setVisible(ehOutro); campCategoriaCustom.setManaged(ehOutro);
            rotuloCustom.setVisible(ehOutro); rotuloCustom.setManaged(ehOutro);
            if (!ehOutro) campCategoriaCustom.clear();
        });

        // Preenchimento na Edição
        if (servicoExistente != null) {
            campTitulo.setText(servicoExistente.getTitulo());
            campDescricao.setText(servicoExistente.getDescricao());
            campValor.setText(String.valueOf(servicoExistente.getValor()));

            if (campCategoria.getItems().contains(servicoExistente.getCategoria())) {
                campCategoria.setValue(servicoExistente.getCategoria());
            } else {
                campCategoria.setValue("Outro");
                campCategoriaCustom.setText(servicoExistente.getCategoria());
            }

            // Descobre qual a classe filha para preencher o formulário adequadamente
            if (servicoExistente instanceof ServicoDigital) {
                campTipoModalidade.setValue("Digital");
                campExtra.setText(((ServicoDigital) servicoExistente).getPlataforma());
            } else if (servicoExistente instanceof ServicoPresencial) {
                campTipoModalidade.setValue("Presencial");
                campExtra.setText(((ServicoPresencial) servicoExistente).getLocal());
            }
        } else {
            campTipoModalidade.setValue("Digital"); // Padrão
        }

        Label lblErro = new Label();
        lblErro.setStyle("-fx-text-fill: " + EstiloUI.COR_VERMELHO + "; -fx-font-size: 11;");

        Button btnSalvar = EstiloUI.criarBotao("Salvar", EstiloUI.COR_ACENTO, EstiloUI.COR_ACENTO_HOVER);
        btnSalvar.setPrefWidth(Double.MAX_VALUE);

        btnSalvar.setOnAction(e -> {
            String titulo    = campTitulo.getText().trim();
            String selecao   = campCategoria.getValue();
            String descricao = campDescricao.getText().trim();
            String valorTxt  = campValor.getText().trim();
            String modalidade = campTipoModalidade.getValue();
            String extra     = campExtra.getText().trim();

            String categoria = "Outro".equals(selecao) ? campCategoriaCustom.getText().trim() : selecao;

            if (titulo.isEmpty() || categoria == null || categoria.isEmpty() || descricao.isEmpty() || valorTxt.isEmpty() || extra.isEmpty()) {
                lblErro.setText("Preencha todos os campos antes de salvar.");
                return;
            }

            double valor;
            try { valor = Double.parseDouble(valorTxt.replace(",", ".")); } 
            catch (NumberFormatException ex) { lblErro.setText("O valor deve ser numérico. Ex: 50.00"); return; }

            // Lógica de Herança: Instancia a subclasse correta dependendo da seleção
            Servico novoServico;
            if ("Digital".equals(modalidade)) {
                novoServico = new ServicoDigital(titulo, categoria, descricao, valor, extra);
            } else {
                novoServico = new ServicoPresencial(titulo, categoria, descricao, valor, extra);
            }

            if (servicoExistente == null) {
                listaServicos.add(novoServico);
            } else {
                // Na edição, se o usuário mudou o tipo, substituímos o objeto inteiro na lista
                novoServico.setId(servicoExistente.getId()); // Preserva o ID original
                int index = listaServicos.indexOf(servicoExistente);
                listaServicos.set(index, novoServico);
                tabela.refresh();
            }

            janela.close();
        });

        GridPane grid = new GridPane();
        grid.setHgap(12); grid.setVgap(14); grid.setPadding(new Insets(25));
        grid.setStyle("-fx-background-color: " + EstiloUI.COR_FUNDO + ";");

        grid.add(tituloForm,                      0, 0, 2, 1);
        grid.add(EstiloUI.rotulo("Modalidade"),   0, 1); grid.add(campTipoModalidade, 1, 1);
        grid.add(EstiloUI.rotulo("Título"),       0, 2); grid.add(campTitulo,         1, 2);
        grid.add(EstiloUI.rotulo("Categoria"),    0, 3); grid.add(campCategoria,      1, 3);
        grid.add(rotuloCustom,                    0, 4); grid.add(campCategoriaCustom,1, 4);
        grid.add(EstiloUI.rotulo("Descrição"),    0, 5); grid.add(campDescricao,      1, 5);
        grid.add(rotuloExtra,                     0, 6); grid.add(campExtra,          1, 6);
        grid.add(EstiloUI.rotulo("Valor R$"),     0, 7); grid.add(campValor,          1, 7);
        grid.add(lblErro,                         0, 8, 2, 1);
        grid.add(btnSalvar,                       0, 9, 2, 1);

        ColumnConstraints c1 = new ColumnConstraints(80);
        ColumnConstraints c2 = new ColumnConstraints(300);
        grid.getColumnConstraints().addAll(c1, c2);

        janela.setScene(new Scene(grid, 440, 480));
        janela.showAndWait();
    }
}
