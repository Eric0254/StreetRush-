package com.example.TesteStreetRush.service;

import com.example.TesteStreetRush.model.Cliente;
import com.example.TesteStreetRush.model.EnderecoFaturamento;
import com.example.TesteStreetRush.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrarClienteComEnderecoFaturamento(Cliente cliente, EnderecoFaturamento enderecoFaturamento) {
        cliente.setEnderecoFaturamento(enderecoFaturamento);
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> atualizarCliente(Long id, Cliente novosDadosCliente, EnderecoFaturamento novoEnderecoFaturamento) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);

        if (clienteExistente.isPresent()) {
            Cliente cliente = clienteExistente.get();
            cliente.setNome(novosDadosCliente.getNome());
            cliente.setEmail(novosDadosCliente.getEmail());
            cliente.setCpf(novosDadosCliente.getCpf());
            cliente.setSenha(novosDadosCliente.getSenha());
            cliente.setNascimento(novosDadosCliente.getNascimento());
            cliente.setGenero(novosDadosCliente.getGenero());
            cliente.setEnderecoFaturamento(novoEnderecoFaturamento);
            return Optional.of(clienteRepository.save(cliente));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Cliente> login(String email, String senha) {
        return clienteRepository.findByEmailAndSenha(email, senha);
    }
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

}
