package org.example.food.domain.user.mapper;

import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.domain.user.User;
import org.example.food.domain.user.dto.UserReqDto;
import org.example.food.domain.user.dto.UserResDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User toEntity(UserReqDto userReqDto);

    UserResDto toUserDto(Restaurant restaurant);

    void updateUserFromDto(UserReqDto userReqDto, User user);
}
