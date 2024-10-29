package org.example.food.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.global.common.dto.Location;
import org.example.food.like.repository.LikeRepository;
import org.example.food.restaurant.dto.RestaurantDetailsDto;
import org.example.food.restaurant.dto.RestaurantResDto;
import org.example.food.restaurant.entity.Category;
import org.example.food.restaurant.entity.Restaurant;
import org.example.food.restaurant.exception.RestaurantException;
import org.example.food.restaurant.exception.RestaurantExceptionType;
import org.example.food.restaurant.repository.RestaurantQueryRepository;
import org.example.food.restaurant.repository.RestaurantRepository;
import org.example.food.user.entity.User;
import org.example.food.video.dto.VideoResDto;
import org.example.food.video.entity.Video;
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
    private final LikeRepository likeRepository;

    @Override
    public List<RestaurantResDto> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantResDto::toRestaurantDto)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDetailsDto getRestaurantById(Long id, User user) {
        Restaurant restaurant = findRestaurantById(id);
        List<VideoResDto> videoDtos = restaurant.getVideos().stream()
                .map(video -> toVideoDto(video, user))
                .collect(Collectors.toList());

        return new RestaurantDetailsDto(restaurant, videoDtos);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
        log.info("Deleted restaurant with ID: {}", id);
    }


    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(RestaurantExceptionType.NOT_FOUND_RESTAURANT));
    }

    @Override
    public List<RestaurantResDto> getNearbyRestaurants(Location location, List<Category> categories) {
        return restaurantQueryRepository.findRestaurantsByLocation(location, categories).stream()
                .map(RestaurantResDto::toRestaurantDto)
                .collect(Collectors.toList());
    }

    private VideoResDto toVideoDto(Video video, User user) {
        boolean isLike = user != null && likeRepository.findByUserAndVideo(user, video).isPresent();
        return VideoResDto.fromVideo(video, isLike);
    }
}