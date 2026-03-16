package com.gadys.controller;

import com.gadys.model.Cidade;
import com.gadys.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cidades")
@CrossOrigin(origins = "*")
public class CidadeController {
    
    @Autowired
    private CidadeService cidadeService;
    
    @GetMapping
    public List<Cidade> listar() {
        return cidadeService.listarTodas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
        Optional<Cidade> cidade = cidadeService.buscarPorId(id);
        return cidade.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/estado/{estadoId}")
    public List<Cidade> buscarPorEstado(@PathVariable Long estadoId) {
        return cidadeService.buscarPorEstado(estadoId);
    }
    
    @GetMapping("/buscar")
    public List<Cidade> buscarPorNome(@RequestParam String nome) {
        return cidadeService.buscarPorNome(nome);
    }
    
    @PostMapping
    public ResponseEntity<Cidade> criar(@RequestBody Cidade cidade) {
        Cidade salva = cidadeService.salvar(cidade);
        return ResponseEntity.ok(salva);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        if (cidadeService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cidade.setId(id);
        Cidade salva = cidadeService.salvar(cidade);
        return ResponseEntity.ok(salva);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (cidadeService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cidadeService.excluir(id);
        return ResponseEntity.ok().build();
    }
}