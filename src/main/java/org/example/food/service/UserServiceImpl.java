package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.user.User;
import org.example.food.domain.user.dto.UserReqDto;
import org.example.food.domain.user.dto.UserResDto;
import org.example.food.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserResDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        UserResDto dto = new UserResDto();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    private User toEntity(UserReqDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }

    private void updateUserFromDto(UserReqDto dto, User user) {
        if (dto == null || user == null) {
            return;
        }
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
    }

    @Override
    public UserResDto getUserById(Long id) {
        User user = findUserById(id);
        return toUserDto(user);
    }

    @Override
    public Long createUser(UserReqDto userReqDto) {
        User user = toEntity(userReqDto);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public UserResDto updateUser(Long id, UserReqDto userReqDto) {
        User user = findUserById(id);
        updateUserFromDto(userReqDto, user);
        userRepository.save(user);
        return toUserDto(user);
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
    public UserResDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return toUserDto(user);
    }
}
