package com.example.demo.dto.request;

import lombok.Data;
@Data
public class SongCreateRequestDTO {
    private String title;
    private String artist;
    private String album;
    private int durationSeconds;
}
