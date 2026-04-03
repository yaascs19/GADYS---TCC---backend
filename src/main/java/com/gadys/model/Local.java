package com.gadys.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Localizacao")
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Lob
    private String descricao;

    @Column(nullable = false, length = 20)
    private String categoria;

    @Column(nullable = false, length = 20)
    private String subcategoria;

    @Column(length = 100)
    private String cidade;

    @Column(length = 100)
    private String estado;

    @Lob
    private String endereco;

    @Column(length = 50)
    private String coordenadas;

    @Column(name = "horario_funcionamento")
    private String horarioFuncionamento;

    @Column(length = 100)
    private String preco;

    @Lob
    private String informacoesAdicionais;

    @Column(name = "imagem_url", length = 500)
    private String imagemUrl;

    @Enumerated(EnumType.STRING)
    private StatusLocal status = StatusLocal.PENDENTE;

    @Column(name = "enviado_por", length = 100)
    private String enviadoPor;

    @ManyToOne
    @JoinColumn(name = "criado_por")
    private Usuario criadoPor;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;

    @ManyToOne
    @JoinColumn(name = "aprovado_por")
    private Usuario aprovadoPor;

    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

    public Local() {}

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

    public StatusLocal getStatus() { return status; }
    public void setStatus(StatusLocal status) { this.status = status; }

    public String getEnviadoPor() { return enviadoPor; }
    public void setEnviadoPor(String enviadoPor) { this.enviadoPor = enviadoPor; }

    public Usuario getCriadoPor() { return criadoPor; }
    public void setCriadoPor(Usuario criadoPor) { this.criadoPor = criadoPor; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAprovacao() { return dataAprovacao; }
    public void setDataAprovacao(LocalDateTime dataAprovacao) { this.dataAprovacao = dataAprovacao; }

    public Usuario getAprovadoPor() { return aprovadoPor; }
    public void setAprovadoPor(Usuario aprovadoPor) { this.aprovadoPor = aprovadoPor; }

    public List<Avaliacao> getAvaliacoes() { return avaliacoes; }
    public void setAvaliacoes(List<Avaliacao> avaliacoes) { this.avaliacoes = avaliacoes; }

    public List<Comentario> getComentarios() { return comentarios; }
    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }

    public void aprovar(Usuario admin) {
        if (admin.isAdmin()) {
            this.status = StatusLocal.ATIVO;
            this.dataAprovacao = LocalDateTime.now();
            this.aprovadoPor = admin;
        }
    }

    public void rejeitar(Usuario admin) {
        if (admin.isAdmin()) {
            this.status = StatusLocal.INATIVO;
        }
    }

    public Double calcularMediaAvaliacoes() {
        return avaliacoes.stream()
            .mapToInt(Avaliacao::getNota)
            .average()
            .orElse(0.0);
    }
}
