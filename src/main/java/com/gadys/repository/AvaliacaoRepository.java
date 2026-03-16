package com.gadys.repository;

import com.gadys.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByLocalId(Long localId);
    Optional<Avaliacao> findByLocalIdAndUsuarioId(Long localId, Long usuarioId);
    
    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.local.id = ?1")
    Double findMediaByLocalId(Long localId);
}