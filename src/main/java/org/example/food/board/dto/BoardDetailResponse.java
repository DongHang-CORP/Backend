package org.example.food.board.dto;

import lombok.Builder;
import lombok.Data;
import org.example.food.board.entity.Board;
import org.example.food.board.entity.Category;
import org.example.food.comment.dto.CommentResponse;
import org.example.food.comment.entity.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class BoardDetailResponse {
    private Long boardId;
    private String userName;
    private String restaurantName;
    private String contentUrl;
    private String content;
    private Category category;
    private int likeCount;
    private boolean likeState;
    private List<CommentResponse> comments;


    @Builder
    private BoardDetailResponse(Long boardId, String userName, String restaurantName, String contentUrl, String content, Category category, int likeCount, boolean likeState,List<CommentResponse> comments) {
        this.boardId = boardId;
        this.userName = userName;
        this.restaurantName = restaurantName;
        this.contentUrl = contentUrl;
        this.content = content;
        this.category = category;
        this.likeCount = likeCount;
        this.likeState = likeState;
        this.comments = comments;
    }

    public static BoardDetailResponse of(Board board, boolean likeState) {
        return BoardDetailResponse.builder()
                .boardId(board.getId())
                .userName(board.getUser().getUsername())
                .restaurantName(board.getRestaurantName())
                .contentUrl(board.getContentUrl())
                .content(board.getContent())
                .category(board.getCategory())
                .comments(board.getComments().stream()
                        .map(CommentResponse::of)
                        .collect(Collectors.toList()))
                .likeCount(board.getLikeCount())
                .build();
    }
}
