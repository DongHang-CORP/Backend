package org.example.food.domain.video.mapper;

import org.example.food.domain.video.Video;
import org.example.food.domain.video.dto.VideoReqDto;
import org.example.food.domain.video.dto.VideoResDto;
import org.mapstruct.Mapper;

@Mapper
public interface VideoMapper {
    Video toEntity(VideoReqDto videoReqDto);

    VideoResDto toVideoDto(Video video);

    void updateVideoFromDto(VideoReqDto videoReqDto, Video video);
}
