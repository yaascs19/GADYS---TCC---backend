package com.gadys.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PasswordResetToken")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiracao;

    public PasswordResetToken() {}

    public PasswordResetToken(String email, String token, LocalDateTime expiracao) {
        this.email = email;
        this.token = token;
        this.expiracao = expiracao;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
    public LocalDateTime getExpiracao() { return expiracao; }
}
