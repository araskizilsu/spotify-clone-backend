package com.example.demo.controller;

import com.example.demo.entity.Playlist;
import com.example.demo.entity.Song;
import com.example.demo.service.PlaylistService;
import com.example.demo.dto.request.PlaylistCreateRequestDTO;
import com.example.demo.dto.response.PlaylistResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping("/{userId}")
    public ResponseEntity<PlaylistResponseDTO> createPlaylist(@PathVariable Long userId,
                                                              @RequestBody PlaylistCreateRequestDTO request) {
        Playlist newPlaylist = playlistService.createPlaylist(userId, request.getPlaylistName());
        return ResponseEntity.ok(convertToPlaylistResponseDTO(newPlaylist));
    }

    @DeleteMapping("/{userId}/playlists/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long userId, @PathVariable Long playlistId) {
        playlistService.deletePlaylist(playlistId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistService.addSongToPlaylist(playlistId, songId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistService.removeSongFromPlaylist(playlistId, songId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponseDTO>> getAllPlaylists() {
        List<Playlist> playlists = playlistService.getAllPlaylists();
        List<PlaylistResponseDTO> playlistResponseDTOS = playlists.stream()
                .map(this::convertToPlaylistResponseDTO)
                .collect(Collectors.toList());       
        return ResponseEntity.ok(playlistResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> getPlaylistById(@PathVariable Long id) {
        Optional<Playlist> playlist = playlistService.getPlaylistById(id);
        return playlist.map(this::convertToPlaylistResponseDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PlaylistResponseDTO>> getPlaylistsByUserId(@PathVariable Long userId) {
        List<Playlist> playlists = playlistService.getPlaylistsByUserId(userId);
        List<PlaylistResponseDTO> playlistResponseDTOS = playlists.stream()
                .map(this::convertToPlaylistResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playlistResponseDTOS);
    }

    private PlaylistResponseDTO convertToPlaylistResponseDTO(Playlist playlist) {
        PlaylistResponseDTO dto = new PlaylistResponseDTO();
        dto.setId(playlist.getId());
        dto.setPlaylistName(playlist.getName());

        return dto;
    }
}
