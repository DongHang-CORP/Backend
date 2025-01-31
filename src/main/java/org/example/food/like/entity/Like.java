package org.example.food.like.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.food.user.entity.User;
import org.example.food.board.entity.Board;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private Like(User user, Board board) {
        this.user = user;
        this.board = board;
    }

    public boolean isLikeOf(Long userId) {
        return user.hasId(userId);
    }

    public void delete() {
        this.board = null;
    }
}
