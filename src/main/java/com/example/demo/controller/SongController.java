package com.example.demo.controller;

import com.example.demo.entity.Song;
import com.example.demo.service.SongService;
import com.example.demo.service.UserService;
import com.example.demo.dto.request.SongCreateRequestDTO;
import com.example.demo.dto.request.SongUpdateRequestDTO;
import com.example.demo.dto.response.SongResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<SongResponseDTO> createSong(@RequestBody SongCreateRequestDTO request) {
        Song newSong = songService.createSong(
                request.getTitle(),
                request.getArtist(),
                request.getAlbum(),
                request.getDurationSeconds()
        );
        return ResponseEntity.ok(convertToSongResponseDTO(newSong));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDTO> getSongById(@PathVariable Long id) {
        Song song = songService.getSongById(id);
        return ResponseEntity.ok(convertToSongResponseDTO(song));
    }

    @GetMapping
    public ResponseEntity<List<SongResponseDTO>> getAllSongs() {
        List<Song> songs = songService.getAllSongs();
        List<SongResponseDTO> songResponseDTOS = songs.stream()
                .map(this::convertToSongResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(songResponseDTOS);
    }

    @PutMapping("/update")
    public ResponseEntity<SongResponseDTO> updateSong(@RequestBody SongUpdateRequestDTO request) {
        Song updatedSong = songService.updateSong(
                request.getId(),
                request.getTitle(),
                request.getArtist(),
                request.getAlbum(),
                request.getDurationSeconds()
        );
        return ResponseEntity.ok(convertToSongResponseDTO(updatedSong));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/like/{songId}")
    public ResponseEntity<String> likeSong(@PathVariable Long userId, @PathVariable Long songId) {
        songService.likeSong(userId, songId);
        return ResponseEntity.ok("Şarkı beğenildi.");
    }

    @DeleteMapping("/{userId}/liked-songs/{songId}")
    public ResponseEntity<Void> unlikeSongs(@PathVariable Long userId, @PathVariable Long songId) {
        songService.unlikeSong(userId, songId);
        return ResponseEntity.noContent().build();
    }

    private SongResponseDTO convertToSongResponseDTO(Song song) {
        SongResponseDTO dto = new SongResponseDTO();
        dto.setId(song.getId());
        dto.setTitle(song.getTitle());
        dto.setArtist(song.getArtist());
        dto.setAlbum(song.getAlbum());
        dto.setDurationSeconds(song.getDuration());
        return dto;
    }
}
