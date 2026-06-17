package com.gadys.controller;

import com.gadys.model.Categoria;
import com.gadys.repository.CategoriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Categoria> listarTodas() {
        return repository.findAll();
    }

    @GetMapping("/globais")
    public List<Categoria> listarGlobais() {
        return repository.findByEstadosIsEmpty();
    }

    @GetMapping("/estado/{estado}")
    public List<Categoria> listarPorEstado(@PathVariable String estado) {
        return repository.findByEstadosContaining(estado);
    }

    @PostMapping
    public ResponseEntity<?> criarOuAdicionarEstado(@RequestBody Map<String, String> body) {
        String nome = body.get("nome");
        String estado = body.get("estado");
        Optional<Categoria> existing = repository.findByNomeIgnoreCase(nome);
        if (existing.isPresent()) {
            Categoria cat = existing.get();
            if (estado != null) cat.addEstado(estado);
            return ResponseEntity.ok(repository.save(cat));
        }
        Categoria nova = new Categoria(nome);
        if (estado != null) nova.addEstado(estado);
        return ResponseEntity.ok(repository.save(nova));
    }

    @DeleteMapping("/{id}/estado/{estado}")
    public ResponseEntity<?> removerEstado(@PathVariable Long id, @PathVariable String estado) {
        return repository.findById(id).map(cat -> {
            cat.removeEstado(estado);
            return ResponseEntity.ok(repository.save(cat));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
