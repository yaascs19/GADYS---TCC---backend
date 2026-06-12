package com.gadys.repository;

import com.gadys.model.Sugestao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SugestaoRepository extends JpaRepository<Sugestao, Long> {
    List<Sugestao> findByStatus(String status);
}
