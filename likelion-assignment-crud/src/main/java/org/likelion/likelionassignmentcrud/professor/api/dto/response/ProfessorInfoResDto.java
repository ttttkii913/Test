package org.likelion.likelionassignmentcrud.professor.api.dto.response;

import lombok.Builder;
import org.likelion.likelionassignmentcrud.professor.domain.Department;
import org.likelion.likelionassignmentcrud.professor.domain.Professor;

@Builder
public record ProfessorInfoResDto(  // 이름, 나이, 이메일, 학과, 연구실
        String name,
        int age,
        String email,
        Department department,
        String office
) {
    public static ProfessorInfoResDto from(Professor professor) {
        return ProfessorInfoResDto.builder()
                .name(professor.getName())
                .age(professor.getAge())
                .email(professor.getEmail())
                .department(professor.getDepartment())
                .office(professor.getOffice())
                .build();
    }
}