package com.gadys.controller;

import com.gadys.repository.AvaliacaoRepository;
import com.gadys.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ranking")
@CrossOrigin(origins = "*")
public class RankingController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private LocalService localService;

    @GetMapping
    public List<Map<String, Object>> getRanking() {
        return avaliacaoRepository.findRanking().stream().map(row -> {
            Long localId = (Long) row[0];
            Double media = (Double) row[1];
            Long total = (Long) row[2];
            Map<String, Object> map = new HashMap<>();
            localService.buscarPorId(localId).ifPresent(local -> {
                map.put("id", local.getId());
                map.put("nome", local.getNome());
                map.put("cidade", local.getCidade());
                map.put("estado", local.getEstado());
                map.put("media", media);
                map.put("totalAvaliacoes", total);
            });
            return map;
        }).filter(m -> !m.isEmpty()).collect(Collectors.toList());
    }
}
