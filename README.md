MarketFaesa — Marketplace Universitário
Aplicação desktop em Java + JavaFX que funciona como marketplace de serviços acadêmicos entre alunos da FAESA: monitorias, aulas particulares, freelas de programação, design, idiomas e afins. O usuário se registra, faz login e pode publicar, buscar, editar e remover serviços.

Projeto acadêmico do curso de Análise e Desenvolvimento de Sistemas — FAESA Centro Universitário.

Stack
Item	Versão / Escolha
Linguagem	Java (compilação configurada para release 25 no pom.xml)
Interface	JavaFX 21.0.6 (javafx-controls, javafx-fxml)
Build	Maven (com Maven Wrapper — mvnw / mvnw.cmd)
Persistência	Serialização binária Java (.dat), sem SGBD
Testes	JUnit 5.12.1 declarado no pom.xml (ainda sem testes escritos)
Modularidade	JPMS via module-info.java
Estrutura do projeto
MarketFaesa/
└── Market Faesa Main/
    ├── pom.xml
    ├── mvnw / mvnw.cmd
    ├── servicos.dat              # gerado em runtime — base de serviços
    ├── usuarios.dat              # gerado em runtime — base de usuários
    └── src/main/
        ├── java/
        │   ├── module-info.java
        │   └── com/example/marketfaesamain/
        │       ├── ServicoApp.java          # entry point (Application) + tela principal
        │       ├── TelaLogin.java           # login e registro de usuário
        │       ├── Usuario.java             # modelo de usuário (login/senha)
        │       ├── Servico.java             # classe ABSTRATA base do serviço
        │       ├── ServicoDigital.java      # subclasse — atributo "plataforma"
        │       ├── ServicoPresencial.java   # subclasse — atributo "local"
        │       ├── BancoDados.java          # persistência em .dat (serviços e usuários)
        │       ├── TabelaServicos.java      # montagem e estilo da TableView
        │       ├── FormularioServico.java   # janela modal de cadastro/edição
        │       └── EstiloUI.java            # paleta de cores e fábrica de componentes
        └── resources/com/example/marketfaesamain/
            └── hello-view.fxml              # resíduo do arquétipo do IntelliJ, não utilizado
Modelagem — herança e polimorfismo
O núcleo orientado a objetos do projeto está em Servico:

Servico é abstract e implementa Serializable. Guarda id, titulo, categoria, descricao e valor, e declara dois métodos abstratos:
getTipo() — devolve "Digital" ou "Presencial";
getDetalhesExtras() — devolve a informação específica de cada modalidade.
ServicoDigital implementa esses métodos usando o campo plataforma (ex.: Zoom, Discord).
ServicoPresencial implementa usando o campo local (ex.: Biblioteca, Bloco B).
A TableView explora esse polimorfismo diretamente: as colunas Tipo e Info Extra usam PropertyValueFactory<>("tipo") e PropertyValueFactory<>("detalhesExtras"), então cada linha exibe o resultado do método da subclasse correspondente, sem nenhum if na tabela.

O id é gerado por um contador static. Como campos static não são serializados, Servico.sincronizarContador() é chamado logo após carregar o .dat e reposiciona o contador no maior id existente + 1, evitando ids duplicados entre execuções.

Funcionalidades
Autenticação (TelaLogin)

Registro de novo usuário com verificação de login duplicado.
Login validado contra a lista carregada de usuarios.dat.
A tela principal só é construída após sucesso — via Runnable de callback passado pelo ServicoApp.
Serviços

Cadastro com modalidade (Digital/Presencial), título, categoria, descrição, campo extra contextual e valor.
O rótulo e o placeholder do campo extra mudam sozinhos conforme a modalidade selecionada.
Categoria por lista fixa (Monitoria, Programação, Design, Idiomas, Música) ou livre: ao escolher "Outro", um campo adicional aparece para digitar a categoria.
Busca em tempo real por título, categoria ou descrição, usando FilteredList sobre a ObservableList.
Edição com preenchimento automático do formulário. Se a modalidade for trocada na edição, o objeto é substituído por uma instância da outra subclasse preservando o id original.
Remoção com diálogo de confirmação.
Validação: todos os campos obrigatórios e valor numérico (aceita vírgula ou ponto).
Persistência

servicos.dat e usuarios.dat são gravados na pasta de execução após cada cadastro, edição e remoção, e também no fechamento da janela.
Ambos são criados automaticamente na primeira execução; se não existirem, o carregamento devolve lista vazia.
Como executar
Pré-requisitos
JDK compatível com a configuração do maven-compiler-plugin (hoje source/target = 25).
Não é necessário baixar o JavaFX SDK: o Maven resolve as dependências do OpenJFX.
Ajuste obrigatório antes do primeiro run
O pom.xml ainda aponta para a classe do arquétipo do IntelliJ, que não existe no projeto. Em javafx-maven-plugin, troque:

<mainClass>com.example.marketfaesamain/com.example.marketfaesamain.HelloApplication</mainClass>
por:

<mainClass>com.example.marketfaesamain/com.example.marketfaesamain.ServicoApp</mainClass>
Rodando
cd "Market Faesa Main"
./mvnw clean javafx:run      # Linux / macOS
mvnw.cmd clean javafx:run    # Windows
Pelo IntelliJ IDEA
Abra a pasta Market Faesa Main como projeto Maven.
Execute ServicoApp.java. Não é preciso configurar --module-path manualmente — o module-info.java e as dependências do Maven cuidam disso.
Limitações conhecidas
Pontos abertos, documentados de propósito para orientar as próximas entregas:

Senhas em texto puro. Usuario guarda a senha como String e o objeto é serializado direto em usuarios.dat. Qualquer pessoa com acesso ao arquivo lê as credenciais. Correção mínima: hash com salt (BCrypt ou PBKDF2 do próprio JDK).
Sem SGBD. A serialização binária quebra a compatibilidade dos arquivos .dat sempre que a estrutura das classes muda de forma incompatível, e não suporta acesso concorrente. Migrar para SQLite/PostgreSQL + JDBC é o próximo passo natural.
Serviço não tem dono. Não existe vínculo entre Usuario e Servico — depois do login, todo mundo enxerga e edita tudo.
Sem testes. JUnit está no pom.xml, mas nenhuma classe de teste foi escrita.
Arquivos versionados indevidamente. target/ (com os .class compilados) e os .dat estão no repositório. Falta um .gitignore.
Resíduo do arquétipo. hello-view.fxml não é carregado por nenhuma classe e pode ser removido; a UI é toda construída por código.
Nome de pasta com espaços. Market Faesa Main complica scripts e comandos de terminal. Renomear para market-faesa evitaria aspas em todo comando.
Java 25 no compilador. Trava o projeto num JDK muito recente. Se a ideia é rodar em várias máquinas do grupo, baixar para 17 ou 21 (LTS) reduz atrito.
Autores
Igor Hermann Salgado
Enzo Ceglias Coutinho
Isaque Novaes
Arthur Nunes Berti Xavier
Tiago Cleto de Azeredo
Lucas de Souza Barboza
Daniel Stieg Radaelle
FAESA Centro Universitário — Análise e Desenvolvimento de Sistemas — 2026
