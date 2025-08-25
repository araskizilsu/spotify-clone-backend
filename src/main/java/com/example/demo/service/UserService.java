package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User updateUser(Long id, String username, String email, String password);
}
