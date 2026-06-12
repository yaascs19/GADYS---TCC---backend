package com.gadys.service;

import com.gadys.model.Sugestao;
import com.gadys.repository.SugestaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SugestaoService {

    private final SugestaoRepository repository;

    public SugestaoService(SugestaoRepository repository) {
        this.repository = repository;
    }

    public List<Sugestao> listarTodos() {
        return repository.findAll();
    }

    public List<Sugestao> listarPendentes() {
        return repository.findByStatus("PENDENTE");
    }

    public Sugestao salvar(Sugestao sugestao) {
        return repository.save(sugestao);
    }

    public Sugestao analisar(Long id) {
        Sugestao sugestao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sugestão não encontrada: " + id));
        sugestao.setStatus("ANALISADA");
        return repository.save(sugestao);
    }

    public Sugestao descartar(Long id) {
        Sugestao sugestao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sugestão não encontrada: " + id));
        sugestao.setStatus("DESCARTADA");
        return repository.save(sugestao);
    }
}
