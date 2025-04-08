package com.ai.chatbot.aichatbot.service;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ai.chatbot.aichatbot.dto.ChatMessageRequestDTO;
import com.ai.chatbot.aichatbot.dto.ChatMessageResponseDTO;
import com.ai.chatbot.aichatbot.mapper.ChatMessageMapper;
import com.ai.chatbot.aichatbot.model.ChatMessage;
import com.ai.chatbot.aichatbot.repository.ChatMessageRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    @Value("${OPENROUTER_API_KEY}")
    private String openRouterApiKey;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessageResponseDTO getChatMessageResponse(ChatMessageRequestDTO chatMessageRequestDTO) {
        try {
            String body = String.format("{\"model\": \"deepseek/deepseek-chat-v3-0324\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}", chatMessageRequestDTO.getPrompt());
            String apiUrl = "https://openrouter.ai/api/v1/chat/completions";

            String responseBody = Request.post(apiUrl)
                    .addHeader("Authorization", "Bearer " + openRouterApiKey)
                    .addHeader("Content-Type", "application/json")
                    .bodyString(body, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            String aiResponse = root.path("choices").get(0).path("message").path("content").asText();

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setPrompt(chatMessageRequestDTO.getPrompt());
            chatMessage.setResponse(aiResponse);
            chatMessageRepository.save(chatMessage);

            return ChatMessageMapper.ToDto(chatMessage);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting chat message response: " + e.getMessage());
        }
    }
}
