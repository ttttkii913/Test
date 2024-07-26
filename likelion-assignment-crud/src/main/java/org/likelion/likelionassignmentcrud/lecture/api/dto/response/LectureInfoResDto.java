package org.likelion.likelionassignmentcrud.lecture.api.dto.response;

import lombok.Builder;
import org.likelion.likelionassignmentcrud.lecture.domain.Lecture;

@Builder
public record LectureInfoResDto(
        String title,
        Long grade,
        String writer
) {
    public static LectureInfoResDto from(Lecture lecture) {
        return LectureInfoResDto.builder()
                .title(lecture.getTitle())
                .grade(lecture.getGrade())
                .writer(lecture.getProfessor().getName())
                .build();
    }
}