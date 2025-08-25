package com.example.demo.dto.response;

import lombok.Data;
import java.util.List;
import com.example.demo.entity.Song;

@Data
public class PlaylistResponseDTO {
    private Long id;
    private String playlistName;
    private Long userId;
    private List<Song> songs;
}
