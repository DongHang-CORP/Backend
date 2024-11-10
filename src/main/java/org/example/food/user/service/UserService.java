package org.example.food.user.service;

import org.example.food.user.entity.User;
import org.example.food.user.dto.UserReqDto;
import org.example.food.user.dto.UserResDto;

public interface UserService {
    UserResDto getUserById(Long id);

    Long createUser(UserReqDto userReqDto);

    UserResDto updateUser(Long id, UserReqDto userReqDto);

    void deleteUser(Long id);

    User findUserById(Long id);

    Long findUserByEmail(String email);
}
