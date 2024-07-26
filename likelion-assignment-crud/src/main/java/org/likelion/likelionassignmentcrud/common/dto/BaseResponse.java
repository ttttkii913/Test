package org.likelion.likelionassignmentcrud.common.dto;

import lombok.*;
import org.likelion.likelionassignmentcrud.common.error.ErrorCode;
import org.likelion.likelionassignmentcrud.common.error.SuccessCode;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Builder
public class BaseResponse<T> {  // 제네릭 타입으로 선언
    // 상태코드, 메시지, 데이터 등을 공통된 응답 구조를 제공하기 위해 만드는 클래스 -> 발생하는 예외를 일관적으로 반환 가능

    private final int code;             // 상태 코드를 나타내는 code
    private final String message;       // 상태 메시지를 나타내는 message 필드
    private T data;                     // 제네릭 타입 T의 data 필드 선언

    // 전달할 data 없이 단순히 상태코드와 메세지만 전달할 경우
    public static BaseResponse success (SuccessCode success) {
        return new BaseResponse<>(success.getHttpStatusCode(), success.getMessage());
    }

    // 제네릭 메소드로 데이터를 포함하는 성공 응답 객체를 생성
    public static <T> BaseResponse<T> success(SuccessCode success, T data) {
        return new BaseResponse<T>(success.getHttpStatusCode(), success.getMessage(), data);
    }

    // ErrorCode 정보를 사용하여 오류 응답 객체 BaseResponse를 생성
    public static BaseResponse error(ErrorCode error) {
        return new BaseResponse<>(error.getHttpStatusCode(), error.getMessage());
    }

    // ErrorCode 정보를 사용하되, 사용자 정의 메세지로 오류 응답 객체 BaseResponse 생성
    public static BaseResponse error(ErrorCode error, String message) {
        return new BaseResponse<>(error.getHttpStatusCode(), message);
    }
}
