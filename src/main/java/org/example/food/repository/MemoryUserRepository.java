package org.example.food.repository;

import org.example.food.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryUserRepository implements UserRepository{
    private static Map<Long, User> store = new HashMap<>();
    @Override
    public User save(User user) {
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public User findById(Long userId) {
        return store.get(userId);
    }


    @Override
    public void deleteUser(Long id) {
        store.remove(id);
    }
}
