package com.example.TesteStreetRush.service;

import com.example.TesteStreetRush.model.EnderecoEntrega;
import com.example.TesteStreetRush.repository.EnderecoEntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoEntregaService {

    @Autowired
    private EnderecoEntregaRepository enderecoEntregaRepository;

    public EnderecoEntrega salvarEndereco(EnderecoEntrega enderecoEntrega) {
        return enderecoEntregaRepository.save(enderecoEntrega);
    }
}
