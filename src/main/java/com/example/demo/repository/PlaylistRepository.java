package com.example.demo.repository;

import com.example.demo.entity.Playlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
    List<Playlist> findByUserId(Long userId);
}
