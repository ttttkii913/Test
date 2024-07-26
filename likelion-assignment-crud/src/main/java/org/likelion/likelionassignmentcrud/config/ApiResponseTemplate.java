package org.likelion.likelionassignmentcrud.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponseTemplate<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private T data;
    private String message;

    public static <T> ApiResponseTemplate<T> successResponse(T data) {
        return new ApiResponseTemplate<>(SUCCESS_STATUS, data, null);
    }

    public static ApiResponseTemplate<?> successWithNoContent() {
        return new ApiResponseTemplate<>(SUCCESS_STATUS, null, null);
    }

    public static ApiResponseTemplate<?> failResponse(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponseTemplate<>(FAIL_STATUS, errors, null);
    }

    public static ApiResponseTemplate<?> errorResponse(String message) {
        return new ApiResponseTemplate<>(ERROR_STATUS, null, message);
    }

    private ApiResponseTemplate(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}