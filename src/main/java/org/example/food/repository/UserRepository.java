package org.example.food.repository;

import org.example.food.entity.User;

public interface UserRepository {
    void save(User user);
    User findById(Long userId);
}
