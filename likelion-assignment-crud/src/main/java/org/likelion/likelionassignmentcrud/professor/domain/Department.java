package org.likelion.likelionassignmentcrud.professor.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.likelion.likelionassignmentcrud.common.error.ErrorCode;
import org.likelion.likelionassignmentcrud.common.exception.CustomException;

import java.util.stream.Stream;

public enum Department {  //사전에 정의된 상수들의 집합
    SOFTWARE, COMPUTER_SCIENCE;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Department findByDepartment(String department) {
        return Stream.of(Department.values())
                .filter(c -> c.equals(department))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.VALIDATION_ERROR,"enum에 없는 값은 들어갈 수 없습니다."));
    }

}
