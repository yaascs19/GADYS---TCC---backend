package com.gadys.service;

import com.gadys.model.Cidade;
import com.gadys.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {
    
    @Autowired
    private CidadeRepository cidadeRepository;
    
    public List<Cidade> listarTodas() {
        return cidadeRepository.findAll();
    }
    
    public Optional<Cidade> buscarPorId(Long id) {
        return cidadeRepository.findById(id);
    }
    
    public List<Cidade> buscarPorEstado(Long estadoId) {
        return cidadeRepository.findByEstadoId(estadoId);
    }
    
    public List<Cidade> buscarPorNome(String nome) {
        return cidadeRepository.findByNomeContaining(nome);
    }
    
    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }
    
    public void excluir(Long id) {
        cidadeRepository.deleteById(id);
    }
}