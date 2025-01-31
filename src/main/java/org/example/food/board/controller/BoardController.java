package org.example.food.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.board.dto.BoardDetailResponse;
import org.example.food.common.jwt.LoginUser;
import org.example.food.board.entity.Category;
import org.example.food.user.entity.User;
import org.example.food.board.dto.BoardRequest;
import org.example.food.board.dto.BoardResponse;
import org.example.food.board.service.BoardServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardServiceImpl boardService;

    @GetMapping
    public ResponseEntity<?> getAllBoards(Pageable pageable) {

        List<BoardResponse> boardResponses = boardService.getAllBoards(pageable);
        return ResponseEntity.ok(boardResponses);
    }

    @PostMapping
    public ResponseEntity<Long> createBoard(@RequestBody BoardRequest boardRequest, @LoginUser User user) {

        Long boardId = boardService.createboard(boardRequest, user);
        return ResponseEntity.ok(boardId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailResponse> getBoard(@PathVariable Long id, @LoginUser User user) {

        BoardDetailResponse boardResponse = boardService.getBoard(id, user);
        return ResponseEntity.ok(boardResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id, @LoginUser User user) {

        boardService.deleteboard(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category")
    public ResponseEntity<List<BoardResponse>> getboardsByCategories(@RequestParam List<Category> categories) {
        List<BoardResponse> boards = boardService.getboardsByCategories(categories);
        return ResponseEntity.ok(boards);
    }
}
