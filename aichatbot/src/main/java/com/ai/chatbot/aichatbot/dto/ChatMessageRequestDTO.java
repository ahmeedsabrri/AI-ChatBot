package com.ai.chatbot.aichatbot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChatMessageRequestDTO {
    
    @NotNull
    @NotBlank(message="Prompt cannot be blank")
    String prompt;

    public String getPrompt() {
        return prompt;
    }
}
