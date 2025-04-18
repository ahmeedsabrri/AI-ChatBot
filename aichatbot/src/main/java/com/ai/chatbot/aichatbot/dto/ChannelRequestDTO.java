package com.ai.chatbot.aichatbot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChannelRequestDTO {
    @NotBlank(message = "Channel name cannot be blank")
    @Size(min = 3, max = 50, message = "Channel name must be between 3 and 50 characters")
    private String name;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}