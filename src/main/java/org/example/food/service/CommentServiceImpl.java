package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.comment.Comment;
import org.example.food.domain.comment.dto.CommentReqDto;
import org.example.food.domain.comment.dto.CommentResDto;
import org.example.food.domain.comment.mapper.CommentMapper;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.repository.CommentRepository;
import org.example.food.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl {
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final CommentMapper commentMapper;

    public Long writeComment(Long boardId, CommentReqDto commentDto, User user) {
        Comment comment = commentMapper.toEntity(commentDto);
        Video video = videoRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시판을 찾을 수 없습니다.");
        });
        comment.setUser(user);
        comment.setVideo(video);
        commentRepository.save(comment);
        return comment.getId();
    }

    public List<CommentResDto> getComments(Long boardId) {
        List<Comment> comments = commentRepository.findAllByVideoId(boardId);
        return comments.stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
