package com.gadys.service;

import com.gadys.model.Comentario;
import com.gadys.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {
    
    @Autowired
    private ComentarioRepository comentarioRepository;
    
    public List<Comentario> listarPorLocal(Long localId) {
        return comentarioRepository.findByLocalIdOrderByDataComentarioDesc(localId);
    }
    
    public List<Comentario> listarPorUsuario(Long usuarioId) {
        return comentarioRepository.findByUsuarioId(usuarioId);
    }
    
    public Optional<Comentario> buscarPorId(Long id) {
        return comentarioRepository.findById(id);
    }
    
    public Comentario salvar(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }
    
    public void excluir(Long id) {
        comentarioRepository.deleteById(id);
    }
}