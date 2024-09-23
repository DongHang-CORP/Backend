package org.example.food.domain.comment.mapper;

import org.example.food.domain.comment.Comment;
import org.example.food.domain.comment.dto.CommentReqDto;
import org.example.food.domain.comment.dto.CommentResDto;
import org.example.food.domain.user.User;
import org.example.food.domain.user.dto.UserReqDto;
import org.example.food.domain.user.dto.UserResDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toEntity(CommentReqDto commentReqDto);

    CommentResDto toCommentDto(Comment comment);

    void updateCommentFromDto(CommentReqDto commentReqDto, @MappingTarget Comment comment);
}
