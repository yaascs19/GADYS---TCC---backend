package com.gadys.service;

import com.gadys.dto.LoginRequest;
import com.gadys.dto.LoginResponse;
import com.gadys.model.Usuario;
import com.gadys.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private static final String RECAPTCHA_SECRET = System.getenv().getOrDefault("RECAPTCHA_SECRET_KEY", "");
    private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";

    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private RestTemplate restTemplate = new RestTemplate();

    private boolean validarRecaptcha(String token) {
        if (token == null || token.isBlank()) return false;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("secret", RECAPTCHA_SECRET);
            params.add("response", token);

            Map response = restTemplate.postForObject(RECAPTCHA_URL, params, Map.class);
            if (response == null) return false;

            boolean success = Boolean.TRUE.equals(response.get("success"));
            double score = response.get("score") != null ? ((Number) response.get("score")).doubleValue() : 0.0;

            return success && score >= 0.5;
        } catch (Exception e) {
            logger.error("Erro ao validar reCAPTCHA: {}", e.getMessage());
            return false;
        }
    }

    public LoginResponse login(LoginRequest request) {
        if (!validarRecaptcha(request.getRecaptchaToken())) {
            logger.warn("❌ LOGIN BLOQUEADO - reCAPTCHA inválido");
            return new LoginResponse(false, "Verificação de segurança falhou.");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());

        if (usuarioOpt.isEmpty()) {
            logger.warn("❌ LOGIN FALHOU - Email não encontrado: {}", request.getEmail());
            return new LoginResponse(false, "Email não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        if ("INATIVO".equals(usuario.getAtivo())) {
            logger.warn("❌ LOGIN FALHOU - Conta inativada: {}", request.getEmail());
            return new LoginResponse(false, "Conta inativada. Entre em contato com o administrador.");
        }

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            logger.warn("❌ LOGIN FALHOU - Senha incorreta para: {}", request.getEmail());
            return new LoginResponse(false, "Senha incorreta");
        }

        logger.info("✅ LOGIN REALIZADO COM SUCESSO!");
        logger.info("👤 Usuário: {} ({})", usuario.getNome(), usuario.getEmail());
        logger.info("===========================================");

        return new LoginResponse(true, "Login realizado com sucesso",
                usuario.getId(), usuario.getNome(), usuario.getTipoUsuario().toString());
    }

    public LoginResponse cadastrar(Usuario usuario) {
        if (!validarRecaptcha(usuario.getRecaptchaToken())) {
            logger.warn("❌ CADASTRO BLOQUEADO - reCAPTCHA inválido");
            return new LoginResponse(false, "Verificação de segurança falhou.");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            logger.warn("❌ CADASTRO REJEITADO - Email já existe: {}", usuario.getEmail());
            return new LoginResponse(false, "Email já cadastrado");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        logger.info("✅ NOVO USUÁRIO CADASTRADO!");
        logger.info("👤 Nome: {}", usuarioSalvo.getNome());
        logger.info("📧 Email: {}", usuarioSalvo.getEmail());
        logger.info("🏷️ Tipo: {}", usuarioSalvo.getTipoUsuario());
        logger.info("🆔 ID: {}", usuarioSalvo.getId());
        logger.info("===========================================");

        return new LoginResponse(true, "Usuário cadastrado com sucesso",
                usuarioSalvo.getId(), usuarioSalvo.getNome(), usuarioSalvo.getTipoUsuario().toString());
    }
}
