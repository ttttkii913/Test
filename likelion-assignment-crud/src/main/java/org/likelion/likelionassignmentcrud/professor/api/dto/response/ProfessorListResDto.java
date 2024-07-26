package org.likelion.likelionassignmentcrud.professor.api.dto.response;

import lombok.Builder;
import java.util.List;

@Builder
public record ProfessorListResDto(
        List<ProfessorInfoResDto> professors
) {
    public static ProfessorListResDto from(List<ProfessorInfoResDto> professors) {
        return ProfessorListResDto.builder()
                .professors(professors)
                .build();
    }
}