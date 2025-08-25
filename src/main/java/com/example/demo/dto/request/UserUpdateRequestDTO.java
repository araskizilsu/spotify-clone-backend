package com.example.demo.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequestDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
}
