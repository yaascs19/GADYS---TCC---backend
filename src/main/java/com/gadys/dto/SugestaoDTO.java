package com.gadys.dto;

public class SugestaoDTO {

    private String nome;
    private String descricao;
    private String endereco;
    private String estado;
    private String subcategoria;
    private String imagemUrl;
    private String enviadoPor;
    private Integer usuarioId;
    private String status;
    private String categoriaCustom;
    private String rascunhoConteudo;

    public SugestaoDTO() {}

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

    public String getCategoriaCustom() { return categoriaCustom; }
    public void setCategoriaCustom(String categoriaCustom) { this.categoriaCustom = categoriaCustom; }

    public String getRascunhoConteudo() { return rascunhoConteudo; }
    public void setRascunhoConteudo(String rascunhoConteudo) { this.rascunhoConteudo = rascunhoConteudo; }
}
