package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "songs")
public class Song {

    @Id
    private Long id;

    private String title;
    private String artist;
    private String album;
    private Integer duration;
    private String cover_url;

    @ManyToMany(mappedBy = "likedSongs")
    private List<User> likedByUsers = new ArrayList<>();

    @ManyToMany(mappedBy = "songs")
    private List<Playlist> playlists = new ArrayList<>();
}
