package com.example.TesteStreetRush.controller;

import com.example.TesteStreetRush.model.EnderecoEntrega;
import com.example.TesteStreetRush.service.EnderecoEntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endereco-entrega")
public class EnderecoEntregaController {

    @Autowired
    private EnderecoEntregaService enderecoEntregaService;

    @PostMapping("/adicionar")
    public EnderecoEntrega adicionarEndereco(@RequestBody EnderecoEntrega enderecoEntrega) {
        return enderecoEntregaService.salvarEndereco(enderecoEntrega);
    }
}
