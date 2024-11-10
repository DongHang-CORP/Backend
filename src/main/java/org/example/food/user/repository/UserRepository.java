package org.example.food.user.repository;

import org.example.food.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

}
