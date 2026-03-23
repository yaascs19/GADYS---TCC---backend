package com.gadys.config;

import com.gadys.model.Usuario;
import com.gadys.model.TipoUsuario;
import com.gadys.model.Estado;
import com.gadys.model.Cidade;
import com.gadys.model.Categoria;
import com.gadys.service.UsuarioService;
import com.gadys.service.EstadoService;
import com.gadys.service.CidadeService;
import com.gadys.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private EstadoService estadoService;
    
    @Autowired
    private CidadeService cidadeService;
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Override
    public void run(String... args) throws Exception {
        try {
        // Criar usuário admin
        if (!usuarioService.existeEmail("admin@gadys.com")) {
            Usuario admin = new Usuario("Administrador", "admin@gadys.com", "123456");
            admin.setTipoUsuario(TipoUsuario.ADMIN);
            usuarioService.salvar(admin);
        }
        
        // Criar usuário comum
        if (!usuarioService.existeEmail("usuario@gadys.com")) {
            Usuario usuario = new Usuario("Usuário Teste", "usuario@gadys.com", "123456");
            usuarioService.salvar(usuario);
        }
        
        // Criar estados
        if (estadoService.listarTodos().isEmpty()) {
            Estado amazonas = new Estado("Amazonas", "AM");
            Estado sao_paulo = new Estado("São Paulo", "SP");
            Estado rio = new Estado("Rio de Janeiro", "RJ");
            
            estadoService.salvar(amazonas);
            estadoService.salvar(sao_paulo);
            estadoService.salvar(rio);
            
            // Criar cidades
            cidadeService.salvar(new Cidade("Manaus", amazonas));
            cidadeService.salvar(new Cidade("Parintins", amazonas));
            cidadeService.salvar(new Cidade("São Paulo", sao_paulo));
            cidadeService.salvar(new Cidade("Rio de Janeiro", rio));
        }
        
        // Criar categorias
        if (categoriaService.listarTodas().isEmpty()) {
            categoriaService.salvar(new Categoria("Natureza", "🌿", "#4CAF50"));
            categoriaService.salvar(new Categoria("Cultura", "🎭", "#9C27B0"));
            categoriaService.salvar(new Categoria("Gastronomia", "🍽️", "#FF9800"));
            categoriaService.salvar(new Categoria("Monumentos", "🏛️", "#607D8B"));
            categoriaService.salvar(new Categoria("Aventura", "🏔️", "#F44336"));
        }
        } catch (Exception e) {
            System.err.println("DataInitializer falhou: " + e.getMessage());
        }
    }