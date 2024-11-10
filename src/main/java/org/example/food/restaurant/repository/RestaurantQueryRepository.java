package org.example.food.restaurant.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.food.global.common.dto.Location;
import org.example.food.restaurant.entity.Category;
import org.example.food.restaurant.entity.QRestaurant;
import org.example.food.restaurant.entity.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RestaurantQueryRepository {

    private final JPAQueryFactory queryFactory;

    private static final double EARTH_RADIUS_KM = 6371.0;

    // 하버사인 공식으로 반경 내의 음식점 검색
    public List<Restaurant> findRestaurantsByLocation(Location location, List<Category> categories) {
        QRestaurant restaurant = QRestaurant.restaurant;
        return queryFactory
                .selectFrom(restaurant)
                .where(distanceWithinRadius(location, restaurant.lat, restaurant.lng)
                        .and(categoryIn(categories))) // 카테고리 필터 추가
                .fetch();
    }

    private BooleanExpression distanceWithinRadius(Location location,
                                                   NumberPath<Double> restaurantLat,
                                                   NumberPath<Double> restaurantLon) {

        NumberTemplate<Double> distance = calculateDistance(location, restaurantLat, restaurantLon);
        return distance.loe(location.getRadius());
    }

    private BooleanExpression categoryIn(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return null; // 카테고리가 없으면 모든 카테고리 반환
        }
        return QRestaurant.restaurant.category.in(categories); // 선택된 카테고리에 해당하는 음식점
    }

    private NumberTemplate<Double> calculateDistance(Location location,
                                                     NumberPath<Double> restaurantLat,
                                                     NumberPath<Double> restaurantLon) {
        return Expressions.numberTemplate(Double.class,
                "({0} * acos(cos(radians({1})) * cos(radians({2})) * cos(radians({3}) - radians({4})) + sin(radians({1})) * sin(radians({2}))) )",
                EARTH_RADIUS_KM, location.getUserLat(), restaurantLat, location.getUserLon(), restaurantLon);
    }
}
