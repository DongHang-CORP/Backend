package org.example.food.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.restaurant.dto.RestaurantDetailsDto;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.domain.user.User;
import org.example.food.domain.video.Category;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.example.food.exception.RestaurantException;
import org.example.food.exception.RestaurantExceptionType;
import org.example.food.repository.LikeRepository;
import org.example.food.repository.RestaurantQueryRepository;
import org.example.food.repository.RestaurantRepository;
import org.example.food.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.food.domain.restaurant.Restaurant.toEntity;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;
    private final VideoRepository videoRepository;
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

    private VideoResDto toVideoDto(Video video, User user) {
        boolean isLike = user != null && likeRepository.findByUserAndVideo(user, video).isPresent();

        return VideoResDto.builder()
                .videoId(video.getId())
                .url(video.getUrl())
                .content(video.getContent())
                .category(video.getCategory())
                .restaurant(video.getRestaurant().getName())
                .restaurantId(video.getRestaurant().getId())
                .userNickname(video.getUser().getNickname())
                .likeCount(video.getLikeCount())
                .isLike(isLike)
                .build();
    }

    @Override
    @Transactional
    public Long createRestaurant(RestaurantReqDto restaurantReqDto) {
        Restaurant restaurant = toEntity(restaurantReqDto);
        restaurantRepository.save(restaurant);
        log.info("Created restaurant: {}", restaurant.getName());
        return restaurant.getId();
    }


    @Override
    @Transactional
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
        log.info("Deleted restaurant with ID: {}", id);
    }

    @Transactional
    public Restaurant findOrCreateRestaurant(VideoReqDto videoReqDto) {
        return restaurantRepository.findByName(videoReqDto.getRestaurant())
                .orElseGet(() -> {
                    Restaurant restaurant = Restaurant.of(
                            videoReqDto.getRestaurant(),
                            videoReqDto.getLat(),
                            videoReqDto.getLng(),
                            videoReqDto.getCategory()
                    );
                    restaurantRepository.save(restaurant);
                    log.info("Created restaurant: {}", restaurant.getName());
                    return restaurant;
                });
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(RestaurantExceptionType.NOT_FOUND_RESTAURANT));
    }

    @Override
    public List<RestaurantResDto> getNearbyRestaurants(double userLat, double userLon, double radius, List<Category> categories) {
        return restaurantQueryRepository.findRestaurantsByLocation(userLat, userLon, radius, categories).stream()
                .map(RestaurantResDto::toRestaurantDto)
                .collect(Collectors.toList());
    }

}