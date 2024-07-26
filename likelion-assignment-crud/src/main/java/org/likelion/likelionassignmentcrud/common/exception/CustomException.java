package org.likelion.likelionassignmentcrud.common.exception;

import lombok.Getter;
import org.likelion.likelionassignmentcrud.common.error.ErrorCode;

@Getter
public class CustomException extends RuntimeException{  // RuntimeException 상속
    // ErrorCode 변수 선언, 예외와 관련된 오류 코드 저장
    private final ErrorCode errorCode;

    // ErrorCode 객체와 예외 메시지를 전달받아 초기화하는 constructor
    public CustomException(ErrorCode error, String message) {
        super(message);             // RuntimeException의 생성자를 호출하여 예외 메시지를 설정
        this.errorCode = error;     // errorCode 필드에 전달받은 ErrorCode 객체를 할당
    }

    public int getHttpStatus() {    // Http 상태 반환
        return errorCode.getHttpStatusCode();
    }
}
