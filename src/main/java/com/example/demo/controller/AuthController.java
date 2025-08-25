package com.example.demo.controller;

import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService; // UserService'i import etmeyi unutmayın
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestParam String username,
                                             @RequestParam String email,
                                             @RequestParam String password) {
        User registeredUser = authService.registerUser(username, email, password);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> getUser(@RequestBody LoginRequestDTO request) {
        User user = authService.loginUser(request);
        LoginResponseDTO response = LoginResponseDTO.builder().user(user).build();
        return ResponseEntity.ok(response);
    }

}
