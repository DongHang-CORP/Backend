package org.example.food.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.food.domain.restaurant.QRestaurant;
import org.example.food.domain.restaurant.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RestaurantQueryRepository {

    private final JPAQueryFactory queryFactory;

    private static final double EARTH_RADIUS_KM = 6371.0; // 지구 반지름 (킬로미터 단위)

    // 하버사인 공식으로 반경 내의 음식점 검색
    public List<Restaurant> findRestaurantsByLocation(double userLat, double userLon, double radius) {
        QRestaurant restaurant = QRestaurant.restaurant; // QueryDSL 엔티티 사용
        return queryFactory
                .selectFrom(restaurant)
                .where(distanceWithinRadius(userLat, userLon, restaurant.lat, restaurant.lng, radius))
                .fetch();
    }

    // 하버사인 공식으로 거리 계산하여 반경 내인지 확인하는 메서드
    private BooleanExpression distanceWithinRadius(double userLat, double userLon,
                                                   NumberPath<Double> restaurantLat,
                                                   NumberPath<Double> restaurantLon,
                                                   double radius) {
        // 하버사인 거리 계산을 위한 식을 템플릿으로 생성
        NumberTemplate<Double> distance = calculateDistance(userLat, userLon, restaurantLat, restaurantLon);

        // 계산된 거리가 반경 내인지 확인
        return distance.loe(radius);
    }

    // 하버사인 공식으로 거리 계산 (SQL 템플릿으로 표현)
    private NumberTemplate<Double> calculateDistance(double userLat, double userLon,
                                                     NumberPath<Double> restaurantLat,
                                                     NumberPath<Double> restaurantLon) {
        // Expressions.numberTemplate()을 사용하여 템플릿 생성
        return Expressions.numberTemplate(Double.class,
                "({0} * acos(cos(radians({1})) * cos(radians({2})) * cos(radians({3}) - radians({4})) + sin(radians({1})) * sin(radians({2}))))",
                EARTH_RADIUS_KM, // 지구 반지름
                userLat,         // 사용자 위도
                restaurantLat,   // 레스토랑 위도
                userLon,         // 사용자 경도
                restaurantLon    // 레스토랑 경도
        );
    }
}
