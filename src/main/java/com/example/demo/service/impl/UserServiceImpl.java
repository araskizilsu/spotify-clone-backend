package com.example.demo.service.impl;

import com.example.demo.entity.Song;
import com.example.demo.entity.User;
import com.example.demo.repository.SongRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public User createUser(String username, String email, String password) {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, String username, String email, String password) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Kullanici bulunamadı");
        }

        User user = userOpt.get();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }

    public void likeSong(Long userId, Long songId) {
        Optional<User> userOpt = userRepository.findById(songId);

        if (userOpt.isEmpty()){
            throw  new RuntimeException("Kullanici bulunamadi");
        }

        Optional<Song> songOpt = songRepository.findById(songId);

        if (songOpt.isEmpty())
             throw new RuntimeException("Şarki bulunamadi");

        User user = userOpt.get();
        Song song = songOpt.get();

        user.getLikedSongs().add(song);

        userRepository.save(user);
    }


    public User getUser(Long id){
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()){
            throw new RuntimeException("Kullanici bulunamadi");
        }
        return  userOpt.get();

    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
    public  User getteUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanici bulunamadi"));
    }
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanici bulunamadi"));
    }

}
