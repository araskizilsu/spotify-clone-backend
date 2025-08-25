package com.example.demo.service.impl;

import com.example.demo.entity.Playlist;
import com.example.demo.entity.Song;
import com.example.demo.entity.User;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.SongRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    @Override
    @Transactional
    public Playlist createPlaylist(Long userId, String name) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı, id: " + userId));

        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setUser(user);
        return playlistRepository.save(playlist);
    }

    @Override
    @Transactional
    public Playlist addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Çalma listesi bulunamadı, id: " + playlistId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Şarkı bulunamadı, id: " + songId));

        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
            return playlistRepository.save(playlist);
        }
        return playlist;
    }

    @Override
    @Transactional
    public Playlist removeSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Çalma listesi bulunamadı, id: " + playlistId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Şarkı bulunamadı, id: " + songId));

        playlist.getSongs().remove(song);
        return playlistRepository.save(playlist);
    }

    @Override
    @Transactional
    public void deletePlaylist(Long playlistId) {
        if (!playlistRepository.existsById(playlistId)) {
            throw new IllegalArgumentException("Çalma listesi bulunamadı, id: " + playlistId);
        }
        playlistRepository.deleteById(playlistId);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return (List<Playlist>) playlistRepository.findAll();
    }

    @Override
    public Optional<Playlist> getPlaylistById(Long id) {
        return playlistRepository.findById(id);
    }

    @Override
    public List<Playlist> getPlaylistsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Kullanıcı bulunamadı, id: " + userId);
        }
        return playlistRepository.findByUserId(userId);
    }
}