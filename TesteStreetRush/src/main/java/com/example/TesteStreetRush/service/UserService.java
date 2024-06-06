package com.example.TesteStreetRush.service;

import com.example.TesteStreetRush.model.User;
import com.example.TesteStreetRush.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setNome(user.getNome());
            existingUser.setEmail(user.getEmail());
            existingUser.setCpf(user.getCpf());
            if(user.getSenha() != null && !user.getSenha().isEmpty()) {
                existingUser.setSenha(passwordEncoder.encode(user.getSenha()));
            }
            if(user.getSenha() != null && !user.getSenha().isEmpty()) {
                existingUser.setConfirmarSenha(passwordEncoder.encode(user.getConfirmarSenha()));
            }
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

    public User updateUserStatus(Long id, String status) {
        User existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setStatus(status);
            return userRepository.save(existingUser);
        }
        return null;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
