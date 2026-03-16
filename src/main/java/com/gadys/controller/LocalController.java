package com.gadys.controller;

import com.gadys.dto.LocalDTO;
import com.gadys.model.*;
import com.gadys.service.*;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locais")
@CrossOrigin(origins = "*")
public class LocalController {
    
    @Autowired
    private LocalService localService;
    
    @Autowired
    private CidadeService cidadeService;
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public List<Local> listar() {
        return localService.listarTodos();
    }
    
    @GetMapping("/ativos")
    public List<Local> listarAtivos() {
        return localService.listarAtivos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Local> buscar(@PathVariable Long id) {
        Optional<Local> local = localService.buscarPorId(id);
        return local.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/buscar")
    public List<Local> buscarPorNome(@RequestParam String nome) {
        return localService.buscarPorNome(nome);
    }
    
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody LocalDTO dto, @RequestParam Long usuarioId) {
        Optional<Cidade> cidade = cidadeService.buscarPorId(dto.getCidadeId());
        Optional<Categoria> categoria = categoriaService.buscarPorId(dto.getCategoriaId());
        Optional<Usuario> usuario = usuarioService.buscarPorId(usuarioId);
        
        if (cidade.isEmpty() || categoria.isEmpty() || usuario.isEmpty()) {
            return ResponseEntity.badRequest().body("Cidade, categoria ou usuário não encontrado");
        }
        
        Local local = new Local(dto.getNome(), dto.getDescricao(), cidade.get(), categoria.get(), usuario.get());
        local.setEndereco(dto.getEndereco());
        local.setCoordenadas(dto.getCoordenadas());
        local.setHorarioFuncionamento(dto.getHorarioFuncionamento());
        local.setPreco(dto.getPreco());
        local.setInformacoesAdicionais(dto.getInformacoesAdicionais());
        local.setImagemUrl(dto.getImagemUrl());
        local.setStatus(StatusLocal.PENDENTE);
        
        Local salvo = localService.salvar(local);
        return ResponseEntity.ok(salvo);
    }
    
    @PutMapping("/{id}/aprovar")
    public ResponseEntity<?> aprovar(@PathVariable Long id, @RequestParam Long adminId) {
        Optional<Usuario> admin = usuarioService.buscarPorId(adminId);
        if (admin.isEmpty() || !admin.get().isAdmin()) {
            return ResponseEntity.badRequest().body("Usuário não é admin");
        }
        
        localService.aprovarLocal(id, admin.get());
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<?> rejeitar(@PathVariable Long id, @RequestParam Long adminId) {
        Optional<Usuario> admin = usuarioService.buscarPorId(adminId);
        if (admin.isEmpty() || !admin.get().isAdmin()) {
            return ResponseEntity.badRequest().body("Usuário não é admin");
        }
        
        localService.rejeitarLocal(id, admin.get());
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (localService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        localService.excluir(id);
        return ResponseEntity.ok().build();
    }
}