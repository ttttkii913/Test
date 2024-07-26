package org.likelion.likelionassignmentcrud.common.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.likelionassignmentcrud.common.dto.BaseResponse;
import org.likelion.likelionassignmentcrud.common.error.ErrorCode;
import org.likelion.likelionassignmentcrud.common.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j  // 로깅을 위한 로거 생성 어노테이션
@RestControllerAdvice   // REST API 컨트롤러에 대한 예외 처리 어드바이스임을 나타내는 어노테이션
@Component  // 클래스를 Spring component로 등록
@RequiredArgsConstructor    // 초기화 되지 않은 final 필드에 대한 생성자 자동 생성
public class CustomExceptionAdvice {
    /**
     * 500 Internal Server Error
     */
    // 원인 모를 이유의 예외 발생 시
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)   // 예외 발생 시 500 반환
    @ExceptionHandler(Exception.class)  // Exception 클래스의 모든 하위 예외 처리
    public BaseResponse handlerServerException(final Exception e) {
        log.error("Internal Server Error: {}", e.getMessage(), e);  // 예외 발생 시 로그를 남김
        return BaseResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 400 BAD REQUEST
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)     // 예외 발생 시 400 반환
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        // 에러 메시지 생성
        Map<String, String> errorMap = new HashMap<>(); // 에러 메시지를 저장할 errorMap 생성
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());    // 필드 이름과 에러 메시지를 Map에 추가
        }
        // 응답 생성
        return BaseResponse.error(ErrorCode.VALIDATION_ERROR, convertMapToString(errorMap));
    }

    private String convertMapToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append(", ");
        }
        sb.deleteCharAt(sb.length() - 1);   // 마지막 띄어쓰기 제거
        sb.deleteCharAt(sb.length() - 1);   // 마지막 쉼표 제거
        return sb.toString();
    }

    /**
     *  Custom error
     */
    // 내부 커스텀 에러 처리하기
    @ExceptionHandler(CustomException.class)
    public BaseResponse handlerCustomException(CustomException e) {
        log.error("Custom Exception: {}", e.getMessage(), e);
        return BaseResponse.error(e.getErrorCode(), e.getMessage());    // 에러 코드와 에러 메시지로 BaseResponse 리턴
    }

}
