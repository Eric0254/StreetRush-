package com.example.TesteStreetRush.service;

import com.example.TesteStreetRush.model.Cliente;
import com.example.TesteStreetRush.model.EnderecoFaturamento;
import com.example.TesteStreetRush.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrarClienteComEnderecoFaturamento(Cliente cliente, EnderecoFaturamento enderecoFaturamento) {
        cliente.setEnderecoFaturamento(enderecoFaturamento);
        return clienteRepository.save(cliente);
    }
}
