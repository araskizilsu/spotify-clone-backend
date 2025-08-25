package com.example.demo.service;

import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.entity.User;

public interface AuthService {
    User registerUser(String username, String email, String password);
    User loginUser(LoginRequestDTO request);
}           