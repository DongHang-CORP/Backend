package org.example.food.service;

import org.assertj.core.api.Assertions;
import org.example.food.domain.user.User;
import org.example.food.domain.user.dto.UserReqDto;
import org.example.food.domain.user.mapper.UserMapper;
import org.example.food.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRepository userRepository;

    @Test
    void getUserById() {
        //given
        //when
        //then
    }

    @Test
    void 회원가입() {
        //given
        UserReqDto user = new UserReqDto();
        user.setEmail("urinaner@naver.com");
        user.setPassword("1234");
        //when
        Long saveId = userService.createUser(user);
        //then
        User findUser = userService.findUserById(saveId);
        Assertions.assertThat(user.getEmail()).isEqualTo(findUser.getEmail());
    }

    @Test
    void updateUser() {
        //given
        //when
        //then
    }

    @Test
    void deleteUser() {
        //given
        //when
        //then
    }

    @Test
    void findUserById() {
    }
}