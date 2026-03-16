const API_BASE_URL = '/api/auth';

class AuthService {
  // Login
  async login(email, senha) {
    try {
      const response = await fetch(`${API_BASE_URL}/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, senha }),
      });

      const data = await response.json();

      if (data.sucesso) {
        const user = {
          id: data.usuarioId,
          nome: data.nome,
          tipoUsuario: data.tipoUsuario
        };
        localStorage.setItem('user', JSON.stringify(user));
        localStorage.setItem('isLoggedIn', 'true');
        localStorage.setItem('userType', data.tipoUsuario);
        localStorage.setItem('userName', data.nome);
        return { success: true, user };
      } else {
        return { success: false, error: data.mensagem };
      }
    } catch (error) {
      return { success: false, error: 'Erro de conexão com o servidor' };
    }
  }

  // Registrar usuário
  async register(userData) {
    try {
      const response = await fetch(`${API_BASE_URL}/cadastrar`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          nome: userData.nome,
          email: userData.email,
          senha: userData.senha,
          tipoUsuario: userData.tipoUsuario === 'adm' ? 'ADMIN' : 'USUARIO'
        }),
      });

      const data = await response.json();

      if (data.sucesso) {
        return { success: true, message: data.mensagem };
      } else {
        return { success: false, error: data.mensagem };
      }
    } catch (error) {
      return { success: false, error: 'Erro de conexão com o servidor' };
    }
  }

  // Logout
  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('userType');
    localStorage.removeItem('userName');
  }

  // Obter perfil
  async getProfile() {
    try {
      const token = this.getToken();
      if (!token) return { success: false, error: 'Token não encontrado' };

      const response = await fetch(`${API_BASE_URL}/profile`, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });

      return await response.json();
    } catch (error) {
      return { success: false, error: 'Erro de conexão' };
    }
  }

  // Alterar senha
  async changePassword(senhaAtual, novaSenha) {
    try {
      const token = this.getToken();
      if (!token) return { success: false, error: 'Token não encontrado' };

      const response = await fetch(`${API_BASE_URL}/change-password`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify({ senhaAtual, novaSenha }),
      });

      return await response.json();
    } catch (error) {
      return { success: false, error: 'Erro de conexão' };
    }
  }

  // Utilitários
  getToken() {
    return localStorage.getItem('token');
  }

  getUser() {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  isAuthenticated() {
    return !!this.getToken();
  }

  isAdmin() {
    const user = this.getUser();
    return user && user.tipoUsuario === 'adm';
  }
}

export default new AuthService();