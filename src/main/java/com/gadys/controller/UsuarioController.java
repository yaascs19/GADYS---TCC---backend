package com.gadys.controller;

import com.gadys.dto.LoginDTO;
import com.gadys.dto.UsuarioDTO;
import com.gadys.model.Usuario;
import com.gadys.service.UsuarioService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listar() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<Map<String, Object>> result = usuarios.stream().map(u -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", u.getId());
            map.put("nome", u.getNome());
            map.put("email", u.getEmail());
            map.put("tipoUsuario", u.getTipoUsuario());
            map.put("ativo", u.getAtivo());
            map.put("dataCadastro", u.getDataCadastro());
            return map;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody UsuarioDTO dto) {
        if (usuarioService.existeEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        Usuario usuario = new Usuario(dto.getNome(), dto.getEmail(), dto.getSenha());
        if (dto.getTipoUsuario() != null) {
            usuario.setTipoUsuario(dto.getTipoUsuario());
        }
        Usuario salvo = usuarioService.salvar(usuario);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {
        Optional<Usuario> usuario = usuarioService.autenticar(dto.getEmail(), dto.getSenha());
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.badRequest().body("Email ou senha inválidos");
    }

    @PostMapping("/{id}/inativar")
    public ResponseEntity<?> toggleAtivo(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
        if (usuarioOpt.isEmpty()) return ResponseEntity.notFound().build();
        Usuario usuario = usuarioOpt.get();
        usuario.setAtivo("ATIVO".equals(usuario.getAtivo()) ? "INATIVO" : "ATIVO");
        usuarioService.salvar(usuario);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
        if (usuarioOpt.isEmpty()) return ResponseEntity.notFound().build();
        Usuario usuario = usuarioOpt.get();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenha(dto.getSenha());
        }
        Usuario salvo = usuarioService.salvar(usuario);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (usuarioService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.excluir(id);
        return ResponseEntity.ok().build();
    }
}
