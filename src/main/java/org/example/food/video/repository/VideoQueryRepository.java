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
    private static final double EARTH_RADIUS_KM = 6371.0; // 지구 반지름 (킬로미터 단위)

    // 하버사인 공식으로 반경 내의 비디오 검색
    public Page<Video> findVideosByLocationWithPagination(Location request, Pageable pageable) {
        QVideo video = QVideo.video;

        // 쿼리 실행하여 결과 리스트 가져오기
        List<Video> videos = queryFactory
                .selectFrom(video)
                .where(distanceWithinRadius(request.getUserLat(), request.getUserLon(), video.restaurant.lat, video.restaurant.lng, request.getRadius()))
                .offset(pageable.getOffset())  // offset 설정
                .limit(pageable.getPageSize())  // 페이지 사이즈만 설정
                .fetch();

        long total = queryFactory
                .selectFrom(video)
                .where(distanceWithinRadius(request.getUserLat(), request.getUserLon(), video.restaurant.lat, video.restaurant.lng, request.getRadius()))
                .fetchCount(); // 전체 데이터 수 조회

        return new PageImpl<>(videos, pageable, total);
    }

    // 하버사인 공식으로 거리 계산하여 반경 내인지 확인하는 메서드
    private BooleanExpression distanceWithinRadius(double userLat, double userLon,
                                                   NumberPath<Double> restaurantLat,
                                                   NumberPath<Double> restaurantLon,
                                                   double radius) {
        // 하버사인 거리 계산을 위한 식
        NumberTemplate<Double> distance = calculateDistance(userLat, userLon, restaurantLat, restaurantLon);
        return distance.loe(radius);
    }

    // 하버사인 공식으로 거리 계산
    private NumberTemplate<Double> calculateDistance(double userLat, double userLon,
                                                     NumberPath<Double> restaurantLat,
                                                     NumberPath<Double> restaurantLon) {
        return Expressions.numberTemplate(Double.class,
                "({0} * acos(cos(radians({1})) * cos(radians({2} / 1000000.0)) * cos(radians({3} / 1000000.0) - radians({4})) + sin(radians({1})) * sin(radians({2} / 1000000.0))) )",
                EARTH_RADIUS_KM, userLat, restaurantLat, userLon, restaurantLon);
    }
}
