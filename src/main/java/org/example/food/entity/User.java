package org.example.food.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String profileImage;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    public User() {}

    public User(Long id, String username, String profileImage, String password, String email) {
        this.id = id;
        this.username = username;
        this.profileImage = profileImage;
        this.password = password;
        this.email = email;
    }
}
