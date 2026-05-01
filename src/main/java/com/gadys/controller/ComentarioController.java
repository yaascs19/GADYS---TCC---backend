package com.gadys.controller;

import com.gadys.model.Comentario;
import com.gadys.model.Local;
import com.gadys.model.Usuario;
import com.gadys.service.ComentarioService;
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
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "*")
public class ComentarioController {
    
    @Autowired
    private ComentarioService comentarioService;
    
    @Autowired
    private LocalService localService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/local/{localId}")
    public List<Map<String, Object>> listarPorLocal(@PathVariable Long localId) {
        return comentarioService.listarPorLocal(localId).stream().map(c -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", c.getId());
            map.put("texto", c.getTexto());
            map.put("dataComentario", c.getDataComentario());
            map.put("usuarioId", c.getUsuario().getId());
            map.put("nomeUsuario", c.getUsuario().getNome());
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Map<String, Object>> listarPorUsuario(@PathVariable Long usuarioId) {
        return comentarioService.listarPorUsuario(usuarioId).stream().map(c -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", c.getId());
            map.put("texto", c.getTexto());
            map.put("dataComentario", c.getDataComentario());
            map.put("usuarioId", c.getUsuario().getId());
            map.put("nomeUsuario", c.getUsuario().getNome());
            return map;
        }).collect(Collectors.toList());
    }
    
    @PostMapping
    public ResponseEntity<?> criar(@RequestParam Long localId, @RequestParam Long usuarioId, @RequestParam String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Texto do comentário é obrigatório");
        }
        
        Optional<Local> local = localService.buscarPorId(localId);
        Optional<Usuario> usuario = usuarioService.buscarPorId(usuarioId);
        
        if (local.isEmpty() || usuario.isEmpty()) {
            return ResponseEntity.badRequest().body("Local ou usuário não encontrado");
        }
        
        Comentario comentario = new Comentario(local.get(), usuario.get(), texto);
        Comentario salvo = comentarioService.salvar(comentario);
        return ResponseEntity.ok(salvo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestParam Long usuarioId, @RequestParam String texto) {
        Optional<Comentario> comentarioOpt = comentarioService.buscarPorId(id);
        Optional<Usuario> usuario = usuarioService.buscarPorId(usuarioId);
        
        if (comentarioOpt.isEmpty() || usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Comentario comentario = comentarioOpt.get();
        if (!comentario.podeEditar(usuario.get())) {
            return ResponseEntity.badRequest().body("Usuário não pode editar este comentário");
        }
        
        comentario.editar(texto);
        Comentario salvo = comentarioService.salvar(comentario);
        return ResponseEntity.ok(salvo);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id, @RequestParam Long usuarioId) {
        Optional<Comentario> comentarioOpt = comentarioService.buscarPorId(id);
        Optional<Usuario> usuario = usuarioService.buscarPorId(usuarioId);
        
        if (comentarioOpt.isEmpty() || usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Comentario comentario = comentarioOpt.get();
        if (!comentario.podeEditar(usuario.get())) {
            return ResponseEntity.badRequest().body("Usuário não pode excluir este comentário");
        }
        
        comentarioService.excluir(id);
        return ResponseEntity.ok().build();
    }
}