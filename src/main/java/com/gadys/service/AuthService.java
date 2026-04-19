package com.gadys.service;

import com.gadys.dto.LoginRequest;
import com.gadys.dto.LoginResponse;
import com.gadys.model.PasswordResetToken;
import com.gadys.model.Usuario;
import com.gadys.repository.PasswordResetTokenRepository;
import com.gadys.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private static final String RECAPTCHA_SECRET = System.getenv().getOrDefault("RECAPTCHA_SECRET_KEY", "");
    private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

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

        if (Boolean.FALSE.equals(usuario.getAtivo())) {
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

    public LoginResponse loginGoogle(String email, String nome) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        Usuario usuario;
        if (usuarioOpt.isPresent()) {
            usuario = usuarioOpt.get();
            if (Boolean.FALSE.equals(usuario.getAtivo())) {
                return new LoginResponse(false, "Conta inativada. Entre em contato com o administrador.");
            }
            logger.info("✅ LOGIN GOOGLE - Usuário existente: {}", email);
        } else {
            usuario = new Usuario(nome, email, UUID.randomUUID().toString());
            usuario.setTipoUsuario(com.gadys.model.TipoUsuario.USUARIO);
            usuario = usuarioRepository.save(usuario);
            logger.info("✅ LOGIN GOOGLE - Novo usuário criado: {}", email);
        }

        return new LoginResponse(true, "Login realizado com sucesso",
                usuario.getId(), usuario.getNome(), usuario.getTipoUsuario().toString());
    }

    @Transactional
    public LoginResponse esqueciSenha(String email) {
        tokenRepository.deleteByEmail(email);

        if (usuarioRepository.findByEmail(email).isPresent()) {
            String token = UUID.randomUUID().toString();
            tokenRepository.save(new PasswordResetToken(email, token, LocalDateTime.now().plusHours(1)));

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject("Redefinição de senha - GADYS");
            msg.setText("Clique no link para redefinir sua senha:\n\n" +
                "https://gadys-tcc.vercel.app/redefinir-senha?token=" + token +
                "\n\nO link expira em 1 hora.");
            mailSender.send(msg);
        }

        return new LoginResponse(true, "Email enviado com instruções.");
    }

    @Transactional
    public LoginResponse redefinirSenha(String token, String novaSenha) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);

        if (tokenOpt.isEmpty() || LocalDateTime.now().isAfter(tokenOpt.get().getExpiracao())) {
            return new LoginResponse(false, "Link inválido ou expirado.");
        }

        PasswordResetToken resetToken = tokenOpt.get();
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(resetToken.getEmail());

        if (usuarioOpt.isEmpty()) {
            return new LoginResponse(false, "Usuário não encontrado.");
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
        tokenRepository.delete(resetToken);

        return new LoginResponse(true, "Senha redefinida com sucesso.");
    }
}
