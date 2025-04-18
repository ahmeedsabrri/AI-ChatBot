package com.ai.chatbot.aichatbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedChannelAccessException extends RuntimeException {
    public UnauthorizedChannelAccessException(String message) {
        super(message);
    }

    public UnauthorizedChannelAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}