package com.example.demo.service.impl;

import com.example.demo.entity.Song;
import com.example.demo.entity.User;
import com.example.demo.repository.SongRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, UserRepository userRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Song createSong(String title, String artist, String album, int durationSeconds) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Şarkı başlığı boş olamaz");
        }
        if (durationSeconds <= 0) {
            throw new IllegalArgumentException("Şarkı süresi geçerli olmalıdır");
        }

        Song song = new Song();
        song.setTitle(title);
        song.setArtist(artist);
        song.setAlbum(album);
        song.setDuration(durationSeconds);

        return songRepository.save(song);
    }

    @Override
    public Song getSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Şarkı bulunamadı, id: " + id));
    }

    @Override
    public List<Song> getAllSongs() {
        return (List<Song>) songRepository.findAll();
    }

    @Override
    @Transactional
    public Song updateSong(Long id, String title, String artist, String album, int durationSeconds) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Şarkı bulunamadı, id: " + id));

        if (title != null && !title.trim().isEmpty()) {
            song.setTitle(title);
        }
        if (artist != null) {
            song.setArtist(artist);
        }
        if (album != null) {
            song.setAlbum(album);
        }
        if (durationSeconds > 0) {
            song.setDuration(durationSeconds);
        } else {
            throw new IllegalArgumentException("Şarkı süresi geçerli olmalıdır");
        }

        return songRepository.save(song);
    }

    @Override
    @Transactional
    public void deleteSong(Long id) {
        if (!songRepository.existsById(id)) {
            throw new IllegalArgumentException("Şarkı bulunamadı, id: " + id);
        }
        songRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Song likeSong(Long userId, Long songId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı, id: " + userId));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Şarkı bulunamadı, id: " + songId));

        if (!song.getLikedByUsers().contains(user)) {
            song.getLikedByUsers().add(user);
            songRepository.save(song);
        }

        return song;
    }

    @Override
    @Transactional
    public Song unlikeSong(Long userId, Long songId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı, id: " + userId));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Şarkı bulunamadı, id: " + songId));

        song.getLikedByUsers().remove(user);
        return songRepository.save(song);
    }
}