package com.example.TesteStreetRush.controller;

import com.example.TesteStreetRush.model.Cliente;
import com.example.TesteStreetRush.model.ClienteCadastroWrapper;
import com.example.TesteStreetRush.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastro")
    public ResponseEntity<Cliente> cadastrarClienteComEnderecoFaturamento(@RequestBody ClienteCadastroWrapper wrapper) {
        Cliente novoCliente = clienteService.cadastrarClienteComEnderecoFaturamento(wrapper.getCliente(), wrapper.getEnderecoFaturamento());
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
    }
}
