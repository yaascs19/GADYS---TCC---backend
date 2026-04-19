package com.gadys.controller;

import com.gadys.dto.LoginRequest;
import com.gadys.dto.LoginResponse;
import com.gadys.model.Usuario;
import com.gadys.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
    origins = "*",
    methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
    }
)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<LoginResponse> cadastrar(@RequestBody Usuario usuario) {
        LoginResponse response = authService.cadastrar(usuario);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/google")
    public ResponseEntity<LoginResponse> loginGoogle(@RequestBody Map<String, String> body) {
        LoginResponse response = authService.loginGoogle(body.get("email"), body.get("nome"));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<LoginResponse> esqueciSenha(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(authService.esqueciSenha(body.get("email")));
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<LoginResponse> redefinirSenha(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(authService.redefinirSenha(body.get("token"), body.get("novaSenha")));
    }
}