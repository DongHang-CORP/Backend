package org.example.food.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
