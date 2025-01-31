package org.example.food.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.board.dto.BoardDetailResponse;
import org.example.food.board.entity.Category;
import org.example.food.user.entity.User;
import org.example.food.board.dto.BoardRequest;
import org.example.food.board.dto.BoardResponse;
import org.example.food.board.entity.Board;
import org.example.food.board.exception.BoardException;
import org.example.food.board.exception.BoardExceptionType;
import org.example.food.board.repository.BoardRepository;
import org.example.food.like.repository.LikeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    @Override
    public List<BoardResponse> getAllBoards(Pageable pageable) {
        Slice<Board> boards = boardRepository.findSliceBy(pageable);
        return boards.stream()
                .map(BoardResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardDetailResponse getBoard(Long id, User user) {
        if (!boardRepository.existsById(id)) {
            throw new BoardException(BoardExceptionType.NOT_FOUND_board);
        }
        Board board = boardRepository.findBoardWithComments(id);
        boolean likeState = likeRepository.existsByUserIdAndBoardId(user.getId(), board.getId());
        return BoardDetailResponse.of(board, likeState);
    }

    @Override
    @Transactional
    public Long createboard(BoardRequest boardRequest, User user) {
        Board board = Board.toEntity(boardRequest, user);
        boardRepository.save(board);
        return board.getId();
    }

    @Override
    @Transactional
    public void deleteboard(Long id, User user) {
        if (!boardRepository.existsById(id)) {
            throw new BoardException(BoardExceptionType.NOT_FOUND_board);
        }
        boardRepository.deleteById(id);
    }

    @Override
    public Board findboardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardException(BoardExceptionType.NOT_FOUND_board));
    }

    public List<BoardResponse> getboardsByCategories(List<Category> categories) {
        List<Board> boards = boardRepository.findAllByCategoryIn(categories);
        return boards.stream()
                .map(BoardResponse::of)
                .collect(Collectors.toList());
    }
}