package com.gadys.repository;

import com.gadys.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuarioId(Long usuarioId);
    Optional<Favorito> findByUsuarioIdAndLocalId(Long usuarioId, Long localId);

    @Transactional
    void deleteByUsuarioIdAndLocalId(Long usuarioId, Long localId);
}
