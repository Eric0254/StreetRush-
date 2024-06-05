package com.example.TesteStreetRush.model;

public class EnderecoEntregaWrapper {
    private EnderecoEntrega enderecoEntrega;
    private Long clienteId;

    // Getters e Setters
    public EnderecoEntrega getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}
