package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.user.User;
import org.example.food.domain.user.dto.UserReqDto;
import org.example.food.domain.user.dto.UserResDto;
import org.example.food.domain.user.mapper.UserMapper;
import org.example.food.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResDto getUserById(Long id) {
        User user = findUserById(id);
        return userMapper.toUserDto(user);
    }

    @Override
    public Long createUser(UserReqDto userReqDto) {
        User user = userMapper.toEntity(userReqDto);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public UserResDto updateUser(Long id, UserReqDto userReqDto) {
        User user = findUserById(id);
        userMapper.updateUserFromDto(userReqDto, user);
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }

    @Override
    public UserResDto findUserByEmail(String email){
        User findUser = userRepository.findByEmail(email);
        return userMapper.toUserDto(findUser);
    }
}
