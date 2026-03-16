package com.gadys.service;

import com.gadys.model.Avaliacao;
import com.gadys.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {
    
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    
    public List<Avaliacao> listarPorLocal(Long localId) {
        return avaliacaoRepository.findByLocalId(localId);
    }
    
    public Optional<Avaliacao> buscarPorLocalEUsuario(Long localId, Long usuarioId) {
        return avaliacaoRepository.findByLocalIdAndUsuarioId(localId, usuarioId);
    }
    
    public Double calcularMedia(Long localId) {
        return avaliacaoRepository.findMediaByLocalId(localId);
    }
    
    public Avaliacao salvar(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }
    
    public void excluir(Long id) {
        avaliacaoRepository.deleteById(id);
    }
}