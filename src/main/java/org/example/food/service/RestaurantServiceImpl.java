package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.domain.restaurant.mapper.RestaurantMapper;
import org.example.food.repository.RestaurantRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantMapper restaurantMapper = Mappers.getMapper(RestaurantMapper.class);
    private final RestaurantRepository restaurantRepository;

    private static final double SEARCH_RADIUS = 5.0; // 5km 반경


    @Override
    public List<RestaurantResDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(restaurantMapper::toRestaurantDto)  // 각각의 객체를 매핑
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantResDto getRestaurantById(Long id) {
        Restaurant restaurant = findRestaurantById(id);
        return restaurantMapper.toRestaurantDto(restaurant);
    }

    @Override
    public Long createRestaurant(RestaurantReqDto restaurantReqDto) {
        Restaurant restaurant = restaurantMapper.toEntity(restaurantReqDto);
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    @Override
    @Transactional
    public RestaurantResDto updateRestaurant(Long id, RestaurantReqDto restaurantReqDto) {
        Restaurant restaurant = findRestaurantById(id);
        restaurantMapper.updateRestaurantFromDto(restaurantReqDto, restaurant);
        restaurantRepository.save(restaurant);
        return restaurantMapper.toRestaurantDto(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("음식점이 존재하지 않습니다"));
    }

    @Override
    public List<RestaurantResDto> getNearbyRestaurants(double userLat, double userLon) {
        // 기본적으로 위도/경도 범위를 이용해 먼저 필터링
        double latRange = SEARCH_RADIUS / 110.574; // 위도의 1도당 거리
        double lonRange = SEARCH_RADIUS / (111.320 * Math.cos(Math.toRadians(userLat))); // 경도의 1도당 거리

        // 위도/경도 범위 내의 레스토랑 검색
        List<Restaurant> nearbyRestaurants = restaurantRepository.findByLatitudeBetweenAndLongitudeBetween(
                userLat - latRange, userLat + latRange,
                userLon - lonRange, userLon + lonRange
        );

        // 하버사인 공식으로 정확한 거리 계산 후 필터링
        return nearbyRestaurants.stream()
                .filter(restaurant -> {
                    double distance = LocationService.calculateDistance(
                            userLat, userLon, restaurant.getLatitude(), restaurant.getLongitude()
                    );
                    return distance <= SEARCH_RADIUS;  // 반경 내의 레스토랑만 반환
                })
                .map(restaurantMapper::toRestaurantDto)
                .collect(Collectors.toList());
    }

}
