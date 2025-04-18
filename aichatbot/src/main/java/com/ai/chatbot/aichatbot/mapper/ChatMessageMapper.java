package com.ai.chatbot.aichatbot.mapper;

import com.ai.chatbot.aichatbot.dto.ChatMessageResponseDTO;
import com.ai.chatbot.aichatbot.model.ChatMessage;
import org.springframework.data.domain.Page;

public class ChatMessageMapper {
    
    public static ChatMessageResponseDTO ToDto(ChatMessage message) {
        if (message == null) {
            return null;
        }

        ChatMessageResponseDTO dto = new ChatMessageResponseDTO();
        dto.setId(message.getId());
        dto.setPrompt(message.getPrompt());
        dto.setResponse(message.getResponse());
        dto.setCreatedAt(message.getCreatedAt());
        dto.setChannelId(message.getChannel().getId());
        return dto;
    }

    public static Page<ChatMessageResponseDTO> ToPageDto(Page<ChatMessage> messages) {
        return messages.map(ChatMessageMapper::ToDto);
    }
}
