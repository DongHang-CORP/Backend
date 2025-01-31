package org.example.food.like.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.food.board.entity.Board;
import org.example.food.board.repository.BoardRepository;
import org.example.food.like.dto.LikeFlipResponse;
import org.example.food.like.entity.Like;
import org.example.food.like.repository.LikeRepository;
import org.example.food.user.entity.User;
import org.example.food.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public LikeFlipResponse flipboardLike(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(RuntimeException::new);

        int likeCount = flipboardLike(user.getId(), board);

        boolean liked = likeRepository.existsByBoardAndUserId(board, user.getId());

        return new LikeFlipResponse(likeCount, liked);
    }

    private int flipboardLike(Long UserId, Board board) {
        final Optional<Like> like = likeRepository.findByBoardAndUserId(board, UserId);

        if (like.isPresent()) {
            board.deleteLike(like.get());
            boardRepository.decreaseLikeCount(board.getId());
            return board.getLikeCount() - 1;
        }

        // 좋아요를 추가하는 경우
        addNewboardLike(UserId, board);
        boardRepository.increaseLikeCount(board.getId());
        return board.getLikeCount() + 1;
    }

    private void addNewboardLike(Long userId, Board board) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        Like boardLike = Like.builder()
                .user(user)
                .board(board)
                .build();
        likeRepository.save(boardLike);
    }

}
