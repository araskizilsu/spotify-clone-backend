package com.example.demo.service;

import com.example.demo.entity.Song;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface SongService {
    Song createSong(String title, String artist, String album, int durationSeconds);
    Song getSongById(Long id);
    List<Song> getAllSongs();
    Song updateSong(Long id, String title, String artist, String album, int durationSeconds);
    void deleteSong(Long id);
    Song likeSong(Long userId, Long songId);
    Song unlikeSong(Long userId, Long songId);


}