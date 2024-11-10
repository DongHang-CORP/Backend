package org.example.food.comment.repository;

import org.example.food.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByVideoId(Long boardId);

}
