package org.example.food.restaurant.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.board.entity.Location;
import org.example.food.restaurant.dto.RestaurantDetailsDto;
import org.example.food.restaurant.dto.RestaurantResDto;
import org.example.food.restaurant.entity.Direction;
import org.example.food.restaurant.entity.GeometryUtil;
import org.example.food.restaurant.entity.Restaurant;
import org.example.food.restaurant.exception.RestaurantException;
import org.example.food.restaurant.exception.RestaurantExceptionType;
import org.example.food.restaurant.repository.RestaurantRepository;
import org.example.food.user.entity.User;
import org.example.food.board.dto.BoardResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RestaurantServiceImpl {

    private final EntityManager em;

    @Transactional(readOnly = true)
    public List<Restaurant> getNearByRestaurants(Double latitude, Double longitude, Double distance) {
        Location northEast = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        Query query = em.createNativeQuery("SELECT r.id, r.address, r.address_city, "
                        + "r.address_district, r.address_district_old, r.address_old, r.address_province, "
                        + "r.category, r.category_code, r.category_industry, r.category_main, r.category_sub, "
                        + "r.point, r.name, r.zip_code "
                        + "FROM restaurant AS r "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", r.point)", Restaurant.class)
                .setMaxResults(10);

        List<Restaurant> restaurants = query.getResultList();
        return restaurants;
    }
}