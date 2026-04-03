package com.gadys.controller;

import com.gadys.model.MensagemContato;
import com.gadys.service.MensagemContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contato")
@CrossOrigin(origins = "*")
public class MensagemContatoController {

    @Autowired
    private MensagemContatoService service;

    @PostMapping
    public ResponseEntity<MensagemContato> enviar(@RequestBody MensagemContato mensagem) {
        return ResponseEntity.ok(service.salvar(mensagem));
    }

    @GetMapping
    public List<MensagemContato> listar() {
        return service.listarTodas();
    }

    @GetMapping("/novas")
    public List<MensagemContato> listarNovas() {
        return service.listarNovas();
    }

    @PutMapping("/{id}/responder")
    public ResponseEntity<?> responder(@PathVariable Long id, @RequestBody String resposta) {
        return service.buscarPorId(id).map(msg -> {
            msg.setResposta(resposta);
            msg.setStatus("respondida");
            return ResponseEntity.ok(service.salvar(msg));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.excluir(id);
        return ResponseEntity.ok().build();
    }
}
