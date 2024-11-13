package org.example.food.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    private String role;

    //  @Column
    //    private String provider; //공급자 (google, facebook ...)
    //
    //    @Column
    //    private String providerId; //공급 아이디

//    @Builder
//    public UserEntity(String username, String nickname, String email, String profileImage, String role, String provider, String providerId) {
//        this.username = username;
//        this.nickname = nickname;
//        this.email = email;
//        this.profileImage = profileImage;
//        this.role = role;
//        this.provider = provider;
//        this.providerId = providerId;
//    }
//
//    public UserEntity() {
//
//    }
//
//    public UserEntity update(String name, String picture) {
//        this.name = name;
//        this.picture = picture;
//
//        return this;
//    }
//
//    public String getRoleKey() {
//        return this.userRole.getKey();
//    }
}
