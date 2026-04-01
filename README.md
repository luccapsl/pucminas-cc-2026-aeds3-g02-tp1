# PUC Minas - Ciência da Computação - AEDS III - TP1 - Grupo 02

## Descrição do Projeto

Este projeto implementa um sistema de gerenciamento de cursos e usuários, desenvolvido como Trabalho Prático 1 da disciplina Algoritmos e Estruturas de Dados III (AEDS III) da PUC Minas, ano 2026. O sistema permite o cadastro, consulta, atualização e exclusão de usuários e cursos, com suporte a relacionamentos 1:N entre usuários e cursos (um usuário pode gerenciar múltiplos cursos).

### Funcionalidades Principais

#### Gerenciamento de Usuários
- **Criação**: Cadastro de novos usuários com nome, email, senha (armazenada como hash), pergunta secreta e resposta secreta.
- **Leitura**: Consulta de usuários por ID ou email.
- **Atualização**: Modificação dos dados de um usuário existente.
- **Exclusão**: Remoção de usuários por ID ou email.

#### Gerenciamento de Cursos
- **Criação**: Cadastro de novos cursos com nome, descrição, data de início e estado (0: planejamento, 1: aberto para inscrições, 2: inscrições encerradas, 3: curso em andamento).
- **Leitura**: Consulta de cursos por ID ou código único (NanoID).
- **Listagem**: Visualização de todos os cursos de um usuário específico.
- **Ordenação**: Listagem de cursos de um usuário ordenados por nome.
- **Atualização**: Modificação dos dados de um curso existente.
- **Exclusão**: Remoção de cursos por ID ou código.
- **Mudanças de Estado**: Métodos para abrir inscrições, encerrar inscrições e iniciar o curso.

#### Relacionamentos e Índices
- **Relacionamento 1:N**: Cada curso está associado a um usuário (instrutor).
- **Índices de Busca**:
  - Hash Extensível para busca rápida por email (usuários) e código (cursos).
  - Árvores B+ para relacionamentos usuário-curso e listagens ordenadas por nome.

### Estrutura do Projeto

```
pucminas-cc-2026-ti3-g02-tp1/
├── pom.xml                          # Configuração Maven
├── README.md                        # Este arquivo
├── Entidades/                       # Classes de entidade
│   ├── Usuario.java                 # Entidade Usuário
│   └── Curso.java                   # Entidade Curso
├── CRUD/                            # Classes de operações CRUD
│   ├── CrudUsuario.java             # Operações CRUD para usuários
│   └── CrudCurso.java               # Operações CRUD para cursos
├── Genericos/                       # Classes genéricas e estruturas de dados
│   ├── Arquivo.java                 # Gerenciamento de arquivos binários
│   ├── HashExtensivel.java          # Implementação de Hash Extensível
│   ├── ArvoreBMais.java             # Implementação de Árvore B+
│   ├── Registro.java                # Interface para registros
│   ├── RegistroArvoreBMais.java     # Interface para registros em árvore B+
│   ├── RegistroHashExtensivel.java  # Interface para registros em hash
│   ├── ParEmailID.java              # Par chave-valor para índice de email
│   ├── ParCodigoID.java             # Par chave-valor para índice de código
│   ├── ParIDEndereco.java           # Par chave-valor para índice direto
│   ├── ParIdUsuarioIdCurso.java     # Par para relacionamento usuário-curso
│   └── ParUsuarioNomeCursoId.java   # Par para ordenação por nome
└── Principal/                       # (Diretório vazio - possível ponto de entrada futuro)
```

### Tecnologias Utilizadas

- **Linguagem**: Java 11
- **Build Tool**: Maven
- **Dependências**:
  - NanoID (para geração de códigos únicos de cursos)
- **Estruturas de Dados**:
  - Hash Extensível (para índices indiretos)
  - Árvore B+ (para relacionamentos e ordenação)
- **Persistência**: Arquivos binários com mecanismo de lápide para exclusão lógica

### Como Compilar e Executar

1. **Pré-requisitos**:
   - Java 11 ou superior instalado
   - Maven instalado

2. **Compilação**:
   ```bash
   mvn clean compile
   ```

3. **Estrutura de Diretórios**:
   O sistema cria automaticamente os diretórios necessários para armazenar os arquivos de dados:
   - `dados/usuarios/` - Arquivos de usuários e índices
   - `dados/cursos/` - Arquivos de cursos e índices

### Arquitetura e Design

#### Persistência de Dados
- Utiliza `RandomAccessFile` para leitura/escrita eficiente em arquivos binários.
- Implementa lápide (byte indicador de exclusão) para exclusão lógica.
- Cada registro tem tamanho fixo para acesso direto por posição.

#### Índices
- **Diretos**: Mapeiam ID para endereço no arquivo (não implementados neste projeto, mas suportados pela arquitetura).
- **Indiretos**: Mapeiam chaves de busca (email, código) para ID, usando Hash Extensível.
- **Relacionais**: Árvores B+ para relacionamentos 1:N e ordenação.

#### Segurança
- Senhas armazenadas como hash (usando `hashCode()` do Java).
- Sistema de pergunta/resposta secreta para recuperação de conta.

### Grupo
- **Instituição**: PUC Minas - Ciência da Computação
- **Disciplina**: Algoritmos e Estruturas de Dados III
- **Ano**: 2026
- **Grupo**: 02
- **Membros**: Daniel Santos, Daniel Rocha, Lucca Lopes, Rafael Henrique Silva

### Observações
- O projeto foi desenvolvido seguindo as especificações do enunciado do TP1.
- Todas as classes estão devidamente documentadas em português.
- O sistema é compatível com Windows (caminhos usando `\\`).