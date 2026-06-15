package com.gadys.controller;

import com.gadys.model.Favorito;
import com.gadys.model.Local;
import com.gadys.model.Usuario;
import com.gadys.repository.FavoritoRepository;
import com.gadys.repository.LocalRepository;
import com.gadys.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@CrossOrigin(origins = "*")
public class FavoritoController {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalRepository localRepository;

    public FavoritoController(FavoritoRepository favoritoRepository,
                               UsuarioRepository usuarioRepository,
                               LocalRepository localRepository) {
        this.favoritoRepository = favoritoRepository;
        this.usuarioRepository = usuarioRepository;
        this.localRepository = localRepository;
    }

    @GetMapping
    public ResponseEntity<List<Favorito>> listar(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(favoritoRepository.findByUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestParam Long usuarioId, @RequestParam Long localId) {
        if (favoritoRepository.findByUsuarioIdAndLocalId(usuarioId, localId).isPresent()) {
            return ResponseEntity.badRequest().body("Já está nos favoritos.");
        }
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + usuarioId));
        Local local = localRepository.findById(localId)
                .orElseThrow(() -> new RuntimeException("Local não encontrado: " + localId));
        return ResponseEntity.ok(favoritoRepository.save(new Favorito(usuario, local)));
    }

    @DeleteMapping
    public ResponseEntity<Void> remover(@RequestParam Long usuarioId, @RequestParam Long localId) {
        favoritoRepository.deleteByUsuarioIdAndLocalId(usuarioId, localId);
        return ResponseEntity.noContent().build();
    }
}
