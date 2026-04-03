package com.gadys.controller;

import com.gadys.dto.LocalDTO;
import com.gadys.model.Local;
import com.gadys.model.StatusLocal;
import com.gadys.model.Usuario;
import com.gadys.service.LocalService;
import com.gadys.service.UsuarioService;
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
        return localService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<Local> buscarPorNome(@RequestParam String nome) {
        return localService.buscarPorNome(nome);
    }

    @GetMapping("/categoria/{categoria}")
    public List<Local> buscarPorCategoria(@PathVariable String categoria) {
        return localService.buscarPorCategoria(categoria);
    }

    @GetMapping("/subcategoria/{subcategoria}")
    public List<Local> buscarPorSubcategoria(@PathVariable String subcategoria) {
        return localService.buscarPorSubcategoria(subcategoria);
    }

    @GetMapping("/cidade/{cidade}")
    public List<Local> buscarPorCidade(@PathVariable String cidade) {
        return localService.buscarPorCidade(cidade);
    }

    @GetMapping("/estado/{estado}")
    public List<Local> buscarPorEstado(@PathVariable String estado) {
        return localService.buscarPorEstado(estado);
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody LocalDTO dto, @RequestParam(required = false) Long usuarioId) {
        Local local = new Local();
        local.setNome(dto.getNome());
        local.setDescricao(dto.getDescricao());
        local.setCategoria(dto.getCategoria());
        local.setSubcategoria(dto.getSubcategoria());
        local.setCidade(dto.getCidade());
        local.setEstado(dto.getEstado());
        local.setEndereco(dto.getEndereco());
        local.setCoordenadas(dto.getCoordenadas());
        local.setHorarioFuncionamento(dto.getHorarioFuncionamento());
        local.setPreco(dto.getPreco());
        local.setInformacoesAdicionais(dto.getInformacoesAdicionais());
        local.setImagemUrl(dto.getImagemUrl());
        local.setEnviadoPor(dto.getEnviadoPor());
        local.setStatus(StatusLocal.PENDENTE);

        if (usuarioId != null) {
            Optional<Usuario> usuario = usuarioService.buscarPorId(usuarioId);
            usuario.ifPresent(local::setCriadoPor);
        }

        return ResponseEntity.ok(localService.salvar(local));
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
