package org.example.food.domain.video;

import jakarta.persistence.*;
import lombok.*;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.user.User;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String content;
    private String url;
    private int likeCount;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Version
    private Long version;

    public int addLikeCount(int likeCount){
        this.likeCount += likeCount;
        return this.likeCount;
    }
}
