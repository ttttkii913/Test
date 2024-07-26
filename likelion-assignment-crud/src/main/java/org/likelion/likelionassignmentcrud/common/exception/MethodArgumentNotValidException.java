package org.likelion.likelionassignmentcrud.common.exception;

import org.likelion.likelionassignmentcrud.common.error.ErrorCode;

public class MethodArgumentNotValidException extends CustomException {
    public MethodArgumentNotValidException(ErrorCode errorCode, String message) {
        super(errorCode, message);  // CustomException의 생성자를 호출하여 errorCode와 예외 메시지 설정
    }
}