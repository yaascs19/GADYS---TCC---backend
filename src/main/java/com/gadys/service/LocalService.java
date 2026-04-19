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
import java.util.stream.Collectors;

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
        List<Local> semCoords = localRepository.findSemCoordenadas();

        int count = 0;
        for (Local local : semCoords) {
            String coords = buscarCoordenadas(local.getEndereco(), local.getCidade(), local.getEstado());
            if (coords != null) {
                local.setCoordenadas(coords);
                localRepository.save(local);
                count++;
            }
            try { Thread.sleep(1100); } catch (InterruptedException ignored) {}
        }
        return count;
    }

    private String buscarCoordenadas(String endereco, String cidade, String estado) {
        String GEOAPIFY_KEY = "b5509ede52e14e848d51c5fbf09f520d";

        String[] tentativas = {
            (endereco != null && !endereco.trim().isEmpty()) ? endereco + ", " + cidade + ", Brasil" : null,
            cidade + ", " + estado + ", Brasil",
        };

        for (String query : tentativas) {
            if (query == null) continue;
            try {
                String url = "https://api.geoapify.com/v1/geocode/search?text="
                    + java.net.URLEncoder.encode(query, "UTF-8")
                    + "&filter=countrycode:br&lang=pt&limit=1&apiKey=" + GEOAPIFY_KEY;

                java.net.URL geoapify = new java.net.URL(url);
                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) geoapify.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) sb.append(line);
                reader.close();

                String body = sb.toString();
                if (body.contains("\"lon\"") && body.contains("\"lat\"")) {
                    String lat = body.split("\"lat\":" )[1].split("[,}]")[0].trim();
                    String lon = body.split("\"lon\":" )[1].split("[,}]")[0].trim();
                    return lat + "," + lon;
                }
            } catch (Exception e) {
                System.err.println("Erro ao geocodificar " + cidade + ": " + e.getMessage());
            }
        }
        return null;
    }
}
