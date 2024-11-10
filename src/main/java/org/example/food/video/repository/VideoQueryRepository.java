package org.example.food.video.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.food.global.common.dto.Location;
import org.example.food.video.entity.QVideo;
import org.example.food.video.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VideoQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final double EARTH_RADIUS_KM = 6371.0;

    public Page<Video> findVideosByLocationWithPagination(Location location, Pageable pageable) {
        QVideo video = QVideo.video;

        List<Video> videos = queryFactory
                .selectFrom(video)
                .where(distanceWithinRadius(location, video.restaurant.lat, video.restaurant.lng))
                .offset(pageable.getOffset())  // offset 설정
                .limit(pageable.getPageSize())  // 페이지 사이즈만 설정
                .fetch();

        long total = queryFactory
                .selectFrom(video)
                .where(distanceWithinRadius(location, video.restaurant.lat, video.restaurant.lng))
                .fetchCount(); // 전체 데이터 수 조회

        return new PageImpl<>(videos, pageable, total);
    }

    private BooleanExpression distanceWithinRadius(Location location,
                                                   NumberPath<Double> restaurantLat,
                                                   NumberPath<Double> restaurantLon
    ) {
        NumberTemplate<Double> distance = calculateDistance(location, restaurantLat, restaurantLon);
        return distance.loe(location.getRadius());
    }

    private NumberTemplate<Double> calculateDistance(Location location,
                                                     NumberPath<Double> restaurantLat,
                                                     NumberPath<Double> restaurantLon) {
        return Expressions.numberTemplate(Double.class,
                "({0} * acos(cos(radians({1})) * cos(radians({2} / 1000000.0)) * cos(radians({3} / 1000000.0) - radians({4})) + sin(radians({1})) * sin(radians({2} / 1000000.0))) )",
                EARTH_RADIUS_KM, location.getUserLat(), restaurantLat, location.getUserLon(), restaurantLon);
    }
}
