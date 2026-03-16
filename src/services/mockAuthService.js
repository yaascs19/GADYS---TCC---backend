// Mock do AuthService para testar sem backend
class MockAuthService {
  constructor() {
    // Simular banco em localStorage
    this.users = JSON.parse(localStorage.getItem('mockUsers')) || [
      { id: 1, nome: 'Admin', email: 'admin@gadys.com', senha: '123456', tipoUsuario: 'ADMIN' }
    ];
  }

  async login(email, senha) {
    await this.delay(500); // Simular delay de rede
    
    const user = this.users.find(u => u.email === email && u.senha === senha);
    
    if (user) {
      localStorage.setItem('user', JSON.stringify(user));
      localStorage.setItem('isLoggedIn', 'true');
      localStorage.setItem('userType', user.tipoUsuario);
      localStorage.setItem('userName', user.nome);
      
      return { 
        success: true, 
        user: {
          id: user.id,
          nome: user.nome,
          tipoUsuario: user.tipoUsuario
        }
      };
    }
    
    return { success: false, error: 'Email ou senha incorretos' };
  }

  async register(userData) {
    await this.delay(500);
    
    // Verificar se email já existe
    if (this.users.find(u => u.email === userData.email)) {
      return { success: false, error: 'Email já cadastrado' };
    }
    
    // Criar novo usuário
    const newUser = {
      id: this.users.length + 1,
      nome: userData.nome,
      email: userData.email,
      senha: userData.senha,
      tipoUsuario: userData.tipoUsuario === 'adm' ? 'ADMIN' : 'USUARIO'
    };
    
    this.users.push(newUser);
    localStorage.setItem('mockUsers', JSON.stringify(this.users));
    
    return { success: true, message: 'Usuário cadastrado com sucesso!' };
  }

  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('userType');
    localStorage.removeItem('userName');
  }

  getUser() {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  isAuthenticated() {
    return localStorage.getItem('isLoggedIn') === 'true';
  }

  delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
}

export default new MockAuthService();