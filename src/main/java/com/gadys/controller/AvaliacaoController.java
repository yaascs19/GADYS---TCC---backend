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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Map<String, Object>> listarPorLocal(@PathVariable Long localId) {
        return avaliacaoService.listarPorLocal(localId).stream().map(a -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", a.getId());
            map.put("nota", a.getNota());
            map.put("usuarioId", a.getUsuario().getId());
            return map;
        }).collect(Collectors.toList());
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
            avaliacaoService.salvar(avaliacao);
            return ResponseEntity.ok(Map.of("success", true));
        }

        Avaliacao avaliacao = new Avaliacao(local.get(), usuario.get(), nota);
        avaliacaoService.salvar(avaliacao);
        return ResponseEntity.ok(Map.of("success", true));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id, @RequestParam Long usuarioId) {
        Optional<Avaliacao> avaliacao = avaliacaoService.buscarPorId(id);
        Optional<Usuario> usuario = usuarioService.buscarPorId(usuarioId);

        if (avaliacao.isEmpty() || usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!avaliacao.get().getUsuario().getId().equals(usuarioId) && !usuario.get().isAdmin()) {
            return ResponseEntity.status(403).body("Sem permissão para excluir esta avaliação");
        }

        avaliacaoService.excluir(id);
        return ResponseEntity.ok().build();
    }
}