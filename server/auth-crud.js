const express = require('express');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const sql = require('mssql');
const cors = require('cors');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3001;
const JWT_SECRET = process.env.JWT_SECRET || 'gadys_secret_key_2025';

// Middleware
app.use(cors());
app.use(express.json());

// Configuração do banco SQL Server
const config = {
    server: process.env.DB_SERVER || 'localhost',
    database: process.env.DB_NAME || 'GADYS_DB',
    user: process.env.DB_USER || 'sa',
    password: process.env.DB_PASSWORD || 'sua_senha',
    options: {
        encrypt: false,
        trustServerCertificate: true,
        enableArithAbort: true
    }
};

// Conectar ao banco
sql.connect(config).then(() => {
    console.log('✅ Conectado ao SQL Server');
}).catch(err => {
    console.error('❌ Erro ao conectar:', err);
});

// Middleware de autenticação
const authenticateToken = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if (!token) {
        return res.status(401).json({ error: 'Token de acesso requerido' });
    }

    jwt.verify(token, JWT_SECRET, (err, user) => {
        if (err) {
            return res.status(403).json({ error: 'Token inválido' });
        }
        req.user = user;
        next();
    });
};

// =============================================
// CRUD DE AUTENTICAÇÃO
// =============================================

// CREATE - Registrar usuário
app.post('/api/auth/register', async (req, res) => {
    try {
        const { nome, email, senha, tipoUsuario = 'usuario' } = req.body;

        // Validações
        if (!nome || !email || !senha) {
            return res.status(400).json({ error: 'Nome, email e senha são obrigatórios' });
        }

        // Verificar se email já existe
        const existingUser = await sql.query`
            SELECT ID FROM Usuarios WHERE Email = ${email}
        `;

        if (existingUser.recordset.length > 0) {
            return res.status(400).json({ error: 'Email já cadastrado' });
        }

        // Hash da senha
        const saltRounds = 10;
        const hashedPassword = await bcrypt.hash(senha, saltRounds);

        // Inserir usuário
        const result = await sql.query`
            INSERT INTO Usuarios (Nome, Email, Senha, TipoUsuario, DataCadastro, TotalAcessos)
            OUTPUT INSERTED.ID, INSERTED.Nome, INSERTED.Email, INSERTED.TipoUsuario
            VALUES (${nome}, ${email}, ${hashedPassword}, ${tipoUsuario}, GETDATE(), 0)
        `;

        const newUser = result.recordset[0];

        // Gerar token JWT
        const token = jwt.sign(
            { 
                id: newUser.ID, 
                email: newUser.Email, 
                tipoUsuario: newUser.TipoUsuario 
            },
            JWT_SECRET,
            { expiresIn: '24h' }
        );

        res.status(201).json({
            success: true,
            message: 'Usuário cadastrado com sucesso',
            user: {
                id: newUser.ID,
                nome: newUser.Nome,
                email: newUser.Email,
                tipoUsuario: newUser.TipoUsuario
            },
            token
        });

    } catch (err) {
        console.error('Erro no registro:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// READ - Login
app.post('/api/auth/login', async (req, res) => {
    try {
        const { email, senha } = req.body;

        // Validações
        if (!email || !senha) {
            return res.status(400).json({ error: 'Email e senha são obrigatórios' });
        }

        // Buscar usuário
        const result = await sql.query`
            SELECT ID, Nome, Email, Senha, TipoUsuario, TotalAcessos
            FROM Usuarios 
            WHERE Email = ${email}
        `;

        if (result.recordset.length === 0) {
            return res.status(401).json({ error: 'Credenciais inválidas' });
        }

        const user = result.recordset[0];

        // Verificar senha
        const isValidPassword = await bcrypt.compare(senha, user.Senha);
        if (!isValidPassword) {
            return res.status(401).json({ error: 'Credenciais inválidas' });
        }

        // Atualizar último acesso
        await sql.query`
            UPDATE Usuarios 
            SET UltimoAcesso = GETDATE(), TotalAcessos = TotalAcessos + 1
            WHERE ID = ${user.ID}
        `;

        // Gerar token JWT
        const token = jwt.sign(
            { 
                id: user.ID, 
                email: user.Email, 
                tipoUsuario: user.TipoUsuario 
            },
            JWT_SECRET,
            { expiresIn: '24h' }
        );

        res.json({
            success: true,
            message: 'Login realizado com sucesso',
            user: {
                id: user.ID,
                nome: user.Nome,
                email: user.Email,
                tipoUsuario: user.TipoUsuario,
                totalAcessos: user.TotalAcessos + 1
            },
            token
        });

    } catch (err) {
        console.error('Erro no login:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// READ - Obter perfil do usuário
app.get('/api/auth/profile', authenticateToken, async (req, res) => {
    try {
        const result = await sql.query`
            SELECT ID, Nome, Email, TipoUsuario, DataCadastro, UltimoAcesso, TotalAcessos
            FROM Usuarios 
            WHERE ID = ${req.user.id}
        `;

        if (result.recordset.length === 0) {
            return res.status(404).json({ error: 'Usuário não encontrado' });
        }

        res.json({
            success: true,
            user: result.recordset[0]
        });

    } catch (err) {
        console.error('Erro ao buscar perfil:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// UPDATE - Atualizar perfil
app.put('/api/auth/profile', authenticateToken, async (req, res) => {
    try {
        const { nome, email } = req.body;

        if (!nome || !email) {
            return res.status(400).json({ error: 'Nome e email são obrigatórios' });
        }

        // Verificar se email já existe (exceto o próprio usuário)
        const existingUser = await sql.query`
            SELECT ID FROM Usuarios WHERE Email = ${email} AND ID != ${req.user.id}
        `;

        if (existingUser.recordset.length > 0) {
            return res.status(400).json({ error: 'Email já está em uso' });
        }

        // Atualizar usuário
        await sql.query`
            UPDATE Usuarios 
            SET Nome = ${nome}, Email = ${email}
            WHERE ID = ${req.user.id}
        `;

        res.json({
            success: true,
            message: 'Perfil atualizado com sucesso'
        });

    } catch (err) {
        console.error('Erro ao atualizar perfil:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// UPDATE - Alterar senha
app.put('/api/auth/change-password', authenticateToken, async (req, res) => {
    try {
        const { senhaAtual, novaSenha } = req.body;

        if (!senhaAtual || !novaSenha) {
            return res.status(400).json({ error: 'Senha atual e nova senha são obrigatórias' });
        }

        // Buscar senha atual
        const result = await sql.query`
            SELECT Senha FROM Usuarios WHERE ID = ${req.user.id}
        `;

        if (result.recordset.length === 0) {
            return res.status(404).json({ error: 'Usuário não encontrado' });
        }

        // Verificar senha atual
        const isValidPassword = await bcrypt.compare(senhaAtual, result.recordset[0].Senha);
        if (!isValidPassword) {
            return res.status(401).json({ error: 'Senha atual incorreta' });
        }

        // Hash da nova senha
        const hashedNewPassword = await bcrypt.hash(novaSenha, 10);

        // Atualizar senha
        await sql.query`
            UPDATE Usuarios 
            SET Senha = ${hashedNewPassword}
            WHERE ID = ${req.user.id}
        `;

        res.json({
            success: true,
            message: 'Senha alterada com sucesso'
        });

    } catch (err) {
        console.error('Erro ao alterar senha:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// DELETE - Excluir conta
app.delete('/api/auth/account', authenticateToken, async (req, res) => {
    try {
        const { senha } = req.body;

        if (!senha) {
            return res.status(400).json({ error: 'Senha é obrigatória para excluir conta' });
        }

        // Buscar senha atual
        const result = await sql.query`
            SELECT Senha FROM Usuarios WHERE ID = ${req.user.id}
        `;

        if (result.recordset.length === 0) {
            return res.status(404).json({ error: 'Usuário não encontrado' });
        }

        // Verificar senha
        const isValidPassword = await bcrypt.compare(senha, result.recordset[0].Senha);
        if (!isValidPassword) {
            return res.status(401).json({ error: 'Senha incorreta' });
        }

        // Excluir usuário
        await sql.query`
            DELETE FROM Usuarios WHERE ID = ${req.user.id}
        `;

        res.json({
            success: true,
            message: 'Conta excluída com sucesso'
        });

    } catch (err) {
        console.error('Erro ao excluir conta:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// READ - Listar todos os usuários (apenas admin)
app.get('/api/auth/users', authenticateToken, async (req, res) => {
    try {
        // Verificar se é admin
        if (req.user.tipoUsuario !== 'adm') {
            return res.status(403).json({ error: 'Acesso negado' });
        }

        const result = await sql.query`
            SELECT ID, Nome, Email, TipoUsuario, DataCadastro, UltimoAcesso, TotalAcessos
            FROM Usuarios 
            ORDER BY DataCadastro DESC
        `;

        res.json({
            success: true,
            users: result.recordset
        });

    } catch (err) {
        console.error('Erro ao listar usuários:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// DELETE - Excluir usuário (apenas admin)
app.delete('/api/auth/users/:id', authenticateToken, async (req, res) => {
    try {
        // Verificar se é admin
        if (req.user.tipoUsuario !== 'adm') {
            return res.status(403).json({ error: 'Acesso negado' });
        }

        const { id } = req.params;

        // Não permitir excluir a si mesmo
        if (parseInt(id) === req.user.id) {
            return res.status(400).json({ error: 'Não é possível excluir sua própria conta' });
        }

        await sql.query`
            DELETE FROM Usuarios WHERE ID = ${id}
        `;

        res.json({
            success: true,
            message: 'Usuário excluído com sucesso'
        });

    } catch (err) {
        console.error('Erro ao excluir usuário:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// Verificar token
app.post('/api/auth/verify', authenticateToken, (req, res) => {
    res.json({
        success: true,
        user: req.user
    });
});

// Logout (invalidar token no frontend)
app.post('/api/auth/logout', (req, res) => {
    res.json({
        success: true,
        message: 'Logout realizado com sucesso'
    });
});

// Iniciar servidor
app.listen(PORT, () => {
    console.log(`🚀 Servidor de autenticação rodando na porta ${PORT}`);
    console.log(`📡 API disponível em http://localhost:${PORT}`);
});

module.exports = app;