package com.gadys.repository;

import com.gadys.model.Local;
import com.gadys.model.StatusLocal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
    List<Local> findByStatus(StatusLocal status);
    List<Local> findByCategoria(String categoria);
    List<Local> findBySubcategoria(String subcategoria);
    List<Local> findByCidade(String cidade);
    List<Local> findByEstado(String estado);

    @Query("SELECT l FROM Local l WHERE l.nome LIKE %?1%")
    List<Local> findByNomeContaining(String nome);
    Optional<Local> findByRotaFrontend(String rotaFrontend);
}
