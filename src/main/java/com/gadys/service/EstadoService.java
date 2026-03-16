package com.gadys.service;

import com.gadys.model.Estado;
import com.gadys.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {
    
    @Autowired
    private EstadoRepository estadoRepository;
    
    public List<Estado> listarTodos() {
        return estadoRepository.findAll();
    }
    
    public Optional<Estado> buscarPorId(Long id) {
        return estadoRepository.findById(id);
    }
    
    public Optional<Estado> buscarPorSigla(String sigla) {
        return estadoRepository.findBySigla(sigla);
    }
    
    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }
    
    public void excluir(Long id) {
        estadoRepository.deleteById(id);
    }
}