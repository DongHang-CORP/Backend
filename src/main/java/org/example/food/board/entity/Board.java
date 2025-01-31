package org.example.food.board.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.food.comment.entity.Comment;
import org.example.food.like.entity.Like;
import org.example.food.restaurant.entity.Restaurant;
import org.example.food.user.entity.User;
import org.example.food.board.dto.BoardRequest;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String restaurantName;

    private String contentUrl;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private Location location;

    @Embedded
    private Address address;

    private int likeCount = 0;

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boards")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Builder
    private Board(User user, String restaurantName, String contentUrl, String content, Category category, Location location, Address address) {
        this.user = user;
        this.restaurantName = restaurantName;
        this.contentUrl = contentUrl;
        this.content = content;
        this.category = category;
        this.location = location;
        this.address = address;
    }
    public static Board toEntity(BoardRequest dto, User user) {
        Board board = new Board();
        board.user = user;
        board.contentUrl = dto.getContentUrl();
        board.content = dto.getContent();
        board.category = dto.getCategory();
        board.location = dto.getLocation();
        board.address = dto.getAddress();
        return board;
    }


    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setBoard(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setBoard(null);
    }

    public void deleteLike(Like like) {
        likes.remove(like);
        like.delete();
    }

    public int getLikeCount() {
        return likeCount;
    }
}
