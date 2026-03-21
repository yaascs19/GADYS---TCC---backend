// Este arquivo foi atualizado para corresponder às rotas corretas da API do backend e aos formatos de resposta.

class AuthService {
  // Login
  async login(email, senha, tipoUsuario) {
    try {
      const response = await fetch(`/api/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        // IMPORTANTE: O backend requer `tipoUsuario`. Isso precisa ser passado do formulário de login.
        body: JSON.stringify({ email, senha, tipoUsuario }),
      });

      const data = await response.json();

      if (data.success) {
        const user = {
          id: data.user.ID,
          nome: data.user.Nome,
          tipoUsuario: data.user.TipoUsuario
        };
        localStorage.setItem('user', JSON.stringify(user));
        localStorage.setItem('isLoggedIn', 'true');
        localStorage.setItem('userType', user.tipoUsuario);
        localStorage.setItem('userName', user.nome);
        return { success: true, user };
      } else {
        return { success: false, error: data.error };
      }
    } catch (error) {
      return { success: false, error: 'Erro de conexão com o servidor' };
    }
  }

  // Registrar usuário
  async register(userData) {
    try {
      const response = await fetch(`/api/usuarios`, { // URL Corrigida
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

      if (data.success) {
        return { success: true, message: data.message };
      } else {
        return { success: false, error: data.error };
      }
    } catch (error) {
      return { success: false, error: 'Erro de conexão com o servidor' };
    }
  }

  // Logout
  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('userType');
    localStorage.removeItem('userName');
  }

  // Obter usuário e status de autenticação do localStorage
  getUser() {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  isAuthenticated() {
    return localStorage.getItem('isLoggedIn') === 'true';
  }

  isAdmin() {
    const userType = localStorage.getItem('userType');
    // O backend usa 'ADMIN' e 'USUARIO'
    return userType === 'ADMIN';
  }
}

export default new AuthService();
