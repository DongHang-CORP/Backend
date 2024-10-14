package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.exception.RestaurantException;
import org.example.food.exception.RestaurantExceptionType;
import org.example.food.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    // Entity -> DTO 변환
    private RestaurantResDto toRestaurantDto(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }
        RestaurantResDto dto = new RestaurantResDto();
        dto.setRestaurantId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setLatitude(restaurant.getLat());
        dto.setLongitude(restaurant.getLng());
        dto.setCategory(restaurant.getCategory());
        return dto;
    }

    // DTO -> Entity 변환
    private Restaurant toEntity(RestaurantReqDto dto) {
        if (dto == null) {
            return null;
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setLat(dto.getLatitude());
        restaurant.setLat(dto.getLongitude());
        restaurant.setCategory(dto.getCategory());
        return restaurant;
    }

    // DTO -> Entity 업데이트
    private void updateRestaurantFromDto(RestaurantReqDto dto, Restaurant restaurant) {
        if (dto == null || restaurant == null) {
            return;
        }
        restaurant.setName(dto.getName());
        restaurant.setLat(dto.getLatitude());
        restaurant.setLng(dto.getLongitude());
        restaurant.setCategory(dto.getCategory());
    }

    @Override
    public List<RestaurantResDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(this::toRestaurantDto)  // 각각의 객체를 매핑
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantResDto getRestaurantById(Long id) {
        Restaurant restaurant = findRestaurantById(id);
        return toRestaurantDto(restaurant);
    }

    @Override
    public Long createRestaurant(RestaurantReqDto restaurantReqDto) {
        Restaurant restaurant = toEntity(restaurantReqDto);
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    @Override
    @Transactional
    public RestaurantResDto updateRestaurant(Long id, RestaurantReqDto restaurantReqDto) {
        Restaurant restaurant = findRestaurantById(id);
        updateRestaurantFromDto(restaurantReqDto, restaurant);
        restaurantRepository.save(restaurant);
        return toRestaurantDto(restaurant);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Transactional
    public Restaurant findOrCreateRestaurant(VideoReqDto videoReqDto) {
        Restaurant restaurant = restaurantRepository.findByName(videoReqDto.getRestaurant());
        if (restaurant == null) {
            restaurant = new Restaurant();
            restaurant.setName(videoReqDto.getRestaurant());
            restaurant.setLat(videoReqDto.getLat());
            restaurant.setLng(videoReqDto.getLng());
            restaurant.setCategory(videoReqDto.getCategory());
            restaurantRepository.save(restaurant);
        }
        return restaurant;
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantException(RestaurantExceptionType.NOT_FOUND_RESTAURANT));
    }

}