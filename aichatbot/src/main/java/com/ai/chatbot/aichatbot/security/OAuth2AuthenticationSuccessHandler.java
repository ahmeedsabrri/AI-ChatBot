package com.ai.chatbot.aichatbot.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final ApplicationEventPublisher eventPublisher;

    public OAuth2AuthenticationSuccessHandler(JwtUtil jwtUtil, ApplicationEventPublisher eventPublisher) {
        this.jwtUtil = jwtUtil;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        
        // First, publish an event to process the OAuth user
        OAuth2UserProcessingEvent event = new OAuth2UserProcessingEvent(oauth2User);
        eventPublisher.publishEvent(event);
        
        // Use username from the OAuth2User's attributes
        String email = oauth2User.getAttribute("email");
        String username = email != null ? email.split("@")[0] : oauth2User.getName();
        
        // Generate token
        String token = jwtUtil.generateToken(username);

        String targetUrl = UriComponentsBuilder.fromUriString("/")
                .queryParam("token", token)
                .queryParam("username", username)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}