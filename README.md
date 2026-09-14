# 🎓 MarketFaesa

**Marketplace Universitário para alunos da FAESA**

O **MarketFaesa** é uma aplicação desktop desenvolvida em **Java + JavaFX**, criada como projeto acadêmico do curso de **Análise e Desenvolvimento de Sistemas da FAESA Centro Universitário**.

A proposta é funcionar como um marketplace de serviços acadêmicos entre alunos, permitindo a publicação e busca de serviços como:

* 📚 Monitorias
* 👨‍🏫 Aulas particulares
* 💻 Freelas de programação
* 🎨 Design
* 🌎 Idiomas
* 🎵 Música
* Entre outros serviços acadêmicos

O usuário pode se **registrar, fazer login, publicar, buscar, editar e remover serviços**.

---

## 🛠️ Tecnologias utilizadas

| Tecnologia                    | Utilização                                 |
| ----------------------------- | ------------------------------------------ |
| **Java**                      | Linguagem principal                        |
| **JavaFX 21.0.6**             | Interface gráfica                          |
| **Maven**                     | Gerenciamento e build do projeto           |
| **Maven Wrapper**             | Execução sem instalação manual do Maven    |
| **Java Serialization (.dat)** | Persistência dos dados                     |
| **JUnit 5.12.1**              | Framework de testes                        |
| **JPMS**                      | Modularidade através do `module-info.java` |

> O `pom.xml` está configurado atualmente para compilação com **Java 25**.

---

## 📂 Estrutura principal

A aplicação está organizada separando modelos, interface e persistência.

### Principais classes

**`ServicoApp.java`**
Ponto de entrada da aplicação (`Application`) e construção da tela principal.

**`TelaLogin.java`**
Responsável pelo login e cadastro de usuários.

**`Usuario.java`**
Modelo que representa um usuário do sistema.

**`Servico.java`**
Classe abstrata base para os serviços cadastrados.

**`ServicoDigital.java`**
Representa serviços realizados digitalmente e possui o atributo `plataforma`.

**`ServicoPresencial.java`**
Representa serviços presenciais e possui o atributo `local`.

**`BancoDados.java`**
Responsável pela persistência de usuários e serviços nos arquivos `.dat`.

**`TabelaServicos.java`**
Responsável pela construção e estilização da `TableView`.

**`FormularioServico.java`**
Janela utilizada para cadastro e edição de serviços.

**`EstiloUI.java`**
Centraliza a paleta de cores e a criação de componentes da interface.

---

## 🧬 Herança e polimorfismo

O projeto utiliza conceitos de **Programação Orientada a Objetos**, principalmente **abstração, herança e polimorfismo**.

A classe `Servico` é abstrata e implementa `Serializable`.

Ela possui os atributos:

```text
id
titulo
categoria
descricao
valor
```

Também declara os métodos abstratos:

```java
getTipo()
getDetalhesExtras()
```

As subclasses implementam esses métodos de acordo com sua modalidade:

```text
Servico
├── ServicoDigital
└── ServicoPresencial
```

### Serviço Digital

Possui o atributo:

```text
plataforma
```

Exemplos:

```text
Zoom
Discord
Google Meet
```

### Serviço Presencial

Possui o atributo:

```text
local
```

Exemplos:

```text
Biblioteca
Bloco B
Sala de aula
```

A `TableView` utiliza o polimorfismo diretamente. As colunas **Tipo** e **Info Extra** utilizam:

```java
PropertyValueFactory<>("tipo")
PropertyValueFactory<>("detalhesExtras")
```

Assim, cada objeto retorna automaticamente as informações correspondentes à sua subclasse, sem necessidade de condicionais na tabela.

---

## 🔢 Controle de IDs

O ID dos serviços é gerado através de um contador `static`.

Como atributos `static` não são serializados, o método:

```java
Servico.sincronizarContador()
```

é executado após o carregamento do arquivo `.dat`.

Ele identifica o maior ID existente e reposiciona o contador para o próximo valor disponível, evitando duplicação de IDs entre diferentes execuções da aplicação.

---

## 🔐 Autenticação

A tela de login possui:

* Cadastro de novos usuários;
* Verificação de login duplicado;
* Validação das credenciais;
* Carregamento dos usuários através de `usuarios.dat`.

A tela principal somente é construída após um login bem-sucedido, utilizando um `Runnable` como callback.

---

## 🛒 Gerenciamento de serviços

O sistema permite:

### ➕ Cadastro

Ao cadastrar um serviço, o usuário pode definir:

* Modalidade: Digital ou Presencial;
* Título;
* Categoria;
* Descrição;
* Informação adicional;
* Valor.

O campo adicional se adapta automaticamente conforme a modalidade selecionada.

### 🏷️ Categorias

O sistema oferece categorias pré-definidas:

```text
Monitoria
Programação
Design
Idiomas
Música
```

Também existe a opção **Outro**, que permite informar uma categoria personalizada.

### 🔎 Busca

A busca é realizada em tempo real utilizando `FilteredList`.

É possível pesquisar por:

* Título;
* Categoria;
* Descrição.

### ✏️ Edição

Os dados do serviço são carregados automaticamente no formulário para alteração.

Caso a modalidade seja alterada, o objeto é substituído pela subclasse correspondente, mantendo o **ID original**.

### 🗑️ Remoção

A exclusão possui um diálogo de confirmação antes de remover o serviço.

### ✅ Validação

O sistema valida:

* Campos obrigatórios;
* Valor numérico;
* Utilização de vírgula ou ponto no valor.

---

## 💾 Persistência

O projeto não utiliza banco de dados.

As informações são armazenadas através da **serialização binária do Java**, utilizando:

```text
servicos.dat
usuarios.dat
```

Os arquivos são criados automaticamente na primeira execução.

Os dados são salvos após:

* Cadastro;
* Edição;
* Remoção;
* Fechamento da aplicação.

Caso os arquivos ainda não existam, o sistema inicia com listas vazias.

---

## ▶️ Como executar

### Pré-requisitos

É necessário possuir um **JDK compatível com a configuração do projeto**.

Atualmente:

```text
Java 25
```

Não é necessário baixar o JavaFX SDK manualmente, pois o Maven resolve as dependências do OpenJFX.

### ⚠️ Ajuste necessário

Antes da primeira execução, verifique o `pom.xml`.

O plugin `javafx-maven-plugin` originalmente aponta para a classe do arquétipo do IntelliJ.

Substitua:

```text
com.example.marketfaesamain/com.example.marketfaesamain.HelloApplication
```

por:

```text
com.example.marketfaesamain/com.example.marketfaesamain.ServicoApp
```

### Windows

Dentro da pasta do projeto:

```bash
mvnw.cmd clean javafx:run
```

### Linux / macOS

```bash
./mvnw clean javafx:run
```

---

## 💻 Executando pelo IntelliJ IDEA

1. Abra a pasta **Market Faesa Main** no IntelliJ IDEA.
2. Aguarde o carregamento das dependências do Maven.
3. Localize `ServicoApp.java`.
4. Execute a classe.

Não é necessário configurar manualmente o `--module-path`, pois o `module-info.java` e as dependências do Maven cuidam da configuração dos módulos.

---

## ⚠️ Limitações conhecidas

O projeto possui alguns pontos que podem ser melhorados em versões futuras.

### Senhas em texto puro

Atualmente, a senha é armazenada como `String` e o objeto `Usuario` é serializado diretamente no `usuarios.dat`.

Isso significa que alguém com acesso ao arquivo pode recuperar as credenciais.

Uma melhoria recomendada é utilizar **hash com salt**, por exemplo:

* BCrypt;
* PBKDF2 disponível no próprio JDK.

### Sem banco de dados

A aplicação utiliza serialização `.dat`.

Essa abordagem apresenta limitações de compatibilidade quando a estrutura das classes é alterada e não é adequada para acesso concorrente.

Uma evolução possível seria utilizar:

```text
SQLite
PostgreSQL
JDBC
```

### Serviços sem proprietário

Atualmente não existe uma relação entre `Usuario` e `Servico`.

Após realizar o login, os usuários podem visualizar e editar os serviços existentes.

Uma melhoria futura seria associar cada serviço ao usuário responsável pela publicação.

### Testes

O JUnit 5.12.1 está configurado no projeto, porém ainda não existem classes de teste implementadas.

### Arquivos versionados

Os arquivos `target/` e `.dat` estão presentes no repositório.

Uma melhoria recomendada é criar um `.gitignore` para evitar o versionamento de arquivos gerados durante a execução.

### Arquivo não utilizado

O arquivo:

```text
hello-view.fxml
```

é um resíduo do arquétipo do IntelliJ e não é utilizado pela aplicação.

A interface atual é construída diretamente através de código JavaFX.

### Nome da pasta

A pasta principal atualmente possui espaços:

```text
Market Faesa Main
```

Uma alternativa seria utilizar:

```text
market-faesa
```

Isso facilita a utilização em scripts e comandos de terminal.

### Java 25

O projeto está configurado para Java 25, que é uma versão recente.

Para facilitar a execução em diferentes computadores, uma futura versão poderia utilizar uma versão **LTS**, como Java 21.

---

## 👥 Autores

**Igor Hermann Salgado**
**Enzo Ceglias Coutinho**
**Isaque Novaes**
**Arthur Nunes Berti Xavier**
**Tiago Cleto de Azeredo**
**Lucas de Souza Barboza**
**Daniel Stieg Radaelle**

---

## 🎓 Instituição

**FAESA Centro Universitário**

Curso de **Análise e Desenvolvimento de Sistemas — ADS**

**2026**

---

⭐ Projeto desenvolvido para fins acadêmicos.
