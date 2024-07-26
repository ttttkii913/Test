package org.likelion.likelionassignmentcrud.common.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {   // Success 상태와 메세지, 코드를 정의
    /**
     * 200 OK   (요청 성공
     */
    GET_SUCCESS(HttpStatus.OK, "성공적으로 조회했습니다."),
    LECTURE_UPDATE_SUCCESS(HttpStatus.OK, "강의가 성공적으로 수정되었습니다."),
    PROFESSOR_UPDATE_SUCCESS(HttpStatus.OK, "교수가 성공적으로 수정되었습니다."),
    LECTURE_DELETE_SUCCESS(HttpStatus.OK, "강의가 성공적으로 삭제되었습니다."),
    PROFESSOR_DELETE_SUCCESS(HttpStatus.OK, "교수가 성공적으로 삭제되었습니다."),

    /**
     * 201 CREATED (POST의 결과 상태
     */
    LECTURE_SAVE_SUCCESS(HttpStatus.CREATED, "강의가 성공적으로 등록되었습니다."),
    PROFESSOR_SAVE_SUCCESS(HttpStatus.CREATED, "교수가 성공적으로 등록되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }

}
