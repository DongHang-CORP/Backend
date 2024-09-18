package org.example.food.entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String profileImage;
    private String password;
    private String email;
    public User(Long id, String username, String profileImage, String password, String email) {
        this.id = id;
        this.username = username;
        this.profileImage = profileImage;
        this.password = password;
        this.email = email;
    }


}
