package com.gadys.controller;

import com.gadys.dto.LocalDTO;
import com.gadys.model.Local;
import com.gadys.model.StatusLocal;
import com.gadys.model.Usuario;
import com.gadys.repository.LocalRepository;
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
    private LocalRepository localRepository;

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

    @GetMapping("/pendentes")
    public List<Local> listarPendentes() {
        return localService.listarPendentes();
    }

    @GetMapping("/aprovados")
    public List<Local> listarAprovados() {
        return localService.listarAprovados();
    }

    @GetMapping("/rota")
    public ResponseEntity<Local> getByRota(@RequestParam String rota) {
        return localService.buscarPorRotaFrontend(rota)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
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

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody LocalDTO dto) {
        try {
            Local atualizado = localService.atualizar(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/aprovar/{id}")
    public ResponseEntity<?> aprovar(@PathVariable Long id) {
        Long adminId = Long.parseLong(System.getProperty("admin.id", "1"));
        Optional<Usuario> admin = usuarioService.buscarPorId(adminId);
        if (admin.isEmpty() || !admin.get().isAdmin()) {
            return ResponseEntity.badRequest().body("Usuário não é admin");
        }
        localService.aprovarLocal(id, admin.get());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/inativar")
    public ResponseEntity<?> toggleInativar(@PathVariable Long id) {
        Optional<Local> localOpt = localService.buscarPorId(id);
        if (localOpt.isEmpty()) return ResponseEntity.notFound().build();
        Local local = localOpt.get();
        local.setStatus(local.getStatus() == StatusLocal.ATIVO ? StatusLocal.INATIVO : StatusLocal.ATIVO);
        localService.salvar(local);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<?> aprovarPut(@PathVariable Long id, @RequestParam Long adminId) {
        Optional<Usuario> admin = usuarioService.buscarPorId(adminId);
        if (admin.isEmpty() || !admin.get().isAdmin()) {
            return ResponseEntity.badRequest().body("Usuário não é admin");
        }
        localService.aprovarLocal(id, admin.get());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<?> rejeitarPut(@PathVariable Long id, @RequestParam Long adminId) {
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

    @GetMapping("/admin/diagnostico-coords")
    public ResponseEntity<?> diagnostico() {
        List<Local> todos = localRepository.findAll();
        long semCoords = todos.stream()
            .filter(l -> l.getCoordenadas() == null || l.getCoordenadas().trim().isEmpty())
            .count();
        return ResponseEntity.ok("Total: " + todos.size() + " | Sem coordenadas: " + semCoords);
    }

    @PostMapping("/admin/geocodificar-todos")
    public ResponseEntity<?> geocodificarTodos() {
        int atualizados = localService.geocodificarLocaisSemCoordenadas();
        return ResponseEntity.ok("Coordenadas atualizadas: " + atualizados + " locais.");
    }
}
