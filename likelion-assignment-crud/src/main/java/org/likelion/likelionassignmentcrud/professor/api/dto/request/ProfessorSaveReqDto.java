package org.likelion.likelionassignmentcrud.professor.api.dto.request;

import jakarta.validation.constraints.*;
import org.likelion.likelionassignmentcrud.professor.domain.Department;
import org.likelion.likelionassignmentcrud.professor.application.validator.EnumValid;

// professor 저장 요청 dto 레코드(저장에 필요한 데이터 담고 있음
// 이름, 나이, 학과 정보 받아 professorservice 클래스에서 professorsave
public record ProfessorSaveReqDto(
     @NotBlank(message = "이름을 필수로 입력해야합니다.")
     @Size(min = 2, max = 10, message = "2자 이상 10자 이하로 입력해주세요.")    // String: Size 어노테이션
     String name,

     @NotNull(message = "나이를 필수로 입력해야 합니다.")
     @Positive(message = "연나이를 입력해주세요.")
     @Max(value = 100, message = "1부터 100사이의 값만 입력할 수 있습니다.")   // int: min, max(positive로 양수 제한했기에 min(1) 입력 필요 x)
     int age,

     @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$",
             message = "이메일 형식에 맞지 않습니다.")
     String email,

     @EnumValid(enumClass = Department.class)
     Department department,

     @NotBlank
     @Size(min = 4, max = 10, message = "4자 이상 10자 이하로 입력해주세요.")
     String office
) {
}
