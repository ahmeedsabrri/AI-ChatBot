package com.ai.chatbot.aichatbot.service;

import java.util.List;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ai.chatbot.aichatbot.dto.ChatMessageRequestDTO;
import com.ai.chatbot.aichatbot.dto.ChatMessageResponseDTO;
import com.ai.chatbot.aichatbot.mapper.ChatMessageMapper;
import com.ai.chatbot.aichatbot.model.Channel;
import com.ai.chatbot.aichatbot.model.ChatMessage;
import com.ai.chatbot.aichatbot.model.User;
import com.ai.chatbot.aichatbot.repository.ChatMessageRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChannelService channelService;

    @Value("${openrouter.api-key}")
    private String openRouterApiKey;

    public ChatMessageService(ChatMessageRepository chatMessageRepository, ChannelService channelService) {
        this.chatMessageRepository = chatMessageRepository;
        this.channelService = channelService;
    }

    public ChatMessageResponseDTO getChatMessageResponse(ChatMessageRequestDTO chatMessageRequestDTO, Long channelId, User user) {
        try {
            Channel channel = channelService.getChannel(channelId, user);

            // Simplified API request
            String prompt = chatMessageRequestDTO.getPrompt();
            String aiResponse = getAIResponse(prompt);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setPrompt(prompt);
            chatMessage.setResponse(aiResponse);
            chatMessage.setChannel(channel);
            chatMessageRepository.save(chatMessage);

            return ChatMessageMapper.ToDto(chatMessage);
        } catch (Exception e) {
            throw new RuntimeException("Error processing chat message: " + e.getMessage());
        }
    }

    private String getAIResponse(String prompt) throws Exception {
        String body = String.format("{\"model\": \"deepseek/deepseek-chat-v3-0324\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}", prompt);
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
        return root.path("choices").get(0).path("message").path("content").asText();
    }

    public List<ChatMessage> getChannelMessages(Long channelId, User user) {
        Channel channel = channelService.getChannel(channelId, user);
        return chatMessageRepository.findByChannelOrderByCreatedAtDesc(channel);
    }
}