package org.example.food.repository;

import org.example.food.user.entity.User;
import org.example.food.testbase.RepositoryTest;
import org.example.food.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        // 공통 유저 생성 로직
        final String email = "cheese10yun@gmail.com";
        final String username = "testuser";
        final String nickname = "nickname";

        // 유저를 저장하고, 이후 테스트에서 사용할 수 있도록 필드에 저장
        savedUser = userRepository.save(createUser(email, username, nickname));
    }

    @Test
    @DisplayName("유저 저장 테스트")
    void 유저_저장_테스트() {
        // 저장된 유저 검증
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("cheese10yun@gmail.com");
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("이메일로 유저 조회 테스트")
    void 이메일로_유저_조회_테스트() {
        // 이메일로 유저 조회
        User foundUser = userRepository.findByEmail(savedUser.getEmail());

        // 조회된 유저 검증
        assertThat(foundUser.getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    @DisplayName("ID로 유저 조회 테스트")
    void 아이디로_유저_조회_테스트() {
        // ID로 유저 조회
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // 조회된 유저 검증
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    void 유저_삭제_테스트() {
        // 유저 삭제
        userRepository.deleteById(savedUser.getId());

        // 삭제 후 조회 검증
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertThat(deletedUser).isNotPresent();
    }


    @Test
    @DisplayName("유저 이름으로 조회 테스트")
    void 유저_이름으로_조회_테스트() {
        // 유저 이름으로 조회
        User foundUser = userRepository.findByUsername(savedUser.getUsername());

        // 조회된 유저 검증
        assertThat(foundUser.getUsername()).isEqualTo(savedUser.getUsername());
    }

    // 유저 생성 유틸리티 메서드
    private User createUser(String email, String username, String nickname) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setNickname(nickname);
        return user;
    }
}
