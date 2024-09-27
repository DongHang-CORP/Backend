package org.example.food.service;

import org.assertj.core.api.Assertions;
import org.example.food.domain.user.User;
import org.example.food.domain.user.dto.LoginDto;
import org.example.food.domain.user.dto.UserReqDto;
import org.example.food.domain.user.dto.UserResDto;
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
    void 회원가입() {
        //given
        UserReqDto user = createDummyUser();
        //when
        Long saveId = userService.createUser(user);
        //then
        User findUser = userService.findUserById(saveId);
        Assertions.assertThat(user.getEmail()).isEqualTo(findUser.getEmail());
    }

    @Test
    void 로그인_이메일_존재(){
        //given
        UserReqDto user = createDummyUser();
        userService.createUser(user);
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("urinaner@naver.com");
        //when
        UserResDto userResDto = userService.findUserByEmail(loginDto.getEmail());
        //then
        Assertions.assertThat(loginDto.getEmail()).isEqualTo(userResDto.getEmail());
    }

    private UserReqDto createDummyUser(){
        UserReqDto user = new UserReqDto();
        user.setEmail("urinaner@naver.com");
        user.setNickname("jyj");
        user.setUsername("장영재");
        return user;
    }

}