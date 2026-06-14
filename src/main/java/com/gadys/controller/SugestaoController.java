package com.gadys.controller;

import com.gadys.dto.SugestaoDTO;
import com.gadys.model.Sugestao;
import com.gadys.service.SugestaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sugestoes")
@CrossOrigin(origins = "*")
public class SugestaoController {

    private final SugestaoService service;

    public SugestaoController(SugestaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sugestao> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/pendentes")
    public List<Sugestao> listarPendentes() {
        return service.listarPendentes();
    }

    @PostMapping
    public ResponseEntity<Sugestao> criar(@RequestBody SugestaoDTO dto) {
        Sugestao sugestao = new Sugestao();
        sugestao.setNome(dto.getNome());
        sugestao.setDescricao(dto.getDescricao());
        sugestao.setEndereco(dto.getEndereco());
        sugestao.setEstado(dto.getEstado());
        sugestao.setSubcategoria(dto.getSubcategoria());
        sugestao.setImagemUrl(dto.getImagemUrl());
        sugestao.setEnviadoPor(dto.getEnviadoPor());
        sugestao.setUsuarioId(dto.getUsuarioId());
        sugestao.setCategoriaCustom(dto.getCategoriaCustom());
        sugestao.setStatus("PENDENTE");
        return ResponseEntity.ok(service.salvar(sugestao));
    }

    @PostMapping("/{id}/analisar")
    public ResponseEntity<Sugestao> analisar(@PathVariable Long id) {
        return ResponseEntity.ok(service.analisar(id));
    }

    @PostMapping("/{id}/descartar")
    public ResponseEntity<Sugestao> descartar(@PathVariable Long id) {
        return ResponseEntity.ok(service.descartar(id));
    }
}
