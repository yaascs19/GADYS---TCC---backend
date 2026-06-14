package com.gadys.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Sugestao")
public class Sugestao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String endereco;
    @Column(length = 2)
    private String estado;
    private String subcategoria;

    @Column(name = "imagem_url")
    private String imagemUrl;

    @Column(name = "enviado_por")
    private String enviadoPor;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    private String status = "PENDENTE";

    @Column(name = "categoria_custom", length = 100)
    private String categoriaCustom;

    @Column(name = "rascunho_conteudo", columnDefinition = "NVARCHAR(MAX)")
    private String rascunhoConteudo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Sugestao() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getSubcategoria() { return subcategoria; }
    public void setSubcategoria(String subcategoria) { this.subcategoria = subcategoria; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public String getEnviadoPor() { return enviadoPor; }
    public void setEnviadoPor(String enviadoPor) { this.enviadoPor = enviadoPor; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCategoriaCustom() { return categoriaCustom; }
    public void setCategoriaCustom(String categoriaCustom) { this.categoriaCustom = categoriaCustom; }

    public String getRascunhoConteudo() { return rascunhoConteudo; }
    public void setRascunhoConteudo(String rascunhoConteudo) { this.rascunhoConteudo = rascunhoConteudo; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}
