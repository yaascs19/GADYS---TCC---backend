package com.gadys.repository;

import com.gadys.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByEstadosContaining(String estado);
    List<Categoria> findByEstadosIsEmpty();
    Optional<Categoria> findByNomeIgnoreCase(String nome);
}
