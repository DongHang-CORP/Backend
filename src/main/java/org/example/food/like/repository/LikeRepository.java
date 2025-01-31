package org.example.food.like.repository;

import org.example.food.like.entity.Like;
import org.example.food.user.entity.User;
import org.example.food.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    void deleteAllByBoard(Board board);

    boolean existsByBoardAndUserId(Board board, Long UserId);

    Optional<Like> findByBoardAndUserId(Board board, Long UserId);

    Optional<Like> findByBoardAndUser(Board board, User User);

    Long countByBoardId(@Param("reviewId") Long reviewId);

    boolean existsByUserIdAndBoardId(Long userId, Long reviewId);
}
