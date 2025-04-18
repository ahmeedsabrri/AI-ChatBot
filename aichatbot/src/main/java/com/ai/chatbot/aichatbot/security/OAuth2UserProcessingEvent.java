package com.ai.chatbot.aichatbot.security;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2UserProcessingEvent extends ApplicationEvent {
    private final OAuth2User oauth2User;

    public OAuth2UserProcessingEvent(OAuth2User oauth2User) {
        super(oauth2User);
        this.oauth2User = oauth2User;
    }

    public OAuth2User getOAuth2User() {
        return oauth2User;
    }
} 