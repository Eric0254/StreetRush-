package com.example.TesteStreetRush.model;

import jakarta.persistence.*;

@Entity
@Table(name = "imagens")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String caminho;
    private boolean principal;

    public Imagem(int id, String nome, String caminho, boolean principal) {
        this.id = id;
        this.nome = nome;
        this.caminho = caminho;
        this.principal = principal;
    }

    public Imagem() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
