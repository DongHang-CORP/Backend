package org.example.food.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.food.domain.video.Video;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String profileImage;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos;

    public User() {}

    public User(Long id, String username, String nickname, String profileImage, String email, List<Video> videos) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.email = email;
        this.videos = videos;
    }
}
