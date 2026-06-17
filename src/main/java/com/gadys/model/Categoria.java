package com.gadys.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @ElementCollection
    @CollectionTable(name = "categoria_estados", joinColumns = @JoinColumn(name = "categoria_id"))
    @Column(name = "estado", length = 2)
    private List<String> estados = new ArrayList<>();

    public Categoria() {}
    public Categoria(String nome) { this.nome = nome; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<String> getEstados() { return estados; }
    public void setEstados(List<String> estados) { this.estados = estados; }
    public void addEstado(String estado) { if (!estados.contains(estado)) this.estados.add(estado); }
    public void removeEstado(String estado) { this.estados.remove(estado); }
    public boolean isGlobal() { return estados.isEmpty(); }
}
