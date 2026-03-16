package com.gadys.controller;

import com.gadys.model.Categoria;
import com.gadys.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;
    
    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.listarTodas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscar(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaService.buscarPorId(id);
        return categoria.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Categoria categoria) {
        if (categoriaService.existeNome(categoria.getNome())) {
            return ResponseEntity.badRequest().body("Categoria já existe");
        }
        Categoria salva = categoriaService.salvar(categoria);
        return ResponseEntity.ok(salva);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        if (categoriaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        categoria.setId(id);
        Categoria salva = categoriaService.salvar(categoria);
        return ResponseEntity.ok(salva);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (categoriaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        categoriaService.excluir(id);
        return ResponseEntity.ok().build();
    }
}