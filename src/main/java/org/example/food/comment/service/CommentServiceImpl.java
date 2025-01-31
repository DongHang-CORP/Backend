package org.example.food.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.food.comment.entity.Comment;
import org.example.food.comment.dto.CommentRequest;
import org.example.food.comment.dto.CommentResponse;
import org.example.food.user.entity.User;
import org.example.food.board.entity.Board;
import org.example.food.comment.repository.CommentRepository;
import org.example.food.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    private Comment toEntity(CommentRequest dto) {
        if (dto == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        // 필요한 필드들을 추가적으로 매핑
        return comment;
    }

    @Override
    public Long writeComment(Long boardId, CommentRequest commentDto, User user) {
        Comment comment = toEntity(commentDto);
        Board board = boardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시판을 찾을 수 없습니다.");
        });
        comment.setUser(user);
        comment.setBoard(board);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Override
    public List<CommentResponse> getComments(Long boardId) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        return comments.stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
