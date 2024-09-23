package org.example.food.domain.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Video;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Video video;
}
