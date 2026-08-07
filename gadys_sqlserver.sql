-- ============================================================
-- GADYS - Script SQL Server
-- Gerado com base nos models do backend Spring Boot
-- ============================================================

-- ============================================================
-- Execute este script conectado diretamente ao banco 'gadys'
-- (o usuário não precisa de permissão em master)
-- ============================================================

-- ============================================================
-- TABELAS PRINCIPAIS
-- ============================================================

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Usuario' AND xtype='U')
CREATE TABLE Usuario (
    id              BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome            NVARCHAR(100)   NOT NULL,
    email           NVARCHAR(150)   NOT NULL UNIQUE,
    senha           NVARCHAR(255)   NOT NULL,
    tipo_usuario    NVARCHAR(10)    DEFAULT 'USUARIO',   -- USUARIO | ADM
    status_usuario  NVARCHAR(10)    DEFAULT 'ATIVO',
    ultimo_acesso   DATETIME2,
    total_acesso    INT             DEFAULT 0,
    ip_acesso       NVARCHAR(45),
    foto_perfil     NVARCHAR(500),
    data_cadastro   DATETIME2       DEFAULT GETDATE()
);
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Localizacao' AND xtype='U')
CREATE TABLE Localizacao (
    id                      BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome                    NVARCHAR(200)   NOT NULL,
    descricao               NVARCHAR(MAX),
    categoria               NVARCHAR(20)    NOT NULL,
    subcategoria            NVARCHAR(30)    NOT NULL,
    cidade                  NVARCHAR(100),
    estado                  NVARCHAR(100),
    endereco                NVARCHAR(MAX),
    coordenadas             NVARCHAR(50),
    horario_funcionamento   NVARCHAR(255),
    preco                   NVARCHAR(100),
    informacoes_adicionais  NVARCHAR(MAX),
    imagem_url              NVARCHAR(500),
    rota_frontend           NVARCHAR(200),
    status                  NVARCHAR(10)    DEFAULT 'PENDENTE',  -- PENDENTE | ATIVO | INATIVO
    enviado_por             NVARCHAR(100),
    criado_por              BIGINT          REFERENCES Usuario(id),
    data_criacao            DATETIME2       DEFAULT GETDATE(),
    data_aprovacao          DATETIME2,
    aprovado_por            BIGINT          REFERENCES Usuario(id)
);
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Avaliacao' AND xtype='U')
CREATE TABLE Avaliacao (
    id              BIGINT IDENTITY(1,1) PRIMARY KEY,
    Localizacao_id  BIGINT      NOT NULL REFERENCES Localizacao(id) ON DELETE CASCADE,
    usuario_id      BIGINT      NOT NULL REFERENCES Usuario(id),
    nota            INT         NOT NULL CHECK (nota BETWEEN 1 AND 5),
    data_avaliacao  DATETIME2   DEFAULT GETDATE(),
    CONSTRAINT UQ_Avaliacao_Local_Usuario UNIQUE (Localizacao_id, usuario_id)
);
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Comentario' AND xtype='U')
CREATE TABLE Comentario (
    id              BIGINT IDENTITY(1,1) PRIMARY KEY,
    Localizacao_id  BIGINT          NOT NULL REFERENCES Localizacao(id) ON DELETE CASCADE,
    usuario_id      BIGINT          NOT NULL REFERENCES Usuario(id),
    texto           NVARCHAR(MAX)   NOT NULL,
    data_comentario DATETIME2       DEFAULT GETDATE()
);
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Favorito' AND xtype='U')
CREATE TABLE Favorito (
    id              BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id      BIGINT      NOT NULL REFERENCES Usuario(id),
    Localizacao_id  BIGINT      NOT NULL REFERENCES Localizacao(id) ON DELETE CASCADE,
    data_criacao    DATETIME2   DEFAULT GETDATE(),
    CONSTRAINT UQ_Favorito_Usuario_Local UNIQUE (usuario_id, Localizacao_id)
);
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Categoria' AND xtype='U')
CREATE TABLE Categoria (
    id      BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome    NVARCHAR(100) NOT NULL
);
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='categoria_estados' AND xtype='U')
CREATE TABLE categoria_estados (
    categoria_id    BIGINT          NOT NULL REFERENCES Categoria(id) ON DELETE CASCADE,
    estado          NVARCHAR(2)     NOT NULL
);
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Sugestao' AND xtype='U')
CREATE TABLE Sugestao (
    id                  BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome                NVARCHAR(255),
    descricao           NVARCHAR(MAX),
    endereco            NVARCHAR(255),
    estado              NVARCHAR(2),
    subcategoria        NVARCHAR(255),
    imagem_url          NVARCHAR(255),
    enviado_por         NVARCHAR(255),
    usuario_id          INT,
    status              NVARCHAR(20)    DEFAULT 'PENDENTE',
    categoria_custom    NVARCHAR(100),
    rascunho_conteudo   NVARCHAR(MAX),
    data_criacao        DATETIME2       DEFAULT GETDATE()
);
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='MensagemContato' AND xtype='U')
CREATE TABLE MensagemContato (
    id       BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome     NVARCHAR(100)   NOT NULL,
    email    NVARCHAR(150)   NOT NULL,
    assunto  NVARCHAR(200),
    mensagem NVARCHAR(MAX)   NOT NULL,
    resposta NVARCHAR(MAX),
    status   NVARCHAR(15)    DEFAULT 'nova',
    data     DATETIME2       DEFAULT GETDATE()
);
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='PasswordResetToken' AND xtype='U')
CREATE TABLE PasswordResetToken (
    id         BIGINT IDENTITY(1,1) PRIMARY KEY,
    email      NVARCHAR(150)   NOT NULL,
    token      NVARCHAR(255)   NOT NULL UNIQUE,
    expiracao  DATETIME2       NOT NULL
);
GO

-- ============================================================
-- DADOS INICIAIS (espelha o DataInitializer.java)
-- Senha '123456' hasheada com BCrypt (custo 10)
-- ============================================================

IF NOT EXISTS (SELECT 1 FROM Usuario WHERE email = 'admin@gadys.com')
    INSERT INTO Usuario (nome, email, senha, tipo_usuario)
    VALUES ('Administrador', 'admin@gadys.com',
            '$2b$10$V9oOs1z9xanpZn30kd906uwte03ql4eiJA4T.sKIRVthlj7tFoZMy',
            'ADM');
GO

IF NOT EXISTS (SELECT 1 FROM Usuario WHERE email = 'usuario@gadys.com')
    INSERT INTO Usuario (nome, email, senha, tipo_usuario)
    VALUES ('Usuário Teste', 'usuario@gadys.com',
            '$2b$10$V9oOs1z9xanpZn30kd906uwte03ql4eiJA4T.sKIRVthlj7tFoZMy',
            'USUARIO');
GO

IF NOT EXISTS (SELECT 1 FROM Usuario WHERE email = 'gadys2026@gmail.com')
    INSERT INTO Usuario (nome, email, senha, tipo_usuario)
    VALUES ('Admin GADYS', 'gadys2026@gmail.com',
            '$2b$10$V9oOs1z9xanpZn30kd906uwte03ql4eiJA4T.sKIRVthlj7tFoZMy',
            'ADM');
GO
