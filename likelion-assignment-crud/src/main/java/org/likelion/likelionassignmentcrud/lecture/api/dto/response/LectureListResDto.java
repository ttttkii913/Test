package org.likelion.likelionassignmentcrud.lecture.api.dto.response;

import lombok.Builder;
import java.util.List;

@Builder
public record LectureListResDto(
        List<LectureInfoResDto> lectures
) {
    public static LectureListResDto from(List<LectureInfoResDto> lectures) {
        return LectureListResDto.builder()
                .lectures(lectures)
                .build();
    }
}