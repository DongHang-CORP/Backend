package org.example.food.repository;

import org.example.food.entity.User;

public interface UserRepository {
    User save(User user);
    User findById(Long userId);
    void deleteUser(Long id);
}
