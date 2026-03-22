package com.gadys.controller;

import com.gadys.dto.LoginRequest;
import com.gadys.dto.LoginResponse;
import com.gadys.model.Usuario;
import com.gadys.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
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
}
