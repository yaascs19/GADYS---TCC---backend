package com.gadys.repository;

import com.gadys.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByEstado(String estado);
    List<Categoria> findByEstadoIsNull();
    boolean existsByNomeIgnoreCaseAndEstado(String nome, String estado);

    @Transactional
    void deleteByNomeIgnoreCaseAndEstado(String nome, String estado);
}
