package org.likelion.likelionassignmentcrud.common.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode { // 반복적으로 사용될 Error 상태와 메세지, 코드를 정의
                        // 시스템에 비정상적인 상황이 발생했을 경우
    /**
     * 404 NOT FOUND
     */
    LECTURES_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 강의가 없습니다. lectureId=", "NOT_FOUND_404"),
    PROFESSORS_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 교수가 없습니다. professorId=", "NOT_FOUND_404"),

    /**
     * 500 INTERNAL SERVER ERROR
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생했습니다.", "INTERNAL_SERVER_ERROR_500"),

    /**
     * 400 BAD REQUEST
     */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사에 맞지 않습니다.", "BAD_REQUEST_400");

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
