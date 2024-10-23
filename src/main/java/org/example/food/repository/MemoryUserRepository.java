package org.example.food.repository;

import org.example.food.domain.user.User;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserRepository{
    private static Map<Long, User> store = new HashMap<>();
    public User save(User user) {
        store.put(user.getId(), user);
        return user;
    }

    public User findById(Long userId) {
        return store.get(userId);
    }


    public void deleteUser(Long id) {
        store.remove(id);
    }

    public void clearStore() {
        store.clear();
    }
}
