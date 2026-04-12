package com.gadys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", length = 10)
    private TipoUsuario tipoUsuario = TipoUsuario.USUARIO;

    @Column(name = "status_usuario", length = 10)
    private String ativo = "ATIVO";

    @Column(name = "ultimo_acesso")
    private LocalDateTime ultimoAcesso;

    @Column(name = "total_acesso")
    private Integer totalAcessos = 0;

    @Column(name = "ip_acesso", length = 45)
    private String ipAcesso;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro = LocalDateTime.now();

    @OneToMany(mappedBy = "criadoPor")
    private List<Local> locaisCriados = new ArrayList<>();

    public Usuario() {}

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this
