package com.gadys.config;

import com.gadys.model.Usuario;
import com.gadys.model.TipoUsuario;
import com.gadys.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        try {
            if (!usuarioService.existeEmail("admin@gadys.com")) {
                Usuario admin = new Usuario("Administrador", "admin@gadys.com", "123456");
                admin.setTipoUsuario(TipoUsuario.ADM);
                usuarioService.salvar(admin);
            }

            if (!usuarioService.existeEmail("usuario@gadys.com")) {
                usuarioService.salvar(new Usuario("Usuário Teste", "usuario@gadys.com", "123456"));
            }
        } catch (Exception e) {
            System.err.println("DataInitializer falhou: " + e.getMessage());
        }
    }
}
