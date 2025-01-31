package org.example.food.board.service;

import java.util.List;
import org.example.food.user.entity.User;
import org.example.food.board.dto.BoardRequest;
import org.example.food.board.dto.BoardResponse;
import org.example.food.board.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BoardService {
    List<BoardResponse> getAllBoards(Pageable pageable);
    Long createboard(BoardRequest boardRequest, User user);
    void deleteboard(Long id, User user);
    Board findboardById(Long id);
}
