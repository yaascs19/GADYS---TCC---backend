package com.gadys.repository;

import com.gadys.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Optional<Estado> findBySigla(String sigla);
    boolean existsBySigla(String sigla);
}