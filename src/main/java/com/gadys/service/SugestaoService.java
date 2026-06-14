package com.gadys.service;

import com.gadys.dto.SugestaoDTO;
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

    public Sugestao salvarRascunho(Long id, SugestaoDTO dto) {
        Sugestao sugestao = repository.findById(id).orElseThrow();
        sugestao.setStatus("RASCUNHO");
        if (dto.getNome() != null) sugestao.setNome(dto.getNome());
        if (dto.getDescricao() != null) sugestao.setDescricao(dto.getDescricao());
        if (dto.getEndereco() != null) sugestao.setEndereco(dto.getEndereco());
        if (dto.getEstado() != null) sugestao.setEstado(dto.getEstado());
        if (dto.getSubcategoria() != null) sugestao.setSubcategoria(dto.getSubcategoria());
        if (dto.getImagemUrl() != null) sugestao.setImagemUrl(dto.getImagemUrl());
        if (dto.getCategoriaCustom() != null) sugestao.setCategoriaCustom(dto.getCategoriaCustom());
        if (dto.getRascunhoConteudo() != null) sugestao.setRascunhoConteudo(dto.getRascunhoConteudo());
        return repository.save(sugestao);
    }

    public Sugestao descartar(Long id) {
        Sugestao sugestao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sugestão não encontrada: " + id));
        sugestao.setStatus("DESCARTADA");
        return repository.save(sugestao);
    }
}
