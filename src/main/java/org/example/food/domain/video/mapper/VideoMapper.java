package org.example.food.domain.video.mapper;

import org.example.food.common.config.CentralMapperConfig;
import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = CentralMapperConfig.class, componentModel = "spring")
public interface VideoMapper {
    Video toEntity(VideoReqDto videoReqDto);

    VideoResDto toVideoDto(Video video);

    void updateVideoFromDto(VideoReqDto videoReqDto,@MappingTarget Video video);
}
