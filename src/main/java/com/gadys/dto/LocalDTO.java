package com.gadys.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LocalDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200)
    private String nome;

    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    @NotBlank(message = "Subcategoria é obrigatória")
    private String subcategoria;

    private String cidade;
    private String estado;
    private String endereco;
    private String coordenadas;
    private String horarioFuncionamento;
    private String preco;
    private String informacoesAdicionais;
    private String imagemUrl;
    private String enviadoPor;

    public LocalDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getSubcategoria() { return subcategoria; }
    public void setSubcategoria(String subcategoria) { this.subcategoria = subcategoria; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCoordenadas() { return coordenadas; }
    public void setCoordenadas(String coordenadas) { this.coordenadas = coordenadas; }

    public String getHorarioFuncionamento() { return horarioFuncionamento; }
    public void setHorarioFuncionamento(String horarioFuncionamento) { this.horarioFuncionamento = horarioFuncionamento; }

    public String getPreco() { return preco; }
    public void setPreco(String preco) { this.preco = preco; }

    public String getInformacoesAdicionais() { return informacoesAdicionais; }
    public void setInformacoesAdicionais(String informacoesAdicionais) { this.informacoesAdicionais = informacoesAdicionais; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public String getEnviadoPor() { return enviadoPor; }
    public void setEnviadoPor(String enviadoPor) { this.enviadoPor = enviadoPor; }
}
