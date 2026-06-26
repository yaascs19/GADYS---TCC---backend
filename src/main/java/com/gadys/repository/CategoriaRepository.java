package com.gadys.repository;

import com.gadys.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query("SELECT c FROM Categoria c WHERE c.estados IS EMPTY")
    List<Categoria> findGlobais();

    @Query("SELECT c FROM Categoria c JOIN c.estados e WHERE e = :estado")
    List<Categoria> findByEstado(@Param("estado") String estado);

    Optional<Categoria> findByNomeIgnoreCase(String nome);
}
