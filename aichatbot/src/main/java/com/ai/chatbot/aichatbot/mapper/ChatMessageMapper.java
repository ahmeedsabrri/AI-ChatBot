package com.ai.chatbot.aichatbot.mapper;

import com.ai.chatbot.aichatbot.dto.ChatMessageResponseDTO;
import com.ai.chatbot.aichatbot.model.ChatMessage;

public class ChatMessageMapper {

    public static ChatMessageResponseDTO ToDto(ChatMessage chatMessage) {
        ChatMessageResponseDTO chatMessageResponseDTO = new ChatMessageResponseDTO();
        chatMessageResponseDTO.setPrompt(chatMessage.getPrompt());
        chatMessageResponseDTO.setReply(chatMessage.getResponse());
        chatMessageResponseDTO.setCreatedAt(chatMessage.getCreatedAt().toString());
        return chatMessageResponseDTO;
    }
}
