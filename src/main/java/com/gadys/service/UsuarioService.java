package com.gadys.service;

import com.gadys.model.Usuario;
import com.gadys.repository.UsuarioRepository;
import com.gadys.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordUtil passwordUtil;
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public Usuario salvar(Usuario usuario) {
        // Criptografar senha se for nova ou alterada
        if (usuario.getId() == null || !usuario.getSenha().startsWith("$2a$")) {
            usuario.setSenha(passwordUtil.encode(usuario.getSenha()));
        }
        return usuarioRepository.save(usuario);
    }
    
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    public Optional<Usuario> autenticar(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && passwordUtil.matches(senha, usuario.get().getSenha())) {
            Usuario u = usuario.get();
            u.setUltimoAcesso(LocalDateTime.now());
            u.setTotalAcessos(u.getTotalAcessos() + 1);
            usuarioRepository.save(u);
            return usuario;
        }
        return Optional.empty();
    }
    
    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
}