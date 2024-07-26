package org.likelion.likelionassignmentcrud.professor.application.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.likelion.likelionassignmentcrud.common.error.ErrorCode;
import org.likelion.likelionassignmentcrud.common.exception.CustomException;

public class EnumValidator implements ConstraintValidator<EnumValid, Enum> {
    private EnumValid annotation;

    public void initialize(EnumValid constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Enum department, ConstraintValidatorContext context) throws CustomException {
        if (department == null) {
            throw new CustomException(ErrorCode.VALIDATION_ERROR, "null 값은 들어갈 수 없습니다.");
        }
        Object[] enumConstants = this.annotation.enumClass().getEnumConstants();
        if (enumConstants != null) {
            for (Object enumConstant : enumConstants) {
                if (department == enumConstant) {
                    return true;
                }
            }
        }
        return false;
    }
}