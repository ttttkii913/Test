package org.likelion.likelionassignmentcrud.lecture.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LectureSaveReqDto(
        @NotNull(message = "교수 Id를 필수로 입력해야 합니다.")
        Long professorId,

        @NotBlank(message = "강의명을 필수로 입력해야 합니다.")
        String title,

        @Positive
        @Max(value = 4, message = "1부터 4까지의 값만 입력할 수 있습니다.")
        Long grade
) {
}