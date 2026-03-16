const express = require('express');
const sqlite3 = require('sqlite3').verbose();
const cors = require('cors');
const path = require('path');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3001;

// Middleware
app.use(cors());
app.use(express.json());

// Conectar ao banco SQLite
const dbPath = path.join(__dirname, '../database/gadys.db');
const db = new sqlite3.Database(dbPath, (err) => {
    if (err) {
        console.error('❌ Erro ao conectar ao SQLite:', err);
    } else {
        console.log('✅ Conectado ao SQLite');
        console.log(`🗄️ Banco: ${dbPath}`);
        initializeDatabase();
    }
});

// Inicializar estrutura do banco
function initializeDatabase() {
    // Criar tabelas se não existirem
    const tables = [
        `CREATE TABLE IF NOT EXISTS Usuarios (
            ID INTEGER PRIMARY KEY AUTOINCREMENT,
            Nome TEXT NOT NULL,
            Email TEXT UNIQUE NOT NULL,
            Senha TEXT NOT NULL,
            TipoUsuario TEXT CHECK (TipoUsuario IN ('usuario', 'adm')) DEFAULT 'usuario',
            UltimoAcesso DATETIME,
            TotalAcessos INTEGER DEFAULT 0,
            DataCadastro DATETIME DEFAULT CURRENT_TIMESTAMP
        )`,
        
        `CREATE TABLE IF NOT EXISTS Estados (
            ID INTEGER PRIMARY KEY AUTOINCREMENT,
            Nome TEXT NOT NULL,
            Sigla TEXT UNIQUE NOT NULL
        )`,
        
        `CREATE TABLE IF NOT EXISTS Cidades (
            ID INTEGER PRIMARY KEY AUTOINCREMENT,
            Nome TEXT NOT NULL,
            EstadoID INTEGER,
            FOREIGN KEY (EstadoID) REFERENCES Estados(ID)
        )`,
        
        `CREATE TABLE IF NOT EXISTS Categorias (
            ID INTEGER PRIMARY KEY AUTOINCREMENT,
            Nome TEXT NOT NULL,
            Icone TEXT,
            Cor TEXT
        )`,
        
        `CREATE TABLE IF NOT EXISTS Locais (
            ID INTEGER PRIMARY KEY AUTOINCREMENT,
            Nome TEXT NOT NULL,
            Descricao TEXT,
            CidadeID INTEGER,
            CategoriaID INTEGER,
            HorarioFuncionamento TEXT,
            Preco TEXT,
            InformacoesAdicionais TEXT,
            Status TEXT CHECK (Status IN ('ativo', 'inativo', 'pendente')) DEFAULT 'ativo',
            CriadoPor INTEGER,
            DataCriacao DATETIME DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (CidadeID) REFERENCES Cidades(ID),
            FOREIGN KEY (CategoriaID) REFERENCES Categorias(ID),
            FOREIGN KEY (CriadoPor) REFERENCES Usuarios(ID)
        )`,
        
        `CREATE TABLE IF NOT EXISTS Avaliacoes (
            ID INTEGER PRIMARY KEY AUTOINCREMENT,
            LocalID INTEGER NOT NULL,
            UsuarioID INTEGER NOT NULL,
            Nota INTEGER CHECK (Nota >= 1 AND Nota <= 5),
            DataAvaliacao DATETIME DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (LocalID) REFERENCES Locais(ID),
            FOREIGN KEY (UsuarioID) REFERENCES Usuarios(ID),
            UNIQUE(LocalID, UsuarioID)
        )`,
        
        `CREATE TABLE IF NOT EXISTS Comentarios (
            ID INTEGER PRIMARY KEY AUTOINCREMENT,
            LocalID INTEGER NOT NULL,
            UsuarioID INTEGER NOT NULL,
            Texto TEXT NOT NULL,
            DataComentario DATETIME DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (LocalID) REFERENCES Locais(ID),
            FOREIGN KEY (UsuarioID) REFERENCES Usuarios(ID)
        )`
    ];
    
    tables.forEach(sql => {
        db.run(sql, (err) => {
            if (err) console.error('Erro ao criar tabela:', err);
        });
    });
    
    // Inserir dados básicos se não existirem
    insertBasicData();
}

function insertBasicData() {
    // Inserir usuário admin se não existir
    db.get("SELECT COUNT(*) as count FROM Usuarios WHERE TipoUsuario = 'adm'", (err, row) => {
        if (!err && row.count === 0) {
            db.run(`INSERT INTO Usuarios (Nome, Email, Senha, TipoUsuario) 
                    VALUES ('Yasmin', 'yasmincunegundes25@gmail.com', 'Cun*1925', 'adm')`);
        }
    });
    
    // Inserir categorias básicas se não existirem
    db.get("SELECT COUNT(*) as count FROM Categorias", (err, row) => {
        if (!err && row.count === 0) {
            const categorias = [
                ['Monumentos', '🏛️', '#8b4513'],
                ['Natureza', '🌳', '#228b22'],
                ['Gastronomia', '🍽️', '#ff8c00'],
                ['Cultura', '🎨', '#800080'],
                ['Praias', '🏖️', '#4169e1'],
                ['Religioso', '⛪', '#daa520']
            ];
            
            categorias.forEach(([nome, icone, cor]) => {
                db.run("INSERT INTO Categorias (Nome, Icone, Cor) VALUES (?, ?, ?)", [nome, icone, cor]);
            });
        }
    });
    
    // Inserir alguns estados se não existirem
    db.get("SELECT COUNT(*) as count FROM Estados", (err, row) => {
        if (!err && row.count === 0) {
            const estados = [
                ['Amazonas', 'AM'],
                ['São Paulo', 'SP'],
                ['Rio de Janeiro', 'RJ'],
                ['Bahia', 'BA'],
                ['Ceará', 'CE']
            ];
            
            estados.forEach(([nome, sigla]) => {
                db.run("INSERT INTO Estados (Nome, Sigla) VALUES (?, ?)", [nome, sigla]);
            });
        }
    });
}

// =============================================
// ROTA DE TESTE
// =============================================
app.get('/api/test', (req, res) => {
    res.json({ 
        success: true, 
        message: 'CRUD GADYS funcionando!',
        database: 'SQLite',
        timestamp: new Date().toISOString(),
        status: 'ONLINE ✅'
    });
});

// =============================================
// ROTAS DE USUÁRIOS
// =============================================

// Listar usuários
app.get('/api/usuarios', (req, res) => {
    db.all(`SELECT ID, Nome, Email, TipoUsuario, UltimoAcesso, TotalAcessos, DataCadastro 
            FROM Usuarios ORDER BY DataCadastro DESC`, (err, rows) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else {
            res.json(rows);
        }
    });
});

// Adicionar usuário
app.post('/api/usuarios', (req, res) => {
    const { nome, email, senha, tipoUsuario } = req.body;
    
    db.run(`INSERT INTO Usuarios (Nome, Email, Senha, TipoUsuario) 
            VALUES (?, ?, ?, ?)`, [nome, email, senha, tipoUsuario], function(err) {
        if (err) {
            if (err.message.includes('UNIQUE constraint failed')) {
                res.status(400).json({ error: 'Email já cadastrado!' });
            } else {
                res.status(500).json({ error: err.message });
            }
        } else {
            res.json({ success: true, message: 'Usuário cadastrado com sucesso!', id: this.lastID });
        }
    });
});

// Login
app.post('/api/login', (req, res) => {
    const { email, senha, tipoUsuario } = req.body;
    
    db.get(`SELECT ID, Nome, Email, TipoUsuario 
            FROM Usuarios 
            WHERE Email = ? AND Senha = ? AND TipoUsuario = ?`, 
            [email, senha, tipoUsuario], (err, row) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else if (row) {
            // Atualizar último acesso
            db.run(`UPDATE Usuarios 
                    SET UltimoAcesso = CURRENT_TIMESTAMP, TotalAcessos = TotalAcessos + 1 
                    WHERE ID = ?`, [row.ID]);
            
            res.json({ success: true, user: row });
        } else {
            res.status(401).json({ error: 'Credenciais inválidas!' });
        }
    });
});

// =============================================
// ROTAS DE LOCAIS
// =============================================

// Listar locais
app.get('/api/locais', (req, res) => {
    db.all(`SELECT l.*, c.Nome as Categoria, cid.Nome as Cidade, e.Nome as Estado, e.Sigla as EstadoSigla
            FROM Locais l
            LEFT JOIN Categorias c ON l.CategoriaID = c.ID
            LEFT JOIN Cidades cid ON l.CidadeID = cid.ID
            LEFT JOIN Estados e ON cid.EstadoID = e.ID
            WHERE l.Status = 'ativo'
            ORDER BY l.Nome`, (err, rows) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else {
            res.json(rows);
        }
    });
});

// Adicionar local
app.post('/api/locais', (req, res) => {
    const { nome, descricao, cidadeId, categoriaId, criadoPor, horarioFuncionamento, preco, informacoesAdicionais } = req.body;
    
    db.run(`INSERT INTO Locais (Nome, Descricao, CidadeID, CategoriaID, CriadoPor, HorarioFuncionamento, Preco, InformacoesAdicionais) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)`, 
            [nome, descricao, cidadeId, categoriaId, criadoPor, horarioFuncionamento, preco, informacoesAdicionais], 
            function(err) {
        if (err) {
            res.status(500).json({ error: err.message });
        } else {
            res.json({ success: true, message: 'Local cadastrado com sucesso!', id: this.lastID });
        }
    });
});

// Listar categorias
app.get('/api/categorias', (req, res) => {
    db.all("SELECT * FROM Categorias ORDER BY Nome", (err, rows) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else {
            res.json(rows);
        }
    });
});

// Listar estados
app.get('/api/estados', (req, res) => {
    db.all("SELECT * FROM Estados ORDER BY Nome", (err, rows) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else {
            res.json(rows);
        }
    });
});

// Listar cidades por estado
app.get('/api/cidades/:estadoId', (req, res) => {
    const { estadoId } = req.params;
    db.all("SELECT * FROM Cidades WHERE EstadoID = ? ORDER BY Nome", [estadoId], (err, rows) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else {
            res.json(rows);
        }
    });
});

// Excluir usuário
app.delete('/api/usuarios/:id', (req, res) => {
    const { id } = req.params;
    
    db.run("DELETE FROM Usuarios WHERE ID = ?", [id], function(err) {
        if (err) {
            res.status(500).json({ error: err.message });
        } else {
            res.json({ success: true, message: 'Usuário excluído!', changes: this.changes });
        }
    });
});

// =============================================
// ROTAS DE AVALIAÇÕES E COMENTÁRIOS
// =============================================

// Adicionar avaliação
app.post('/api/avaliacoes', (req, res) => {
    const { localId, usuarioId, nota } = req.body;
    
    db.run(`INSERT INTO Avaliacoes (LocalID, UsuarioID, Nota) 
            VALUES (?, ?, ?)`, [localId, usuarioId, nota], function(err) {
        if (err) {
            if (err.message.includes('UNIQUE constraint failed')) {
                res.status(400).json({ error: 'Você já avaliou este local!' });
            } else {
                res.status(500).json({ error: err.message });
            }
        } else {
            res.json({ success: true, message: 'Avaliação registrada!', id: this.lastID });
        }
    });
});

// Adicionar comentário
app.post('/api/comentarios', (req, res) => {
    const { localId, usuarioId, texto } = req.body;
    
    db.run(`INSERT INTO Comentarios (LocalID, UsuarioID, Texto) 
            VALUES (?, ?, ?)`, [localId, usuarioId, texto], function(err) {
        if (err) {
            res.status(500).json({ error: err.message });
        } else {
            res.json({ success: true, message: 'Comentário adicionado!', id: this.lastID });
        }
    });
});

// Listar comentários de um local
app.get('/api/comentarios/:localId', (req, res) => {
    const { localId } = req.params;
    
    db.all(`SELECT c.Texto, c.DataComentario, u.Nome as Usuario
            FROM Comentarios c
            JOIN Usuarios u ON c.UsuarioID = u.ID
            WHERE c.LocalID = ?
            ORDER BY c.DataComentario DESC`, [localId], (err, rows) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else {
            res.json(rows);
        }
    });
});

// =============================================
// ESTATÍSTICAS
// =============================================
app.get('/api/estatisticas', (req, res) => {
    const stats = {};
    
    // Contar usuários
    db.get("SELECT COUNT(*) as total FROM Usuarios", (err, row) => {
        if (!err) stats.usuarios = row.total;
        
        // Contar locais
        db.get("SELECT COUNT(*) as total FROM Locais WHERE Status = 'ativo'", (err, row) => {
            if (!err) stats.locais = row.total;
            
            // Contar avaliações
            db.get("SELECT COUNT(*) as total FROM Avaliacoes", (err, row) => {
                if (!err) stats.avaliacoes = row.total;
                
                // Contar comentários
                db.get("SELECT COUNT(*) as total FROM Comentarios", (err, row) => {
                    if (!err) stats.comentarios = row.total;
                    
                    res.json(stats);
                });
            });
        });
    });
});

// Iniciar servidor
app.listen(PORT, () => {
    console.log(`🚀 Servidor GADYS rodando na porta ${PORT}`);
    console.log(`📡 API disponível em http://localhost:${PORT}`);
    console.log(`🗄️ Banco: SQLite (temporário até SQL Server ser configurado)`);
    console.log(`✅ CRUD funcionando!`);
});

// Fechar conexão ao encerrar
process.on('SIGINT', () => {
    db.close((err) => {
        if (err) {
            console.error(err.message);
        }
        console.log('Conexão com banco fechada.');
        process.exit(0);
    });
});

module.exports = app;