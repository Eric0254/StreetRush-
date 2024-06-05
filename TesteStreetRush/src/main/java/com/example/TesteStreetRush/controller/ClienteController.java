package com.example.TesteStreetRush.controller;

import com.example.TesteStreetRush.model.Cliente;
import com.example.TesteStreetRush.model.ClienteCadastroWrapper;
import com.example.TesteStreetRush.model.EnderecoEntrega;
import com.example.TesteStreetRush.model.EnderecoEntregaWrapper;
import com.example.TesteStreetRush.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody ClienteCadastroWrapper wrapper) {
        Optional<Cliente> clienteAtualizado = clienteService.atualizarCliente(id, wrapper.getCliente(), wrapper.getEnderecoFaturamento());
        return clienteAtualizado.map(cliente -> new ResponseEntity<>(cliente, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/login")
    public ResponseEntity<Cliente> loginCliente(@RequestBody Cliente cliente) {
        Optional<Cliente> clienteLogado = clienteService.login(cliente.getEmail(), cliente.getSenha());
        return clienteLogado.map(cli -> new ResponseEntity<>(cli, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @PostMapping("/adicionar-endereco-entrega")
    public ResponseEntity<Cliente> adicionarEnderecoEntrega(@RequestBody EnderecoEntregaWrapper wrapper) {
        Long clienteId = wrapper.getClienteId();
        EnderecoEntrega enderecoEntrega = wrapper.getEnderecoEntrega();

        if (enderecoEntrega == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Cliente> clienteOpt = clienteService.findById(clienteId);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            enderecoEntrega.setCliente(cliente); // Configura a associação bidirecional
            cliente.setEnderecoEntrega(enderecoEntrega);
            clienteService.save(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
