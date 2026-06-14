package com.gadys.controller;

import com.gadys.model.Categoria;
import com.gadys.repository.CategoriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{estado}")
    public List<Categoria> listarPorEstado(@PathVariable String estado) {
        return repository.findByEstado(estado);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String, String> body) {
        String nome = body.get("nome");
        String estado = body.get("estado");
        if (repository.existsByNomeIgnoreCaseAndEstado(nome, estado)) {
            return ResponseEntity.badRequest().body("Categoria já existe.");
        }
        return ResponseEntity.ok(repository.save(new Categoria(nome, estado)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
