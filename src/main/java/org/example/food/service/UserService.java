package org.example.food.service;

import org.example.food.domain.user.User;
import org.example.food.domain.user.dto.UserReqDto;
import org.example.food.domain.user.dto.UserResDto;

public interface UserService {
    UserResDto getUserById(Long id);
    Long createUser(UserReqDto userReqDto);
    UserResDto updateUser(Long id, UserReqDto userReqDto);
    void deleteUser(Long id);
    User findUserById(Long id);
    Long findUserByEmail(String email);
}
