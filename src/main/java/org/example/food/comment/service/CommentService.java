package org.example.food.comment.service;

import org.example.food.comment.dto.CommentReqDto;
import org.example.food.comment.dto.CommentResDto;
import org.example.food.user.entity.User;

import java.util.List;

public interface CommentService {
    Long writeComment(Long boardId, CommentReqDto commentReqDto, User user);

    void deleteComment(Long commentId);

    List<CommentResDto> getComments(Long boardId);
}
