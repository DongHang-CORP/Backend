package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.comment.Comment;
import org.example.food.domain.comment.dto.CommentReqDto;
import org.example.food.domain.comment.dto.CommentResDto;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.example.food.repository.CommentRepository;
import org.example.food.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;

    private CommentResDto toCommentDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentResDto dto = new CommentResDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setWriter(comment.getUser().getNickname()); // 사용자 ID 추가
        return dto;
    }

    private Comment toEntity(CommentReqDto dto) {
        if (dto == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        // 필요한 필드들을 추가적으로 매핑
        return comment;
    }

    @Override
    public Long writeComment(Long boardId, CommentReqDto commentDto, User user) {
        Comment comment = toEntity(commentDto);
        Video video = videoRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시판을 찾을 수 없습니다.");
        });
        comment.setUser(user);
        comment.setVideo(video);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Override
    public List<CommentResDto> getComments(Long boardId) {
        List<Comment> comments = commentRepository.findAllByVideoId(boardId);
        return comments.stream()
                .map(this::toCommentDto) // 매핑 로직 호출
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
