package org.example.food.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.food.domain.video.Video;

import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String profileImage;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos;

    public User() {}

    public User(Long id, String username, String profileImage, String password, String email, List<Video> videos) {
        this.id = id;
        this.username = username;
        this.profileImage = profileImage;
        this.password = password;
        this.email = email;
        this.videos = videos;
    }
}
