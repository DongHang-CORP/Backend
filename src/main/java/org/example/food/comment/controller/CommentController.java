package org.example.food.comment.controller;


import lombok.RequiredArgsConstructor;
import org.example.food.comment.dto.CommentReqDto;
import org.example.food.comment.dto.CommentResDto;
import org.example.food.user.entity.User;
import org.example.food.user.repository.UserRepository;
import org.example.food.comment.service.CommentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/comments")
public class CommentController {

    private final CommentServiceImpl commentService;
    private final UserRepository userRepository;

    @PostMapping("/{boardId}")
    public ResponseEntity<Long> writeComment(@PathVariable("boardId") Long boardId, @RequestBody CommentReqDto commentReqDto) {
        User user = userRepository.findById(1L).get(); //이 로직은 세션으로 처리
        Long userId = commentService.writeComment(boardId, commentReqDto, user);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{boardId}")
    public ResponseEntity<List<CommentResDto>> getComments(@PathVariable("boardId") Long boardId) {
        List<CommentResDto> commentResDtos = commentService.getComments(boardId);
        return new ResponseEntity<>(commentResDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


