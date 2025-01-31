package org.example.food.comment.service;

import org.example.food.comment.dto.CommentRequest;
import org.example.food.comment.dto.CommentResponse;
import org.example.food.user.entity.User;

import java.util.List;

public interface CommentService {
    Long writeComment(Long boardId, CommentRequest commentRequest, User user);

    void deleteComment(Long commentId);

    List<CommentResponse> getComments(Long boardId);
}
