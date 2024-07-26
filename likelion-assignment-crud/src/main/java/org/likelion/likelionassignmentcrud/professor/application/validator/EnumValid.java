package org.likelion.likelionassignmentcrud.professor.application.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = EnumValidator.class)
@Target( { METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface EnumValid {
    String message() default "SOFTWARE나 COMPUTER_SCIENCE로 작성해주세요.";

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    Class<? extends java.lang.Enum<?>> enumClass();

}
