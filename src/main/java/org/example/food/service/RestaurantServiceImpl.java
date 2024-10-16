package org.example.food.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.restaurant.dto.RestaurantDetailsDto;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.domain.video.Category;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.example.food.exception.RestaurantException;
import org.example.food.exception.RestaurantExceptionType;
import org.example.food.repository.RestaurantQueryRepository;
import org.example.food.repository.RestaurantRepository;
import org.example.food.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;
    private final VideoRepository videoRepository;


    // Entity -> DTO 변환
    private RestaurantResDto toRestaurantDto(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }
        long videoCount = videoRepository.countByRestaurantId(restaurant.getId());
        RestaurantResDto dto = new RestaurantResDto();
        dto.setRestaurantId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setLatitude(restaurant.getLat());
        dto.setLongitude(restaurant.getLng());
        dto.setCategory(restaurant.getCategory());
        dto.setVideoCount(videoCount);
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
    public RestaurantDetailsDto getRestaurantById(Long id) {
        Restaurant restaurant = findRestaurantById(id);
        // 연관된 비디오들을 VideoResDto로 변환
        List<VideoResDto> videoDtos = restaurant.getVideos().stream()
                .map(video -> VideoResDto.builder()
                        .videoId(video.getId())
                        .userNickname(video.getUser().getNickname())  // User의 닉네임을 가져옴
                        .restaurantId(restaurant.getId())  // Restaurant ID를 가져옴
                        .restaurant(restaurant.getName())  // Restaurant 이름을 가져옴
                        .url(video.getUrl())  // Video의 URL을 가져옴
                        .content(video.getContent())  // Video의 컨텐츠를 가져옴
                        .category(video.getCategory())  // Video의 카테고리를 가져옴
                        .build())
                .collect(Collectors.toList());

        return new RestaurantDetailsDto(restaurant, videoDtos);
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

    @Override
    public List<RestaurantResDto> getNearbyRestaurants(double userLat, double userLon, double radius, List<Category> categories) {
        List<Restaurant> restaurants = restaurantQueryRepository.findRestaurantsByLocation(userLat, userLon, radius, categories);
        return restaurants.stream()
                .map(this::toRestaurantDto)
                .collect(Collectors.toList());
    }

}