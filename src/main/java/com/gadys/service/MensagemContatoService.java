package com.gadys.service;

import com.gadys.model.MensagemContato;
import com.gadys.repository.MensagemContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MensagemContatoService {

    @Autowired
    private MensagemContatoRepository repository;

    public List<MensagemContato> listarTodas() {
        return repository.findAll();
    }

    public List<MensagemContato> listarNovas() {
        return repository.findByStatus("nova");
    }

    public Optional<MensagemContato> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public MensagemContato salvar(MensagemContato mensagem) {
        return repository.save(mensagem);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
