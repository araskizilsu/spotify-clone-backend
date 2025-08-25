package com.example.demo.service;

import com.example.demo.entity.Playlist;
import java.util.List;
import java.util.Optional;

public interface PlaylistService {
    Playlist createPlaylist(Long userId, String name);
    Playlist addSongToPlaylist(Long playlistId, Long songId);
    Playlist removeSongFromPlaylist(Long playlistId, Long songId);
    void deletePlaylist(Long playlistId);
    List<Playlist> getAllPlaylists();
    Optional<Playlist> getPlaylistById(Long id);
    List<Playlist> getPlaylistsByUserId(Long userId);
}