# MyCursos — TP2 · AEDS III · Grupo 02 · PUC Minas 2026

## Participantes

| Nome | Código de Pessoa |
|---|---|
| Daniel Santos | 1598779 |
| Daniel Rocha | 1543499 |
| Lucca Lopes | 642298 |
| Rafael Henrique Silva | 1543739 |

## Professor

Prof. Dr. Marcos André Silveira Kutova

---


## Descrição do Sistema

O **MyCursos** é um sistema de gestão de inscrições em cursos livres desenvolvido como Trabalho Prático da disciplina **Algoritmos e Estruturas de Dados III (AEDS III)** do curso de Ciência da Computação da PUC Minas.

O **TP1** implementou o cadastro e gerenciamento de **usuários** e **cursos**, com autenticação por email/senha, relacionamento 1:N entre usuários e cursos e gerenciamento completo de estados de curso.

O **TP2** acrescenta a **busca e listagem de cursos** com paginação, a **tela de detalhes do curso** (visão do aluno), o relacionamento **N:N** entre usuários e cursos por meio de uma entidade de associação `CursoUsuario` com duas Árvores B+ de índice, e o gerenciamento completo de inscrições (incluindo a visão do proponente sobre seus inscritos).

O sistema roda em modo texto (terminal) e se baseia no padrão **MVC**, com separação entre entidades de dados (`Entidades`), operações de persistência (`CRUD`), menus de interface (`Menus`) e lógica de negócio (`Controles`). A navegação usa uma pilha de menus com **breadcrumb** automático exibido em cada tela.

---

## Telas do Sistema

### Tela de Autenticação

![Tela de autenticação](screenshots/tela-autenticacao.png)

### Tela da Home

![Tela principal](screenshots/tela-home.png)

### Tela de Meus Cursos

![Tela de meus cursos](screenshots/tela-meus-cursos.png)

### Tela de Meus Dados

![Tela de meus dados](screenshots/tela-meus-dados.png)

### Tela de Novo Curso

![Tela de novo curso](screenshots/tela-novo-curso.png)

### Tela de Minhas Inscrições (TP2)

Em andamento.

### Tela de Lista de Cursos (TP2)

Em andamento.

### Tela de Detalhes do Curso (TP2)

Em andamento.

---

## Vídeo de Demonstração

### TP 1
[VideoTrabalhoPraticoI_AEDsIII_Grupo02.mkv](Videos/VideoTrabalhoPraticoI_AEDsIII_Grupo02.mkv)


### TP2
Em andamento.

---

## Arquitetura e Classes Criadas

```
pucminas-cc-2026-ti3-g02-tp1/
├── Main.java
├── Entidades/
│   ├── Usuario.java                             # Entidade Usuário
│   ├── Curso.java                               # Entidade Curso (campo transiente `autor`)
│   ├── CursoUsuario.java                        # Entidade de associação N:N (TP2)
│   └── EstadoCurso.java                         # Enum de estados do curso
├── CRUD/
│   ├── CrudUsuario.java                         # CRUD de usuários (índice hash de email)
│   ├── CrudCurso.java                           # CRUD de cursos (hash de código + 2 Árvores B+)
│   └── CrudCursoUsuario.java                    # CRUD de inscrições (2 Árvores B+) (TP2)
├── Genericos/
│   ├── Arquivo.java                             # Base genérica de arquivo binário
│   ├── HashExtensivel.java                      # Tabela Hash Extensível genérica
│   ├── ArvoreBMais.java                         # Árvore B+ genérica
│   ├── Registro.java                            # Interface para entidades serializáveis
│   ├── RegistroHashExtensivel.java              # Interface para registros de hash
│   ├── RegistroArvoreBMais.java                 # Interface para registros de árvore B+
│   ├── ParIDEndereco.java                       # Par (id → endereço) — índice direto
│   ├── ParEmailID.java                          # Par (email → id) — índice de email
│   ├── ParCodigoID.java                         # Par (código → id) — índice de código NanoID
│   ├── ParIdUsuarioIdCurso.java                 # Par (idUsuario, idCurso) — relacionamento 1:N
│   ├── ParUsuarioNomeCursoId.java               # Par (idUsuario, nome, idCurso) — ordenação
│   ├── ParIdCursoIdCursoUsuario.java            # Par (idCurso, idCursoUsuario) — índice B+ (TP2)
│   └── ParIdUsuarioIdCursoUsuario.java          # Par (idUsuario, idCursoUsuario) — índice B+ (TP2)
└── InterfaceGrafica/
    ├── Menus/
    │   ├── IMenu.java                           # Interface de menu
    │   ├── GerenciadorDeMenus.java              # Pilha de menus + breadcrumb + sessão do usuário
    │   ├── MenuUtils.java                       # Exibição e leitura de opções de menu
    │   ├── Home/
    │   │   └── MenuHome.java                    # Menu principal (Meus Dados / Meus Cursos / Sair)
    │   ├── Usuario/
    │   │   ├── MenuAuth.java                    # Tela inicial (Login / Cadastro / Sair)
    │   │   ├── MenuLogin.java                   # Formulário de login
    │   │   ├── MenuCadastro.java                # Formulário de cadastro
    │   │   ├── MenuMeusDados.java               # Perfil do usuário
    │   │   ├── MenuMeusCursos.java              # Listagem de cursos do usuário
    │   │   └── MenuMinhasInscricoes.java        # Busca, listagem e inscrições (TP2)
    │   └── Curso/
    │       ├── MenuCurso.java                   # Detalhes e ações sobre um curso (visão do dono)
    │       ├── MenuNovoCurso.java               # Formulário de criação de curso
    │       ├── MenuAlterarCurso.java            # Formulário de edição de curso
    │       ├── MenuGerenciarInscritos.java      # Gerenciar inscritos (TP2)
    │       ├── MenuListaCursos.java             # Lista paginada de todos os cursos (TP2)
    │       └── MenuDetalhesCurso.java           # Detalhes do curso — visão do aluno (TP2)
    ├── Controles/
    │   ├── Home/
    │   │   └── ControleHome.java               # Lógica do menu principal
    │   ├── Usuario/
    │   │   ├── ControleAuth.java               # Roteamento de autenticação
    │   │   ├── ControleLogin.java              # Validação de email e hash de senha
    │   │   ├── ControleCadastro.java           # Cadastro com validação de email único
    │   │   ├── ControleMeusDados.java          # Edição de perfil (nome, email, senha)
    │   │   ├── ControleMeusCursos.java         # Listagem e seleção de cursos
    │   │   └── ControleMinhasInscricoes.java   # Busca por código, listagem e inscrições (TP2)
    │   └── Curso/
    │       ├── ControleCurso.java              # Ações sobre curso (estado + navegação)
    │       ├── ControleNovoCurso.java          # Criação de curso
    │       └── ControleAlterarCurso.java       # Edição de nome, descrição e data
    └── Opcoes/
        ├── IOpcaoMenu.java
        ├── Home/OpcaoHome.java
        ├── Usuario/
        │   ├── OpcaoAuth.java
        │   ├── OpcaoMeusCursos.java
        │   ├── OpcaoMeusDados.java
        │   └── OpcaoMinhasInscricoes.java      # Atualizado: buscar (A) e listar (C) (TP2)
        └── Curso/
            ├── OpcaoCurso.java
            ├── OpcaoAlterarCurso.java
            ├── OpcaoGerenciarInscritos.java
            ├── OpcaoListaCurso.java             # Opções de paginação (TP2)
            └── OpcaoDetalhesCurso.java          # Opções da tela de detalhes do aluno (TP2)
```

---

## Detalhamento das Entidades

### Usuário (`Usuario.java`)

| Atributo | Tipo | Descrição |
|---|---|---|
| `id` | `int` | Identificador sequencial único (gerado automaticamente) |
| `nome` | `String` | Nome completo |
| `email` | `String` | Usado como login; pode ser alterado (ID não muda) |
| `hashSenha` | `String` | `String.valueOf(senha.hashCode())` |
| `perguntaSecreta` | `String` | Pergunta para recuperação de senha |
| `respostaSecreta` | `String` | Resposta à pergunta secreta |

### Curso (`Curso.java`)

| Atributo | Tipo | Descrição |
|---|---|---|
| `idCurso` | `int` | Identificador sequencial único |
| `idUsuario` | `int` | Chave estrangeira — ID do usuário dono do curso |
| `nome` | `String` | Nome do curso |
| `codigo` | `String` (10 chars) | Código NanoID gerado automaticamente na criação |
| `descricao` | `String` | Descrição detalhada |
| `dataInicio` | `Date` | Data de início prevista |
| `estado` | `char` | Estado atual (ver tabela abaixo) |
| `autor` | `String` | **Transiente** — nome do dono; preenchido em memória via `CrudUsuario` |

### CursoUsuario (`CursoUsuario.java`) — TP2

| Atributo | Tipo | Descrição |
|---|---|---|
| `idCursoUsuario` | `int` | Identificador sequencial único |
| `idCurso` | `int` | Chave estrangeira — ID do curso |
| `idUsuario` | `int` | Chave estrangeira — ID do usuário inscrito |
| `dataInscricao` | `Date` | Data em que a inscrição foi realizada |

### Estados do Curso (`EstadoCurso.java`)

| Valor | Constante | Descrição |
|---|---|---|
| `'0'` | `ATIVO_INSCRICOES` | Ativo e recebendo inscrições |
| `'1'` | `ATIVO_SEM_INSCRICOES` | Ativo, mas sem novas inscrições |
| `'2'` | `CONCLUIDO` | Realizado e concluído |
| `'3'` | `CANCELADO` | Cancelado |

---

## Operações Implementadas

### Autenticação

- **Login**: o usuário informa email e senha. O sistema localiza o registro via `HashExtensivel` (índice de email), calcula `String.valueOf(senha.hashCode())` e compara com o `hashSenha` armazenado. Se conferir, o usuário fica em sessão no `GerenciadorDeMenus`.
- **Cadastro**: preenche nome, email, senha, pergunta e resposta secreta. O sistema valida que o email ainda não existe antes de criar o registro. Após o cadastro, o usuário já fica logado.

### Gerenciamento de Usuários

- **Visualizar dados**: exibe nome, email e pergunta secreta (sem expor senha).
- **Alterar nome**: atualiza o registro no arquivo e no índice direto.
- **Alterar email**: remove o par antigo do índice hash de email e insere o novo antes de gravar.
- **Alterar senha**: exige resposta correta à pergunta secreta; a nova senha é armazenada como hash.

### Gerenciamento de Cursos

- **Listar cursos**: percorre a `ArvoreBMais<ParUsuarioNomeCursoId>` por prefixo (`idUsuario`) e retorna os cursos já em ordem alfabética. O número exibido no menu é sequencial (1, 2, 3…), independente do ID interno.
- **Criar novo curso**: o usuário informa nome, descrição e data de início (formato `dd/MM/yyyy`). O sistema gera automaticamente o código NanoID de 10 caracteres, define o estado inicial como `'0'` (aberto) e associa o `idUsuario` do usuário logado. Ao gravar, os três índices de cursos são atualizados.
- **Visualizar curso**: exibe código, nome, estado (com descrição textual via `EstadoCurso`), data de início e descrição.
- **Editar curso** (`MenuAlterarCurso` / `ControleAlterarCurso`): permite alterar nome, descrição e data de início. Cada alteração chama `CrudCurso.update()`, que sincroniza todos os índices afetados (inclusive a Árvore B+ de nomes, se o nome mudar).
- **Encerrar inscrições**: exige confirmação, define estado `'1'` e atualiza via `CrudCurso.update()`.
- **Concluir curso**: exige confirmação, define estado `'2'` e atualiza.
- **Cancelar curso**: exibe aviso "Esta ação não pode ser desfeita!", exige confirmação e define estado `'3'`.
- **Gerenciar inscritos**: navega para `MenuGerenciarInscritos` (TP2).

### Busca e Listagem de Cursos (TP2)

- **Buscar por código NanoID** (`ControleMinhasInscricoes.buscarPorCodigo`): o usuário informa o código de 10 caracteres. O sistema consulta o índice `HashExtensivel<ParCodigoID>` via `CrudCurso.read(String codigo)` e abre diretamente a tela de detalhes do curso, sem exibir lista intermediária.
- **Listar todos os cursos** (`CrudCurso.listarCursosOrdenadoDataInicio`): recupera todos os registros ativos do arquivo via `Arquivo.readAll()` (varredura sequencial do `RandomAccessFile`, pulando registros com lápide `'*'`) e ordena por data de início com `Comparator.comparing(Curso::getDataInicio)`.
- **Paginação** (`MenuListaCursos`): exibe 10 cursos por página. Teclas `(1)` a `(9)` selecionam o 1º ao 9º item; tecla `(0)` seleciona o 10º (fórmula: `tecla = (posNaPagina + 1) % 10`). Navegação com `(A)` página anterior e `(B)` próxima página.
- **Detalhes do curso** (`MenuDetalhesCurso`): exibe código NanoID (`getCodigo()`), nome, autor (nome do usuário criador, obtido via `CrudUsuario.read(idUsuario)` e armazenado no campo transiente `autor` de `Curso`), descrição e data de início formatada (`SimpleDateFormat("dd/MM/yyyy")`). Oferece a opção `(A) Fazer minha inscrição no curso`.

### Gerenciamento de Inscrições (TP2)

- **Exibir minhas inscrições** (`ControleMinhasInscricoes.exibirMinhasInscricoes`): recupera todos os `CursoUsuario` do usuário logado via `CrudCursoUsuario.readAllByUsuario(idUsuario)` e exibe nome do curso, estado e data de inscrição.
- **Efetivar inscrição** (`ControleMinhasInscricoes.efetivarInscricao`): cria um novo `CursoUsuario` com data atual e chama `CrudCursoUsuario.create()`, atualizando as duas Árvores B+ de índice.
- **Cancelar inscrição** (`ControleMinhasInscricoes.cancelarInscricao`): localiza o registro de inscrição e chama `CrudCursoUsuario.delete()`.

---

## Persistência de Dados e Estruturas de Dados

### `Arquivo<T>` — base de todos os CRUDs

Gerencia um `RandomAccessFile` com a seguinte estrutura de registro:

```
[ lápide (1 byte) ][ tamanho (2 bytes / short) ][ vetor de bytes (variável) ]
```

- Lápide `' '` = válido; `'*'` = excluído (exclusão lógica).
- Cabeçalho: último ID (4 bytes) + ponteiro para lista de espaços excluídos (8 bytes).
- Registros excluídos entram em lista encadeada ordenada por tamanho, permitindo reuso.
- **Índice direto**: `HashExtensivel<ParIDEndereco>` garante acesso O(1) por ID.
- **`readAll()`** (TP2): varre sequencialmente o arquivo pulando lápides `'*'`, retornando todos os registros válidos.

### Índices de Usuários (`CrudUsuario`)

| Índice | Estrutura | Mapeamento |
|---|---|---|
| Direto | `HashExtensivel<ParIDEndereco>` | id → endereço físico |
| Indireto de email | `HashExtensivel<ParEmailID>` | hash(email) → id |

### Índices de Cursos (`CrudCurso`)

| Índice | Estrutura | Mapeamento |
|---|---|---|
| Direto | `HashExtensivel<ParIDEndereco>` | idCurso → endereço físico |
| Indireto de código | `HashExtensivel<ParCodigoID>` | hash(código) → idCurso |
| Relacionamento 1:N | `ArvoreBMais<ParIdUsuarioIdCurso>` | (idUsuario, idCurso) |
| Ordenação por nome | `ArvoreBMais<ParUsuarioNomeCursoId>` | (idUsuario, nome, idCurso) |

### Índices de Inscrições (`CrudCursoUsuario`) — TP2

| Índice | Estrutura | Mapeamento |
|---|---|---|
| Por curso | `ArvoreBMais<ParIdCursoIdCursoUsuario>` | (idCurso, idCursoUsuario) |
| Por usuário | `ArvoreBMais<ParIdUsuarioIdCursoUsuario>` | (idUsuario, idCursoUsuario) |

### Arquivos em Disco

```
dados/
├── usuarios/
│   ├── usuarios.db              # Registros binários
│   ├── usuarios.d.db / .c.db   # Índice direto (hash)
│   ├── indiceEmail.d.db        # Índice de email (diretório)
│   └── indiceEmail.c.db        # Índice de email (cestos)
├── cursos/
│   ├── cursos.db               # Registros binários
│   ├── cursos.d.db / .c.db     # Índice direto (hash)
│   ├── indiceCodigo.d.db / .c.db    # Índice de código NanoID
│   ├── arvoreUsuarioCurso.d.db      # Árvore B+ — relação 1:N
│   └── arvoreUsuarioNome.d.db       # Árvore B+ — ordenação por nome
└── cursoUsuario/                    # Criado automaticamente na primeira inscrição (TP2)
    ├── cursoUsuario.db              # Registros binários de inscrições
    ├── cursoUsuario.d.db / .c.db    # Índice direto (hash)
    ├── arvoreCursoInscritos.d.db    # Árvore B+ — inscritos por curso
    └── arvoreUsuarioInscritos.d.db  # Árvore B+ — inscrições por usuário
```

---

## Como Compilar e Executar

**Pré-requisitos:** Java 11+ e Maven instalados.

Os diretórios e arquivos de dados são criados automaticamente na primeira execução.

### Opção 1 — Compilar e executar em um só comando

```bash
mvn clean compile exec:java
```

### Opção 2 — Compilar e executar separadamente

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="Main"
```

### Opção 3 — Gerar `.jar` e executar

```bash
mvn clean package
java -jar target/pucminas-cc-2026-ti3-g02-tp1-1.0.0.jar
```

---

## Checklist — TP2

**1. Há um CRUD da entidade de associação CursoUsuario (que estende a classe ArquivoIndexado, acrescentando Tabelas Hash Extensíveis e Árvores B+ como índices diretos e indiretos conforme necessidade) que funciona corretamente?**

**Sim.** 

**2. A visão de inscrições está corretamente implementada e permite consultas aos cursos em que um usuário está inscrito?**

**Sim.** A

**3. A visão de cursos funciona corretamente e permite a gestão dos usuários inscritos em um curso?**

**Parcialmente.** A tela `MenuGerenciarInscritos` (acessível via `Meus Cursos → curso → Gerenciar inscritos`) existe e exibe a opção de exportar inscritos, mas a lógica de exportação ainda não está implementada.

**4. Há uma visualização dos cursos de outras pessoas por meio de um código NanoID?**

**Sim.** 

**5. A integridade do relacionamento entre cursos e usuários está mantida em todas as operações?**

**Sim** 

**6. O trabalho compila corretamente?**

**Sim.** 

**7. O trabalho está completo e funcionando sem erros de execução?**

**Sim, com ressalvas.** Todas as funcionalidades de busca e listagem de cursos (TP2) estão implementadas e funcionando: busca por NanoID, listagem paginada por data, tela de detalhes com autor, efetivar e cancelar inscrições. A exportação de inscritos em `MenuGerenciarInscritos` está pendente de implementação.

**8. O trabalho é original e não a cópia de um trabalho de outro grupo?**

**Sim.**

---

## Tecnologias Utilizadas

- **Linguagem:** Java 11
- **Build:** Maven
- **Dependência:** `jnanoid-enhanced` (geração de código NanoID de 10 caracteres)
- **Persistência:** `RandomAccessFile` com serialização binária via `DataOutputStream` / `DataInputStream`
- **Estruturas de dados customizadas:** `HashExtensivel` e `ArvoreBMais`
