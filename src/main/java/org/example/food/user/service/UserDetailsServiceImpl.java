package org.example.food.user.service;

import org.example.food.user.entity.User;
import org.example.food.user.dto.CustomUserDetails;
import org.example.food.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username을 통해 사용자 정보 조회
        User user = userRepository.findByUsername(username);

        if (user == null) {
            // 사용자 정보가 존재하지 않는 경우 예외 발생
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // CustomUserDetails 객체로 변환하여 반환
        return new CustomUserDetails(user);
    }

    // 이메일로 사용자 찾기 (옵션)
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new CustomUserDetails(user.get());
    }
}
