package com.ai.chatbot.aichatbot.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class RateLimitConfig {
    private final LoadingCache<String, Integer> requestCountsPerIpAddress;

    public RateLimitConfig() {
        super();
        requestCountsPerIpAddress = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public LoadingCache<String, Integer> getRequestCountsPerIpAddress() {
        return requestCountsPerIpAddress;
    }
}