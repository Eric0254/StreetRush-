package com.example.TesteStreetRush.service;

import com.example.TesteStreetRush.model.User;
import com.example.TesteStreetRush.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setNome(user.getNome());
            existingUser.setEmail(user.getEmail());
            existingUser.setCpf(user.getCpf());
            existingUser.setSenha(user.getSenha());
            existingUser.setConfirmarSenha(user.getConfirmarSenha());
            existingUser.setCargo(user.getCargo());
            existingUser.setStatus(user.getStatus());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public String deleteUser(Long id) {
        User existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setStatus("Deletado");
            userRepository.save(existingUser);
            return "Usuário com id " + id + " foi deletado.";
        }
        return "Usuário com id " + id + " não encontrado.";
    }
}
