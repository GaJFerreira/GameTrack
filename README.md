**Nome do Projeto:** GameTrack

**Alunos:**

&#x20;Gabriel Jose ferreira Nunes Ribeiro

Vinícius Rodrigues Rocha

Gabriel Barbosa Dos Santos

---

### Escopo Preliminar e Análise de Stakeholders

**Definição clara do escopo preliminar**
O GameTrack será um aplicativo desenvolvido em Android Studio com Java, destinado a usuários que desejam organizar suas bibliotecas de jogos digitais, com foco inicial em integração com a Steam. O escopo inicial inclui funcionalidades de cadastro manual e automático de jogos, acompanhamento de progresso, definição de metas, cronograma de jogatina e visualização de estatísticas básicas. O projeto não contemplará, nesta fase, integração com outras plataformas além da Steam, nem recursos colaborativos ou sociais.

**Identificação de stakeholders principais**

* **Usuário final**: Jogadores que desejam organizar e acompanhar seu backlog de jogos.
* **Equipe de desenvolvimento**: Responsável por implementar, testar e documentar o aplicativo.
* **Plataformas externas**: API pública da Steam como fonte de dados para importação automática.

**Descrição dos requisitos básicos**

* Cadastro e autenticação de usuário
* Cadastro manual de jogos
* Importação automática de jogos via Steam ID
* Registro de status, progresso, conquistas e metas por jogo
* Interface intuitiva com filtros e visualização por plataforma, status e interesse
* Armazenamento local ou em nuvem leve

---

### Apresentação Detalhada do Projeto

**Problema a ser resolvido**
A chamada “síndrome do PC gamer” refere-se ao acúmulo de jogos digitais não jogados, que gera ansiedade, sensação de desperdício e dificuldade de organização. Com bibliotecas virtuais crescentes e a facilidade de aquisição por promoções, muitos jogadores perdem o controle sobre o que possuem ou jogam, criando um ciclo improdutivo de consumo e abandono.

**Objetivos do projeto**
O GameTrack tem como objetivo fornecer uma plataforma que ajude jogadores a organizarem e acompanharem suas bibliotecas digitais de forma consciente e estruturada. A proposta é facilitar o planejamento das jogatinas, promover o uso saudável do tempo de lazer e combater o acúmulo descontrolado através da criação de cronogramas de jogatina, aquisição de novos jogos e cumprimento de objetivos dentro dos jogos. O projeto também busca integrar-se com a API da Steam para automatizar parte da coleta de dados, reduzindo o esforço manual do usuário.

**Variáveis principais envolvidas**

* Nome e plataforma dos jogos
* Status de progresso (não iniciado, jogando, finalizado, pausado, abandonado)
* Data de compra, tempo jogado
* Número de conquistas e progresso em conquistas
* Grau de interesse e nota pessoal
* Metas individuais de jogatina (tempo ou conclusão)
* Dados importados via API (Steam ID, biblioteca, tempo total, conquistas)

**Limitações e condições de contorno**
Na versão inicial, a integração será limitada à API pública da Steam, listando os títulos presentes na plataforma. Dados de outras plataformas (como Epic Games, GOG ou consoles) deverão ser inseridos manualmente. A funcionalidade será centrada em uso individual, sem componentes sociais ou gamificação externa, priorizando clareza, autonomia e usabilidade.

---

### Requisitos Funcionais e Não Funcionais

**Funcionalidade: Cadastro e Edição Manual de Jogos**
**Objetivo principal:** Permitir ao usuário adicionar e gerenciar jogos em sua biblioteca que não são provenientes da Steam ou que ele deseja inserir manualmente.

🎯 **Requisitos Funcionais (RF) para Cadastro e Edição Manual:**

* **RF\_Manual\_001:** O sistema deve permitir que o usuário adicione um novo jogo manualmente à sua biblioteca local.
  Campos mínimos: Nome do Jogo, Plataforma.
  Campos opcionais sugeridos: Data de Compra, Loja onde foi adquirido, Gênero, Desenvolvedor, Publisher, Nota Pessoal, Grau de Interesse.
* **RF\_Manual\_002:** O sistema deve permitir que o usuário edite as informações de um jogo previamente cadastrado manualmente.
* **RF\_Manual\_003:** O sistema deve permitir que o usuário remova um jogo da sua biblioteca local (que foi cadastrado manualmente).
* **RF\_Manual\_004:** O sistema deve listar os jogos cadastrados na biblioteca local, exibindo informações chave (como nome, plataforma, status).

📝 **User Story (Exemplo):**
"Como um jogador, eu quero poder adicionar manualmente os jogos que possuo em diversas plataformas, informando detalhes que considero importantes, para ter minha biblioteca completa e organizada no GameTrack."

✨ **Requisitos Não Funcionais (RNF) Associados:**

* **RNF\_Manual\_001 (Usabilidade):** A interface para adicionar e editar jogos manualmente deve ser clara, intuitiva e fácil de usar.
* **RNF\_Manual\_002 (Persistência de Dados):** As informações dos jogos cadastrados manualmente devem ser salvas localmente no dispositivo de forma persistente.
* **RNF\_Manual\_003 (Desempenho):** A exibição da lista de jogos, incluindo os manuais, deve ser rápida.

**Funcionalidade: Importação e Listagem de Jogos da Steam**
**Objetivo principal:** Facilitar a adição de jogos que o usuário já possui na Steam, reduzindo o cadastro manual e integrando dados relevantes.

🎯 **Requisitos Funcionais (RF) para Importação da Steam:**

* **RF\_Steam\_009:** Todos os jogos importados da Steam devem ser adicionados automaticamente à biblioteca local padrão do usuário.
* **RF\_Steam\_001:** O sistema deve permitir que o usuário insira seu SteamID (ou nome de perfil público Steam) para buscar seus jogos.
* **RF\_Steam\_002:** O sistema deve se conectar à API da Steam para buscar a lista de jogos associados ao SteamID fornecido.
* **RF\_Steam\_003:** O sistema deve exibir a lista de jogos obtidos da Steam para o usuário (com nome do jogo e, idealmente, uma imagem de capa se disponível pela API).
* **RF\_Steam\_004:** O sistema deve permitir que o usuário selecione múltiplos jogos da lista importada da Steam para adicioná-los à sua biblioteca local no GameTrack.
* **RF\_Steam\_005:** Ao adicionar um jogo da Steam, o sistema deve pré-preencher automaticamente o nome do jogo e, se possível e desejado, outros dados como tempo total jogado (via Steam).
* **RF\_Steam\_006:** O sistema deve informar ao usuário caso o perfil Steam seja privado ou se ocorrer um erro ao acessar a API da Steam, com instruções básicas de como proceder (ex: tornar o perfil público).
* **RF\_Steam\_007:** O sistema deve ter um mecanismo para evitar a duplicação de jogos na biblioteca local ao importar da Steam (ex: checando pelo AppID da Steam).
* **RF\_Steam\_008 (Opcional):** O sistema pode oferecer uma opção para sincronizar/atualizar periodicamente os jogos da Steam (novos jogos, tempo jogado).

📝 **User Story (Exemplo):**
"Como um jogador com uma vasta biblioteca na Steam, eu quero poder importar meus jogos para o GameTrack de forma simples, fornecendo meu ID Steam, para que eu não precise cadastrá-los um a um e possa começar a organizá-los rapidamente."

✨ **Requisitos Não Funcionais (RNF) Associados:**

* **RNF\_Steam\_001 (Desempenho):** A busca e listagem de jogos da Steam deve ser concluída em um tempo razoável (ex: < 15 segundos para uma biblioteca grande).
* **RNF\_Steam\_002 (Usabilidade):** O processo de inserir o SteamID, visualizar a lista e selecionar jogos deve ser claro e intuitivo.
* **RNF\_Steam\_003 (Feedback ao Usuário):** O sistema deve fornecer feedback claro durante o processo de importação (ex: "Buscando jogos...", "X jogos encontrados", "Jogos adicionados com sucesso", "Erro ao conectar à Steam").
* **RNF\_Steam\_004 (Conectividade):** O sistema deve lidar adequadamente com a ausência de conexão à internet durante a tentativa de importação da Steam.

---

### Funcionalidades Complementares Simples

**Funcionalidade: Metas de Jogatina**
**Objetivo principal:** Permitir ao usuário definir metas pessoais de tempo ou conclusão para seus jogos, com acompanhamento básico de progresso.

🎯 **Requisitos Funcionais (RF):**

* **RF\_Meta\_001:** O sistema deve permitir que o usuário defina uma meta de tempo de jogo para um título específico (ex: jogar 10h até uma data).
* **RF\_Meta\_002:** O sistema deve mostrar ao usuário o progresso em relação à meta definida.

📝 **User Story (Exemplo):**
"Como um jogador, quero definir metas de tempo de jogo para meus títulos, para organizar melhor minha rotina de jogatina e me manter motivado."

✨ **Requisitos Não Funcionais (RNF):**

* **RNF\_Meta\_001 (Usabilidade):** A interface para definição e visualização das metas deve ser simples e objetiva.
* **RNF\_Meta\_002 (Persistência):** As metas devem ser salvas no dispositivo local de forma segura.

**Funcionalidade: Visualização de Estatísticas Básicas**
**Objetivo principal:** Permitir que o usuário tenha uma visão geral de sua biblioteca por status e plataforma.

🎯 **Requisitos Funcionais (RF):**

* **RF\_Stats\_001:** O sistema deve apresentar contagens de jogos por status (jogando, não iniciado, finalizado etc.).
* **RF\_Stats\_002:** O sistema deve apresentar contagens por plataforma (Steam, manual, etc.).

📝 **User Story (Exemplo):**
"Como um jogador, quero visualizar estatísticas básicas da minha biblioteca, como quantidade de jogos jogando ou finalizados, para entender melhor meu backlog."

✨ **Requisitos Não Funcionais (RNF):**

* **RNF\_Stats\_001 (Desempenho):** A geração dessas estatísticas deve ser quase instantânea.
* **RNF\_Stats\_002 (Clareza Visual):** As estatísticas devem ser exibidas em formato simples e compreensível.

---

### Modelagem de Classes

| Classe                   | Atributos                                                                                                                                                                                                                                     | Relacionamentos                                |                |
| ------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------- | -------------- |
| **Usuario**              | id, nome, email, senha, steamId                                                                                                                                                                                                               | 1\:N com `Biblioteca`                          |                |
| **Jogo**                 | id, nome, plataforma, appIdSteam (nullable), dataCompra, loja, genero, desenvolvedor, publisher, descricao, imagemCapa, icone, interesse, status, tipoCadastro (manual/steam), notaPessoal, horasJogadas, conquistasTotais, conquistasObtidas | N:1 com `Biblioteca`  1\:N com `Meta`          |                |
| **Biblioteca**           | id, nome, tipo (manual/steam), usuarioId                                                                                                                                                                                                      | N:1 com `Usuario`  1\:N com `Jogo`             |                |
| **Meta**                 | id, jogoId, tipo (TEMPO/CONCLUSAO), valorMeta, progressoAtual, dataLimite, prioridade (BAIXA/MEDIA/ALTA), observacoes, status (EM\_ANDAMENTO/CONCLUIDA/EXPIRADA)                                                                              | N:1 com `Jogo`                                 | N:1 com `Jogo` |
| **Estatistica (lógica)** | jogosPorStatus, jogosPorPlataforma (gerados dinamicamente)                                                                                                                                                                                    | Sem persistência, calculada com base em `Jogo` |                |

---

### Classe Complementar: Cronograma

| Classe         | Atributos                                                                                                                   | Relacionamentos |
| -------------- | --------------------------------------------------------------------------------------------------------------------------- | --------------- |
| **Cronograma** | id, jogoId, data, horaInicio, horaFim, descricao, repetir (NUNCA/DIARIAMENTE/SEMANALMENTE/MENSALMENTE), notificar (boolean) | N:1 com `Jogo`  |

**Objetivo principal:** Permitir ao usuário planejar sessões de jogo, definindo dias, horários e frequência para facilitar o cumprimento das metas e organizar o tempo de lazer.

🎯 **Requisitos Funcionais (RF):**

* **RF\_Crono\_001:** O sistema deve permitir agendar sessões de jogo com data e horário definido.
* **RF\_Crono\_002:** O sistema deve permitir descrever o objetivo da sessão (ex: “explorar mapa”, “concluir missão principal”).
* **RF\_Crono\_003:** O sistema deve permitir configurar a repetição da sessão (diária, semanal, etc.).
* **RF\_Crono\_004:** O sistema deve permitir ativar ou desativar notificações locais.

✨ **Requisitos Não Funcionais (RNF):**

* **RNF\_Crono\_001 (Usabilidade):** A visualização do cronograma deve ser clara, com opção de lista ou calendário.
* **RNF\_Crono\_002 (Persistência):** As sessões devem ser armazenadas localmente de forma confiável.
* **RNF\_Crono\_003 (Desempenho):** O carregamento do cronograma deve ser imediato.

---

### Arquitetura do Sistema

O GameTrack será desenvolvido como um aplicativo Android nativo utilizando Java e a arquitetura em camadas. A estrutura será organizada de forma a separar as responsabilidades de interface, lógica de negócios e persistência de dados.

**Camadas e Componentes:**

1. **Camada de Apresentação (UI)**

   * Implementada em XML e Java via Android Studio
   * Responsável por exibir as telas (lista de jogos, metas, cronograma, importação Steam)
   * Interage com os `ViewModels` ou `Controllers` para obter/atualizar dados

2. **Camada de Aplicação (Serviços/Controladores)**

   * Classes Java que processam a lógica de negócio
   * Responsáveis por validar dados, aplicar regras (ex: verificação de duplicatas, atualização de metas)
   * Intermediam a comunicação entre UI e persistência

3. **Camada de Dados (Modelos e Banco)**

   * Classes modelo: `Usuario`, `Jogo`, `Meta`, `Cronograma`, `Biblioteca`
   * Utilização do SQLite para armazenamento local

4. **Integração Externa (Steam API)**

   * Requisições HTTP para buscar jogos por `SteamID`
   * Conversão de JSON em objetos Java

**Fluxo Simplificado:**

* Usuário → UI → Serviço → Banco de Dados (ou API Steam) → UI

**Considerações Técnicas:**

* O app funcionará offline para dados locais (biblioteca, metas, cronogramas)
* A API da Steam exige conexão ativa e perfil público
* As camadas são desacopladas para facilitar manutenção e testes

---

### Fluxos de Uso

A seguir estão os principais fluxos de uso do aplicativo GameTrack, descritos em termos de ações do usuário e respostas do sistema:

**Fluxo 1: Cadastro Manual de Jogo**

1. Usuário acessa a tela "Adicionar Jogo Manual".
2. Preenche campos obrigatórios (nome, plataforma) e, se desejar, os opcionais.
3. Clica em "Salvar".
4. O sistema valida e adiciona o jogo à biblioteca local.

**Fluxo 2: Importação de Jogos da Steam**

1. Usuário acessa a tela "Importar da Steam".
2. Informa seu SteamID ou nome de perfil público.
3. Clica em "Buscar Jogos".
4. O sistema acessa a API da Steam, busca os jogos e exibe em lista.
5. Usuário seleciona jogos desejados.
6. O sistema adiciona os jogos à biblioteca local, evitando duplicatas.

**Fluxo 3: Definição de Meta de Jogatina**

1. Usuário acessa o detalhe de um jogo.
2. Seleciona "Criar Meta".
3. Escolhe tipo (tempo/conclusão), valor e data limite.
4. Clica em "Salvar Meta".
5. O sistema vincula a meta ao jogo e começa a monitorar o progresso.

**Fluxo 4: Agendamento de Sessão de Jogo (Cronograma)**

1. Usuário acessa a aba "Cronograma".
2. Clica em "Adicionar Sessão".
3. Escolhe jogo, define data, horário, repetição e observações.
4. Clica em "Salvar".
5. O sistema adiciona a sessão ao cronograma e agenda notificação (se ativado).

**Fluxo 5: Consulta de Estatísticas**

1. Usuário acessa a aba "Estatísticas".
2. O sistema gera e exibe gráficos de jogos por status e por plataforma.

**Fluxo 6: Atualização de Progresso**

1. Usuário acessa a lista de jogos ou detalhes de um jogo.
2. Atualiza manualmente o status, conquistas ou horas jogadas.
3. O sistema salva os novos dados e recalcula metas se necessário.

---

###
