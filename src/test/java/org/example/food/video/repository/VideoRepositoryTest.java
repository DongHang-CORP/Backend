package org.example.food.video.repository;

import org.assertj.core.api.Assertions;
import org.example.food.restaurant.entity.Category;
import org.example.food.video.entity.Video;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class VideoRepositoryTest {

    @Autowired
    private VideoRepository videoRepository;

    @DisplayName("원하는 카테고리의 비디오를 조회한다.")
    @Test
    void findAllByCategory() {
        //given
        Video video1 = Video.builder()
                .category(Category.chicken)
                .content("맛있어요")
                .build();
        Video video2 = Video.builder()
                .category(Category.chicken)
                .content("맛있어요")
                .build();
        Video video3 = Video.builder()
                .category(Category.chicken)
                .content("맛있어요")
                .build();
        videoRepository.saveAll(List.of(video1, video2, video3));
        //when

        List<Video> videos = videoRepository.findAllByCategoryIn(List.of(Category.chicken));

        //then
        Assertions.assertThat(videos).hasSize(3);
    }


}