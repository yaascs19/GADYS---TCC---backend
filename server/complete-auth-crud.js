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

// Middleware admin
const requireAdmin = (req, res, next) => {
    if (req.user.tipoUsuario !== 'adm') {
        return res.status(403).json({ error: 'Acesso negado - Admin necessário' });
    }
    next();
};

// =============================================
// ROTAS DE AUTENTICAÇÃO
// =============================================

// POST /api/auth/register - Registrar usuário
app.post('/api/auth/register', async (req, res) => {
    try {
        const { nome, email, senha, tipoUsuario = 'usuario' } = req.body;

        if (!nome || !email || !senha) {
            return res.status(400).json({ error: 'Nome, email e senha são obrigatórios' });
        }

        const existingUser = await sql.query`SELECT ID FROM Usuarios WHERE Email = ${email}`;
        if (existingUser.recordset.length > 0) {
            return res.status(400).json({ error: 'Email já cadastrado' });
        }

        const hashedPassword = await bcrypt.hash(senha, 10);

        const result = await sql.query`
            INSERT INTO Usuarios (Nome, Email, Senha, TipoUsuario, DataCadastro, TotalAcessos)
            OUTPUT INSERTED.ID, INSERTED.Nome, INSERTED.Email, INSERTED.TipoUsuario
            VALUES (${nome}, ${email}, ${hashedPassword}, ${tipoUsuario}, GETDATE(), 0)
        `;

        const newUser = result.recordset[0];
        const token = jwt.sign({ id: newUser.ID, email: newUser.Email, tipoUsuario: newUser.TipoUsuario }, JWT_SECRET, { expiresIn: '24h' });

        res.status(201).json({
            success: true,
            message: 'Usuário cadastrado com sucesso',
            user: { id: newUser.ID, nome: newUser.Nome, email: newUser.Email, tipoUsuario: newUser.TipoUsuario },
            token
        });

    } catch (err) {
        console.error('Erro no registro:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// POST /api/auth/login - Login
app.post('/api/auth/login', async (req, res) => {
    try {
        const { email, senha } = req.body;

        if (!email || !senha) {
            return res.status(400).json({ error: 'Email e senha são obrigatórios' });
        }

        const result = await sql.query`
            SELECT ID, Nome, Email, Senha, TipoUsuario, TotalAcessos
            FROM Usuarios WHERE Email = ${email}
        `;

        if (result.recordset.length === 0) {
            return res.status(401).json({ error: 'Credenciais inválidas' });
        }

        const user = result.recordset[0];
        const isValidPassword = await bcrypt.compare(senha, user.Senha);
        if (!isValidPassword) {
            return res.status(401).json({ error: 'Credenciais inválidas' });
        }

        await sql.query`
            UPDATE Usuarios 
            SET UltimoAcesso = GETDATE(), TotalAcessos = TotalAcessos + 1
            WHERE ID = ${user.ID}
        `;

        const token = jwt.sign({ id: user.ID, email: user.Email, tipoUsuario: user.TipoUsuario }, JWT_SECRET, { expiresIn: '24h' });

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

// POST /api/auth/logout - Logout
app.post('/api/auth/logout', (req, res) => {
    res.json({ success: true, message: 'Logout realizado com sucesso' });
});

// POST /api/auth/verify - Verificar token
app.post('/api/auth/verify', authenticateToken, (req, res) => {
    res.json({ success: true, user: req.user });
});

// =============================================
// ROTAS DE PERFIL
// =============================================

// GET /api/auth/profile - Obter perfil
app.get('/api/auth/profile', authenticateToken, async (req, res) => {
    try {
        const result = await sql.query`
            SELECT ID, Nome, Email, TipoUsuario, DataCadastro, UltimoAcesso, TotalAcessos
            FROM Usuarios WHERE ID = ${req.user.id}
        `;

        if (result.recordset.length === 0) {
            return res.status(404).json({ error: 'Usuário não encontrado' });
        }

        res.json({ success: true, user: result.recordset[0] });

    } catch (err) {
        console.error('Erro ao buscar perfil:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// PUT /api/auth/profile - Atualizar perfil
app.put('/api/auth/profile', authenticateToken, async (req, res) => {
    try {
        const { nome, email } = req.body;

        if (!nome || !email) {
            return res.status(400).json({ error: 'Nome e email são obrigatórios' });
        }

        const existingUser = await sql.query`
            SELECT ID FROM Usuarios WHERE Email = ${email} AND ID != ${req.user.id}
        `;

        if (existingUser.recordset.length > 0) {
            return res.status(400).json({ error: 'Email já está em uso' });
        }

        await sql.query`
            UPDATE Usuarios 
            SET Nome = ${nome}, Email = ${email}
            WHERE ID = ${req.user.id}
        `;

        res.json({ success: true, message: 'Perfil atualizado com sucesso' });

    } catch (err) {
        console.error('Erro ao atualizar perfil:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// PUT /api/auth/change-password - Alterar senha
app.put('/api/auth/change-password', authenticateToken, async (req, res) => {
    try {
        const { senhaAtual, novaSenha } = req.body;

        if (!senhaAtual || !novaSenha) {
            return res.status(400).json({ error: 'Senha atual e nova senha são obrigatórias' });
        }

        const result = await sql.query`SELECT Senha FROM Usuarios WHERE ID = ${req.user.id}`;
        if (result.recordset.length === 0) {
            return res.status(404).json({ error: 'Usuário não encontrado' });
        }

        const isValidPassword = await bcrypt.compare(senhaAtual, result.recordset[0].Senha);
        if (!isValidPassword) {
            return res.status(401).json({ error: 'Senha atual incorreta' });
        }

        const hashedNewPassword = await bcrypt.hash(novaSenha, 10);

        await sql.query`
            UPDATE Usuarios 
            SET Senha = ${hashedNewPassword}
            WHERE ID = ${req.user.id}
        `;

        res.json({ success: true, message: 'Senha alterada com sucesso' });

    } catch (err) {
        console.error('Erro ao alterar senha:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// DELETE /api/auth/account - Excluir conta
app.delete('/api/auth/account', authenticateToken, async (req, res) => {
    try {
        const { senha } = req.body;

        if (!senha) {
            return res.status(400).json({ error: 'Senha é obrigatória para excluir conta' });
        }

        const result = await sql.query`SELECT Senha FROM Usuarios WHERE ID = ${req.user.id}`;
        if (result.recordset.length === 0) {
            return res.status(404).json({ error: 'Usuário não encontrado' });
        }

        const isValidPassword = await bcrypt.compare(senha, result.recordset[0].Senha);
        if (!isValidPassword) {
            return res.status(401).json({ error: 'Senha incorreta' });
        }

        await sql.query`DELETE FROM Usuarios WHERE ID = ${req.user.id}`;

        res.json({ success: true, message: 'Conta excluída com sucesso' });

    } catch (err) {
        console.error('Erro ao excluir conta:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// =============================================
// ROTAS ADMINISTRATIVAS
// =============================================

// GET /api/auth/users - Listar todos os usuários (Admin)
app.get('/api/auth/users', authenticateToken, requireAdmin, async (req, res) => {
    try {
        const result = await sql.query`
            SELECT ID, Nome, Email, TipoUsuario, DataCadastro, UltimoAcesso, TotalAcessos
            FROM Usuarios ORDER BY DataCadastro DESC
        `;

        res.json({ success: true, users: result.recordset });

    } catch (err) {
        console.error('Erro ao listar usuários:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// GET /api/auth/users/:id - Obter usuário específico (Admin)
app.get('/api/auth/users/:id', authenticateToken, requireAdmin, async (req, res) => {
    try {
        const { id } = req.params;

        const result = await sql.query`
            SELECT ID, Nome, Email, TipoUsuario, DataCadastro, UltimoAcesso, TotalAcessos
            FROM Usuarios WHERE ID = ${id}
        `;

        if (result.recordset.length === 0) {
            return res.status(404).json({ error: 'Usuário não encontrado' });
        }

        res.json({ success: true, user: result.recordset[0] });

    } catch (err) {
        console.error('Erro ao buscar usuário:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// PUT /api/auth/users/:id - Atualizar usuário (Admin)
app.put('/api/auth/users/:id', authenticateToken, requireAdmin, async (req, res) => {
    try {
        const { id } = req.params;
        const { nome, email, tipoUsuario } = req.body;

        if (!nome || !email || !tipoUsuario) {
            return res.status(400).json({ error: 'Nome, email e tipo de usuário são obrigatórios' });
        }

        const existingUser = await sql.query`
            SELECT ID FROM Usuarios WHERE Email = ${email} AND ID != ${id}
        `;

        if (existingUser.recordset.length > 0) {
            return res.status(400).json({ error: 'Email já está em uso' });
        }

        await sql.query`
            UPDATE Usuarios 
            SET Nome = ${nome}, Email = ${email}, TipoUsuario = ${tipoUsuario}
            WHERE ID = ${id}
        `;

        res.json({ success: true, message: 'Usuário atualizado com sucesso' });

    } catch (err) {
        console.error('Erro ao atualizar usuário:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// DELETE /api/auth/users/:id - Excluir usuário (Admin)
app.delete('/api/auth/users/:id', authenticateToken, requireAdmin, async (req, res) => {
    try {
        const { id } = req.params;

        if (parseInt(id) === req.user.id) {
            return res.status(400).json({ error: 'Não é possível excluir sua própria conta' });
        }

        await sql.query`DELETE FROM Usuarios WHERE ID = ${id}`;

        res.json({ success: true, message: 'Usuário excluído com sucesso' });

    } catch (err) {
        console.error('Erro ao excluir usuário:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// =============================================
// ROTAS DE ESTATÍSTICAS
// =============================================

// GET /api/auth/stats - Estatísticas do sistema (Admin)
app.get('/api/auth/stats', authenticateToken, requireAdmin, async (req, res) => {
    try {
        const totalUsers = await sql.query`SELECT COUNT(*) as total FROM Usuarios`;
        const totalAdmins = await sql.query`SELECT COUNT(*) as total FROM Usuarios WHERE TipoUsuario = 'adm'`;
        const totalRegularUsers = await sql.query`SELECT COUNT(*) as total FROM Usuarios WHERE TipoUsuario = 'usuario'`;
        const recentLogins = await sql.query`
            SELECT COUNT(*) as total FROM Usuarios 
            WHERE UltimoAcesso >= DATEADD(day, -7, GETDATE())
        `;
        const topUsers = await sql.query`
            SELECT TOP 5 Nome, Email, TotalAcessos 
            FROM Usuarios 
            ORDER BY TotalAcessos DESC
        `;

        res.json({
            success: true,
            stats: {
                totalUsuarios: totalUsers.recordset[0].total,
                totalAdmins: totalAdmins.recordset[0].total,
                totalUsuariosRegulares: totalRegularUsers.recordset[0].total,
                loginsUltimos7Dias: recentLogins.recordset[0].total,
                usuariosMaisAtivos: topUsers.recordset
            }
        });

    } catch (err) {
        console.error('Erro ao buscar estatísticas:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// =============================================
// ROTAS DE BUSCA
// =============================================

// GET /api/auth/search - Buscar usuários (Admin)
app.get('/api/auth/search', authenticateToken, requireAdmin, async (req, res) => {
    try {
        const { q } = req.query;

        if (!q) {
            return res.status(400).json({ error: 'Parâmetro de busca é obrigatório' });
        }

        const result = await sql.query`
            SELECT ID, Nome, Email, TipoUsuario, DataCadastro, UltimoAcesso, TotalAcessos
            FROM Usuarios 
            WHERE Nome LIKE ${'%' + q + '%'} OR Email LIKE ${'%' + q + '%'}
            ORDER BY Nome
        `;

        res.json({ success: true, users: result.recordset });

    } catch (err) {
        console.error('Erro na busca:', err);
        res.status(500).json({ error: 'Erro interno do servidor' });
    }
});

// =============================================
// ROTA DE TESTE
// =============================================

// GET /api/auth/test - Testar conexão
app.get('/api/auth/test', async (req, res) => {
    try {
        const result = await sql.query`SELECT GETDATE() as data`;
        res.json({ 
            success: true, 
            message: 'Conexão com banco funcionando', 
            data: result.recordset[0].data 
        });
    } catch (err) {
        res.status(500).json({ error: 'Erro na conexão com banco' });
    }
});

// Iniciar servidor
app.listen(PORT, () => {
    console.log(`🚀 Servidor CRUD Auth rodando na porta ${PORT}`);
    console.log(`📡 API disponível em http://localhost:${PORT}`);
    console.log(`\n📋 ROTAS DISPONÍVEIS:`);
    console.log(`POST   /api/auth/register     - Registrar usuário`);
    console.log(`POST   /api/auth/login        - Login`);
    console.log(`POST   /api/auth/logout       - Logout`);
    console.log(`POST   /api/auth/verify       - Verificar token`);
    console.log(`GET    /api/auth/profile      - Obter perfil`);
    console.log(`PUT    /api/auth/profile      - Atualizar perfil`);
    console.log(`PUT    /api/auth/change-password - Alterar senha`);
    console.log(`DELETE /api/auth/account      - Excluir conta`);
    console.log(`GET    /api/auth/users        - Listar usuários (Admin)`);
    console.log(`GET    /api/auth/users/:id    - Obter usuário (Admin)`);
    console.log(`PUT    /api/auth/users/:id    - Atualizar usuário (Admin)`);
    console.log(`DELETE /api/auth/users/:id    - Excluir usuário (Admin)`);
    console.log(`GET    /api/auth/stats        - Estatísticas (Admin)`);
    console.log(`GET    /api/auth/search       - Buscar usuários (Admin)`);
    console.log(`GET    /api/auth/test         - Testar conexão`);
});

module.exports = app;