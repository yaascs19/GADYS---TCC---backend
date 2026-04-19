package com.gadys.service;

import com.gadys.dto.LocalDTO;
import com.gadys.model.Local;
import com.gadys.model.StatusLocal;
import com.gadys.model.Usuario;
import com.gadys.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;

    public List<Local> listarTodos() {
        return localRepository.findAll();
    }

    public List<Local> listarAtivos() {
        return localRepository.findByStatus(StatusLocal.ATIVO);
    }

    public List<Local> listarPendentes() {
        return localRepository.findByStatus(StatusLocal.PENDENTE);
    }

    public List<Local> listarAprovados() {
        return localRepository.findByStatus(StatusLocal.ATIVO);
    }

    public Optional<Local> buscarPorId(Long id) {
        return localRepository.findById(id);
    }

    public List<Local> buscarPorCategoria(String categoria) {
        return localRepository.findByCategoria(categoria);
    }

    public List<Local> buscarPorSubcategoria(String subcategoria) {
        return localRepository.findBySubcategoria(subcategoria);
    }

    public List<Local> buscarPorCidade(String cidade) {
        return localRepository.findByCidade(cidade);
    }

    public List<Local> buscarPorEstado(String estado) {
        return localRepository.findByEstado(estado);
    }

    public List<Local> buscarPorNome(String nome) {
        return localRepository.findByNomeContaining(nome);
    }

    public Optional<Local> buscarPorRotaFrontend(String rota) {
        return localRepository.findByRotaFrontend(rota);
    }

    public Local salvar(Local local) {
        return localRepository.save(local);
    }

    public Local atualizar(Long id, LocalDTO dto) {
        return localRepository.findById(id).map(local -> {
            local.setNome(dto.getNome());
            local.setDescricao(dto.getDescricao());
            local.setCategoria(dto.getCategoria());
            local.setSubcategoria(dto.getSubcategoria());
            local.setCidade(dto.getCidade());
            local.setEstado(dto.getEstado());
            local.setEndereco(dto.getEndereco());
            local.setCoordenadas(dto.getCoordenadas());
            local.setHorarioFuncionamento(dto.getHorarioFuncionamento());
            local.setPreco(dto.getPreco());
            local.setInformacoesAdicionais(dto.getInformacoesAdicionais());
            local.setImagemUrl(dto.getImagemUrl());
            local.setEnviadoPor(dto.getEnviadoPor());
            return localRepository.save(local);
        }).orElseThrow(() -> new RuntimeException("Local não encontrado"));
    }

    public void aprovarLocal(Long id, Usuario admin) {
        localRepository.findById(id).ifPresent(local -> {
            local.aprovar(admin);
            localRepository.save(local);
        });
    }

    public void rejeitarLocal(Long id, Usuario admin) {
        localRepository.findById(id).ifPresent(local -> {
            local.rejeitar(admin);
            localRepository.save(local);
        });
    }

    public void excluir(Long id) {
        localRepository.deleteById(id);
    }

    public int geocodificarLocaisSemCoordenadas() {
        List<Local> locais = localRepository.findAll().stream()
            .filter(l -> l.getCoordenadas() == null || l.getCoordenadas().isBlank())
            .collect(java.util.stream.Collectors.toList());

        int count = 0;
        for (Local local : locais) {
            String coords = buscarCoordenadas(local.getEndereco(), local.getCidade(), local.getEstado());
            if (coords != null) {
                local.setCoordenadas(coords);
                localRepository.save(local);
                count++;
            }
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }
        return count;
    }

    private String buscarCoordenadas(String endereco, String cidade, String estado) {
        try {
            String query = (endereco != null && !endereco.isBlank())
                ? endereco + ", " + cidade + ", Brasil"
                : cidade + ", " + estado + ", Brasil";

            String url = "https://nominatim.openstreetmap.org/search?format=json&limit=1&q="
                + java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8);

            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(url))
                .header("User-Agent", "GADYS-TCC/1.0")
                .build();

            java.net.http.HttpResponse<String> response = client.send(
                request, java.net.http.HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            if (body.contains("\"lat\"")) {
                String lat = body.split("\"lat\":\"" )[1].split("\"")[0];
                String lon = body.split("\"lon\":\"" )[1].split("\"")[0];
                return lat + "," + lon;
            }
        } catch (Exception e) {
            System.err.println("Erro ao geocodificar: " + e.getMessage());
        }
        return null;
    }
}
