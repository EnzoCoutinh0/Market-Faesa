package com.example.marketfaesamain;

import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

// Classe responsável exclusivamente por criar e configurar a TableView de serviços
// Separar isso do main deixa o ServicoApp mais limpo e fácil de ler
public class TabelaServicos {

    // Cria e retorna uma TableView já configurada com colunas e estilo
    // Recebe a lista de serviços para vincular à tabela
    public static TableView<Servico> criar(FilteredList<Servico> listaServicos) {
        TableView<Servico> tv = new TableView<>();

        // Estilo da tabela: fundo escuro, borda sutil
        tv.setStyle(
                "-fx-background-color: " + EstiloUI.COR_PAINEL + ";" +
                        "-fx-border-color: " + EstiloUI.COR_LINHA + ";" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-table-cell-border-color: " + EstiloUI.COR_LINHA + ";"
        );

        // ===== COLUNAS =====
        TableColumn<Servico, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(40);

        TableColumn<Servico, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo")); // Usa polimorfismo (getTipo())
        colTipo.setPrefWidth(80);

        TableColumn<Servico, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTitulo.setPrefWidth(150);

        TableColumn<Servico, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colCategoria.setPrefWidth(110);
        
        TableColumn<Servico, String> colDetalhes = new TableColumn<>("Info Extra");
        colDetalhes.setCellValueFactory(new PropertyValueFactory<>("detalhesExtras")); // Usa polimorfismo
        colDetalhes.setPrefWidth(140);

        TableColumn<Servico, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colDescricao.setPrefWidth(140);

        TableColumn<Servico, Double> colValor = new TableColumn<>("Valor (R$)");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setPrefWidth(80);

        // Atualize a linha que adiciona as colunas:
        tv.getColumns().addAll(colId, colTipo, colTitulo, colCategoria, colDetalhes, colDescricao, colValor);

        // Vincula a ObservableList: qualquer mudança na lista reflete automaticamente na tabela
        tv.setItems(listaServicos);

        // Mensagem exibida quando a lista está vazia
        Label vazio = new Label("Nenhum serviço publicado ainda.\nClique em '+ Novo Serviço' para começar.");
        vazio.setStyle("-fx-text-fill: " + EstiloUI.COR_SUBTEXTO + "; -fx-font-size: 13; -fx-text-alignment: center;");
        vazio.setAlignment(Pos.CENTER);
        tv.setPlaceholder(vazio);

        return tv;
    }
}
