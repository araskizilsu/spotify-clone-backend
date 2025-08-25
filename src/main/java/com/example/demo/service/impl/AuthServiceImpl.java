package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User registerUser(String username, String email, String password) {
        Optional<User> existingUserByUsername = userRepository.findByUsername(username);
        if (existingUserByUsername.isPresent()) {
            throw new IllegalArgumentException("Kullanıcı adı zaten alınmış: " + username);
        }

        Optional<User> existingUserByEmail = userRepository.findByEmail(email);
        if (existingUserByEmail.isPresent()) {
            throw new IllegalArgumentException("E-posta zaten kayıtlı: " + email);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }

    @Override
    public User loginUser(LoginRequestDTO request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent() && user.get().getPassword().equals(request.getPassword())) {
            return user.get();
        }
        throw new IllegalArgumentException("Giriş başarısız: Geçersiz kullanıcı adı veya şifre");
    }
}