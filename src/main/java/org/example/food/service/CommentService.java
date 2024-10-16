package org.example.food.service;

import org.example.food.domain.comment.dto.CommentReqDto;
import org.example.food.domain.comment.dto.CommentResDto;
import org.example.food.domain.user.User;

import java.util.List;

public interface CommentService {
    Long writeComment(Long boardId, CommentReqDto commentReqDto, User user);
    void deleteComment(Long commentId);
    List<CommentResDto> getComments(Long boardId);
}
