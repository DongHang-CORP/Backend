package org.example.food.service;

import org.example.food.entity.User;

public interface UserService {
    User getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
