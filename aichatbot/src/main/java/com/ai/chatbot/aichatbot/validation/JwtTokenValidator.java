package com.ai.chatbot.aichatbot.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ai.chatbot.aichatbot.security.JwtUtil;

public class JwtTokenValidator implements ConstraintValidator<ValidJwtToken, String> {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void initialize(ValidJwtToken constraintAnnotation) {
    }

    @Override
    public boolean isValid(String token, ConstraintValidatorContext context) {
        if (token == null) {
            return false;
        }

        try {
            return !jwtUtil.isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}