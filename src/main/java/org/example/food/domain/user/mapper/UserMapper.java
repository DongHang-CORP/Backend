package org.example.food.domain.user.mapper;

import org.example.food.common.config.CentralMapperConfig;
import org.example.food.domain.restaurant.Restaurant;
import org.example.food.domain.restaurant.dto.RestaurantReqDto;
import org.example.food.domain.restaurant.dto.RestaurantResDto;
import org.example.food.domain.user.User;
import org.example.food.domain.user.dto.UserReqDto;
import org.example.food.domain.user.dto.UserResDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = CentralMapperConfig.class, componentModel = "spring")
public interface UserMapper {
    User toEntity(UserReqDto userReqDto);

    UserResDto toUserDto(User user);

    void updateUserFromDto(UserReqDto userReqDto, @MappingTarget User user);
}
