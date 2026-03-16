package com.gadys.service;

import com.gadys.model.Categoria;
import com.gadys.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }
    
    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }
    
    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    
    public boolean existeNome(String nome) {
        return categoriaRepository.existsByNome(nome);
    }
    
    public void excluir(Long id) {
        categoriaRepository.deleteById(id);
    }
}