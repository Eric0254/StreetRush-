package com.example.TesteStreetRush.service;

import com.example.TesteStreetRush.model.Cliente;
import com.example.TesteStreetRush.model.EnderecoFaturamento;
import com.example.TesteStreetRush.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Cliente cadastrarClienteComEnderecoFaturamento(Cliente cliente, EnderecoFaturamento enderecoFaturamento) {
        cliente.setEnderecoFaturamento(enderecoFaturamento);
        cliente.setSenha(passwordEncoder.encode(cliente.getSenha())); // Criptografa a senha
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> atualizarCliente(Long id, Cliente novosDadosCliente, EnderecoFaturamento novoEnderecoFaturamento) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);

        if (clienteExistente.isPresent()) {
            Cliente cliente = clienteExistente.get();
            cliente.setNome(novosDadosCliente.getNome());
            cliente.setEmail(novosDadosCliente.getEmail());
            cliente.setCpf(novosDadosCliente.getCpf());
            if(novosDadosCliente.getSenha() != null && !novosDadosCliente.getSenha().isEmpty()) {
                cliente.setSenha(passwordEncoder.encode(novosDadosCliente.getSenha())); // Criptografa a senha
            }
            cliente.setNascimento(novosDadosCliente.getNascimento());
            cliente.setGenero(novosDadosCliente.getGenero());
            cliente.setEnderecoFaturamento(novoEnderecoFaturamento);
            return Optional.of(clienteRepository.save(cliente));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Cliente> login(String email, String senha) {
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(email);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            if (passwordEncoder.matches(senha, cliente.getSenha())) {
                return Optional.of(cliente);
            }
        }
        return Optional.empty();
    }
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

}
