package com.gadys.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Favorito", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"usuario_id", "Localizacao_id"})
})
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "Localizacao_id", nullable = false)
    private Local local;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Favorito() {}

    public Favorito(Usuario usuario, Local local) {
        this.usuario = usuario;
        this.local = local;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Local getLocal() { return local; }
    public void setLocal(Local local) { this.local = local; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}
