package org.example.food.comment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResDto {
    private Long id;
    private String content;
    private String writer;
}
