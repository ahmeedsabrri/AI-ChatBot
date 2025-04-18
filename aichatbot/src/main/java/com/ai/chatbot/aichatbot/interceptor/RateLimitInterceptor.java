package com.ai.chatbot.aichatbot.interceptor;

import com.ai.chatbot.aichatbot.annotation.RateLimit;
import com.ai.chatbot.aichatbot.config.RateLimitConfig;
import com.ai.chatbot.aichatbot.exception.RateLimitExceededException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    private final RateLimitConfig rateLimitConfig;

    public RateLimitInterceptor(RateLimitConfig rateLimitConfig) {
        this.rateLimitConfig = rateLimitConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RateLimit rateLimit = handlerMethod.getMethodAnnotation(RateLimit.class);

        if (rateLimit == null) {
            return true;
        }

        String ipAddress = getClientIP(request);
        int requests = rateLimitConfig.getRequestCountsPerIpAddress().get(ipAddress);
        
        if (requests >= rateLimit.value()) {
            throw new RateLimitExceededException("Rate limit exceeded. Try again in 1 minute.");
        }
        
        rateLimitConfig.getRequestCountsPerIpAddress().put(ipAddress, requests + 1);
        return true;
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}