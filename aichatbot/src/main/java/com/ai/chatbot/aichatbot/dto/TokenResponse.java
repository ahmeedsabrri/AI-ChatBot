package com.ai.chatbot.aichatbot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {
    private String token;
    private String username;
    private String email;
    private Long expiresIn;
    private String tokenType = "Bearer";

    // Static factory methods
    public static TokenResponse of(String token, String username) {
        TokenResponse response = new TokenResponse();
        response.setToken(token);
        response.setUsername(username);
        return response;
    }

    public static TokenResponse of(String token, String username, String email, Long expiresIn) {
        TokenResponse response = new TokenResponse();
        response.setToken(token);
        response.setUsername(username);
        response.setEmail(email);
        response.setExpiresIn(expiresIn);
        return response;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}