package org.likelion.likelionassignmentcrud.professor.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.likelion.likelionassignmentcrud.professor.application.validator.EnumValid;
import org.likelion.likelionassignmentcrud.professor.domain.Department;

public record ProfessorUpdateReqDto( //이름, 학과 update
        @NotBlank(message = "이름을 필수로 입력해야 합니다.")
        @Size(min = 2, max = 10, message = "2자 이상 10자 이하로 입력해주세요.")    // String: Size 어노테이션
        String name,

        @EnumValid(enumClass = Department.class)
        Department department,

        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$",
                message = "이메일 형식에 맞지 않습니다.")
        String email
) {
}
