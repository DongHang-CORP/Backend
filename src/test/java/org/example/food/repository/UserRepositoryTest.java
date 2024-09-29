package org.example.food.repository;

import org.example.food.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 저장 테스트")
    void 유저_저장_테스트() {
        // given
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("이메일로 유저 조회 테스트")
    void 이메일로_유저_조회_테스트() {
        // given
        User user = new User();
        user.setEmail("findme@example.com");
        user.setUsername("findmeuser");
        userRepository.save(user);

        // when
        Optional<User> foundUser = userRepository.findByEmail("findme@example.com");

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("findme@example.com");
    }

    @Test
    @DisplayName("ID로 유저 조회 테스트")
    void 아이디로_유저_조회_테스트() {
        // given
        User user = new User();
        user.setEmail("userbyid@example.com");
        user.setUsername("userbyid");
        User savedUser = userRepository.save(user);

        // when
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("userbyid@example.com");
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    void 유저_삭제_테스트() {
        // given
        User user = new User();
        user.setEmail("deletetest@example.com");
        user.setUsername("deletetest");
        User savedUser = userRepository.save(user);

        // when
        userRepository.deleteById(savedUser.getId());

        // then
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertThat(deletedUser).isNotPresent();
    }

    @Test
    @DisplayName("존재하지 않는 유저 삭제 시 예외 발생 테스트")
    void 존재하지_않는_유저_삭제_테스트() {
        // given
        Long nonExistentUserId = 999L;

        // when & then
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userRepository.deleteById(nonExistentUserId);
        });
    }

    @Test
    @DisplayName("유저 이름으로 조회 테스트")
    void 유저_이름으로_조회_테스트() {
        // given
        User user = new User();
        user.setEmail("namequery@example.com");
        user.setUsername("namequeryuser");
        userRepository.save(user);

        // when
        Optional<User> foundUser = userRepository.findByUsername("namequeryuser");

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("namequeryuser");
    }
}