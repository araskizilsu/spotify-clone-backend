package com.example.demo.dto.response;

import lombok.Data;

@Data
public class SongResponseDTO {
    private Long id;
    private String title;
    private String artist;
    private String album;
    private int durationSeconds;
}
