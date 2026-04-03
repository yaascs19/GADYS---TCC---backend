package com.gadys.service;

import com.gadys.model.Local;
import com.gadys.model.StatusLocal;
import com.gadys.model.Usuario;
import com.gadys.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;

    public List<Local> listarTodos() {
        return localRepository.findAll();
    }

    public List<Local> listarAtivos() {
        return localRepository.findByStatus(StatusLocal.ATIVO);
    }

    public Optional<Local> buscarPorId(Long id) {
        return localRepository.findById(id);
    }

    public List<Local> buscarPorCategoria(String categoria) {
        return localRepository.findByCategoria(categoria);
    }

    public List<Local> buscarPorSubcategoria(String subcategoria) {
        return localRepository.findBySubcategoria(subcategoria);
    }

    public List<Local> buscarPorCidade(String cidade) {
        return localRepository.findByCidade(cidade);
    }

    public List<Local> buscarPorEstado(String estado) {
        return localRepository.findByEstado(estado);
    }

    public List<Local> buscarPorNome(String nome) {
        return localRepository.findByNomeContaining(nome);
    }

    public Local salvar(Local local) {
        return localRepository.save(local);
    }

    public void aprovarLocal(Long id, Usuario admin) {
        Optional<Local> localOpt = localRepository.findById(id);
        if (localOpt.isPresent() && admin.isAdmin()) {
            Local local = localOpt.get();
            local.aprovar(admin);
            localRepository.save(local);
        }
    }

    public void rejeitarLocal(Long id, Usuario admin) {
        Optional<Local> localOpt = localRepository.findById(id);
        if (localOpt.isPresent() && admin.isAdmin()) {
            Local local = localOpt.get();
            local.rejeitar(admin);
            localRepository.save(local);
        }
    }

    public void excluir(Long id) {
        localRepository.deleteById(id);
    }
}
