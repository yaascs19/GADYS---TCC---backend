package com.gadys.controller;

import com.gadys.model.Avaliacao;
import com.gadys.model.Local;
import com.gadys.model.Usuario;
import com.gadys.service.AvaliacaoService;
import com.gadys.service.LocalService;
import com.gadys.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin(origins = "*")
public class AvaliacaoController {
    
    @Autowired
    private AvaliacaoService avaliacaoService;
    
    @Autowired
    private LocalService localService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/local/{localId}")
    public List<Avaliacao> listarPorLocal(@PathVariable Long localId) {
        return avaliacaoService.listarPorLocal(localId);
    }
    
    @GetMapping("/local/{localId}/media")
    public ResponseEntity<Double> obterMedia(@PathVariable Long localId) {
        Double media = avaliacaoService.calcularMedia(localId);
        return ResponseEntity.ok(media != null ? media : 0.0);
    }
    
    @PostMapping
    public ResponseEntity<?> criar(@RequestParam Long localId, @RequestParam Long usuarioId, @RequestParam Integer nota) {
        if (nota < 1 || nota > 5) {
            return ResponseEntity.badRequest().body("Nota deve ser entre 1 e 5");
        }
        
        Optional<Local> local = localService.buscarPorId(localId);
        Optional<Usuario> usuario = usuarioService.buscarPorId(usuarioId);
        
        if (local.isEmpty() || usuario.isEmpty()) {
            return ResponseEntity.badRequest().body("Local ou usuário não encontrado");
        }
        
        // Verifica se já existe avaliação
        Optional<Avaliacao> existente = avaliacaoService.buscarPorLocalEUsuario(localId, usuarioId);
        if (existente.isPresent()) {
            Avaliacao avaliacao = existente.get();
            avaliacao.atualizar(nota);
            Avaliacao salva = avaliacaoService.salvar(avaliacao);
            return ResponseEntity.ok(salva);
        }
        
        Avaliacao avaliacao = new Avaliacao(local.get(), usuario.get(), nota);
        Avaliacao salva = avaliacaoService.salvar(avaliacao);
        return ResponseEntity.ok(salva);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        avaliacaoService.excluir(id);
        return ResponseEntity.ok().build();
    }
}