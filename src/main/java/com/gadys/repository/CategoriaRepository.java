package com.gadys.repository;

import com.gadys.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByEstado(String estado);
    List<Categoria> findByEstadoIsNull();
    boolean existsByNomeIgnoreCaseAndEstado(String nome, String estado);
}
