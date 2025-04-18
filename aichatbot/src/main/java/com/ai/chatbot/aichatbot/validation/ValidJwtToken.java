package com.ai.chatbot.aichatbot.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = JwtTokenValidator.class)
public @interface ValidJwtToken {
    String message() default "Invalid or expired JWT token";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}