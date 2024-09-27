package org.example.food.repository;

import org.assertj.core.api.Assertions;
import org.example.food.domain.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MemoryUserRepositoryTest {

    MemoryUserRepository userRepository = new MemoryUserRepository();

    @AfterEach
    void setUp() {
        userRepository.clearStore();
    }
    @Test
    void save() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("박민수");
        //when
        User savedUser = userRepository.save(user);
        User findUser = userRepository.findById(1L);
        //then
        Assertions.assertThat(savedUser).isEqualTo(findUser);
    }

    @Test
    void findById() {
        //given
        User user = new User();
        user.setId(2L);
        user.setUsername("박민수");
        userRepository.save(user);
        //when
        User foundUser = userRepository.findById(2L);
        //then
        Assertions.assertThat(user).isEqualTo(foundUser);
    }

    @Test
    void deleteUser() {
        //given
        User user = new User();
        user.setId(2L);
        user.setUsername("박민수");
        userRepository.save(user);
        // When
        userRepository.deleteUser(3L);
        // Then
        Assertions.assertThat(userRepository.findById(3L)).isNull();
    }
}