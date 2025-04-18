package com.ai.chatbot.aichatbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.ai.chatbot.aichatbot.dto.ApiResponse;
import com.ai.chatbot.aichatbot.dto.ChatMessageRequestDTO;
import com.ai.chatbot.aichatbot.dto.ChatMessageResponseDTO;
import com.ai.chatbot.aichatbot.mapper.ChatMessageMapper;
import com.ai.chatbot.aichatbot.model.ChatMessage;
import com.ai.chatbot.aichatbot.model.User;
import com.ai.chatbot.aichatbot.service.ChatMessageService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/channels/{channelId}/messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ChatMessageResponseDTO>> sendMessage(
            @PathVariable Long channelId,
            @Valid @RequestBody ChatMessageRequestDTO chatMessageRequestDTO,
            @AuthenticationPrincipal User user) {
        ChatMessageResponseDTO response = chatMessageService.getChatMessageResponse(chatMessageRequestDTO, channelId, user);
        return ResponseEntity.ok(ApiResponse.success("Message sent successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChatMessageResponseDTO>>> getChannelMessages(
            @PathVariable Long channelId,
            @AuthenticationPrincipal User user) {
        List<ChatMessageResponseDTO> messages = chatMessageService.getChannelMessages(channelId, user)
            .stream()
            .map(ChatMessageMapper::ToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Messages retrieved successfully", messages));
    }
}
