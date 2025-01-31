package org.example.food.comment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.food.comment.entity.Comment;

@Data
@NoArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String content;
    private String userName;

    @Builder
    private CommentResponse(Long commentId, String userName, String content) {
        this.commentId = commentId;
        this.userName = userName;
        this.content = content;
    }
    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .userName(comment.getUser().getUsername())
                .content(comment.getContent())
                .build();
    }
}
