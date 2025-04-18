package com.ai.chatbot.aichatbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ai.chatbot.aichatbot.dto.ApiResponse;
import com.ai.chatbot.aichatbot.dto.LoginRequestDTO;
import com.ai.chatbot.aichatbot.dto.RegisterRequestDTO;
import com.ai.chatbot.aichatbot.dto.TokenResponse;
import com.ai.chatbot.aichatbot.model.User;
import com.ai.chatbot.aichatbot.security.JwtUtil;
import com.ai.chatbot.aichatbot.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
@Validated
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    @Operation(summary = "Login with username and password",
               description = "Authenticates a user and returns a JWT token")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @Valid @RequestBody LoginRequestDTO loginRequest) {
        User user = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        String token = jwtUtil.generateToken(user.getUsername());
        
        TokenResponse tokenResponse = TokenResponse.of(
            token, 
            user.getUsername(), 
            user.getEmail(), 
            jwtUtil.getExpirationTime()
        );
        
        return ResponseEntity.ok(ApiResponse.success("Login successful", tokenResponse));
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user",
               description = "Creates a new user account and returns a JWT token")
    public ResponseEntity<ApiResponse<TokenResponse>> register(
            @Valid @RequestBody RegisterRequestDTO registerRequest) {
        User user = userService.createUser(
            registerRequest.getUsername(), 
            registerRequest.getEmail(), 
            registerRequest.getPassword()
        );
        
        String token = jwtUtil.generateToken(user.getUsername());
        TokenResponse tokenResponse = TokenResponse.of(
            token, 
            user.getUsername(), 
            user.getEmail(), 
            jwtUtil.getExpirationTime()
        );
        
        return ResponseEntity.ok(ApiResponse.success("Registration successful", tokenResponse));
    }

    @GetMapping("/oauth2/callback")
    @Operation(summary = "OAuth2 callback handler",
               description = "Handles OAuth2 callback and returns a JWT token")
    public ResponseEntity<ApiResponse<TokenResponse>> oauth2Callback(
            @AuthenticationPrincipal OAuth2User oauth2User) {
        User user = userService.processOAuthPostLogin(oauth2User);
        String token = jwtUtil.generateToken(user.getUsername());
        
        TokenResponse tokenResponse = TokenResponse.of(
            token, 
            user.getUsername(), 
            user.getEmail(), 
            jwtUtil.getExpirationTime()
        );
        
        return ResponseEntity.ok(ApiResponse.success("OAuth2 login successful", tokenResponse));
    }
}
