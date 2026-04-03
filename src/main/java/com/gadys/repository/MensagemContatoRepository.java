package com.gadys.repository;

import com.gadys.model.MensagemContato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MensagemContatoRepository extends JpaRepository<MensagemContato, Long> {
    List<MensagemContato> findByStatus(String status);
}
