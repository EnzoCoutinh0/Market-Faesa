# MarketFaesa — Marketplace Universitário

Plataforma desktop de marketplace universitário desenvolvida em Java com JavaFX, permitindo que estudantes publiquem, editem e busquem serviços acadêmicos como monitorias, freelas e aulas particulares.

---

## Tecnologias

- Java 17+
- JavaFX
- Serialização binária (.dat) para persistência de dados

---

## Estrutura do Projeto

```
MarketFaesa/
├── Servico.java              # Modelo de dados
├── BancoDados.java           # Persistência em arquivo binário .dat
├── TabelaServicos.java       # Configuração da TableView
├── FormularioServico.java    # Janela de cadastro e edição
├── EstiloUI.java             # Constantes de cor e fábricas de componentes
└── ServicoApp.java           # Ponto de entrada da aplicação
```

---

## Funcionalidades

- Cadastro de serviços com título, categoria, descrição e valor
- Categoria personalizada: ao selecionar "Outro", um campo extra é exibido para o usuário digitar a categoria
- Listagem em tabela com colunas fixas (sem redimensionamento ou reordenação)
- Busca em tempo real por título, categoria ou descrição
- Edição e remoção de serviços com confirmação
- Persistência automática: dados salvos em `servicos.dat` na raiz do projeto a cada operação

---

## Como Executar

### Pré-requisitos

- JDK 17 ou superior instalado
- JavaFX SDK instalado ([download](https://gluonhq.com/products/javafx/))

### Compilar

```bash
javac --module-path /caminho/javafx-sdk/lib --add-modules javafx.controls *.java
```

### Executar

```bash
java --module-path /caminho/javafx-sdk/lib --add-modules javafx.controls ServicoApp
```

### Via IntelliJ IDEA

1. Abra o projeto no IntelliJ
2. Vá em `Run → Edit Configurations`
3. Em VM Options adicione:
```
--module-path /caminho/javafx-sdk/lib --add-modules javafx.controls
```
4. Execute `ServicoApp.java`

---

## Persistência de Dados

Os dados são salvos automaticamente no arquivo `servicos.dat` na raiz do projeto após cada cadastro, edição ou remoção. O arquivo é criado automaticamente na primeira execução.

---

## Autores

- Igor Hermann Salgado
- Enzo Ceglias Coutinho
- Isaque Novaes
- Arthur Nunes Berti Xavier
- Tiago Cleto de Azeredo
- Lucas de Souza Barboza
- Daniel Stieg Radaelle

---

FAESA Centro Universitário — Análise e Desenvolvimento de Sistemas — 2026
