package org.example.food.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 ID 설정
    private Long id;

    @Column(nullable = false, unique = true) // 이메일 또는 사용자명에 대해 유니크 제약 조건 추가
    private String username;

    private String profileImage;

    @Column(nullable = false) // 비밀번호는 필수
    private String password;

    @Column(nullable = false, unique = true) // 이메일은 필수, 유니크 제약 조건 추가
    private String email;

    public User() {} // 기본 생성자는 JPA에서 필수입니다.

    public User(Long id, String username, String profileImage, String password, String email) {
        this.id = id;
        this.username = username;
        this.profileImage = profileImage;
        this.password = password;
        this.email = email;
    }
}
