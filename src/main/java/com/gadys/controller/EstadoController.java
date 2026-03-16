package com.gadys.controller;

import com.gadys.model.Estado;
import com.gadys.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estados")
@CrossOrigin(origins = "*")
public class EstadoController {
    
    @Autowired
    private EstadoService estadoService;
    
    @GetMapping
    public List<Estado> listar() {
        return estadoService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscar(@PathVariable Long id) {
        Optional<Estado> estado = estadoService.buscarPorId(id);
        return estado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/sigla/{sigla}")
    public ResponseEntity<Estado> buscarPorSigla(@PathVariable String sigla) {
        Optional<Estado> estado = estadoService.buscarPorSigla(sigla);
        return estado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Estado> criar(@RequestBody Estado estado) {
        Estado salvo = estadoService.salvar(estado);
        return ResponseEntity.ok(salvo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
        if (estadoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        estado.setId(id);
        Estado salvo = estadoService.salvar(estado);
        return ResponseEntity.ok(salvo);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (estadoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        estadoService.excluir(id);
        return ResponseEntity.ok().build();
    }
}