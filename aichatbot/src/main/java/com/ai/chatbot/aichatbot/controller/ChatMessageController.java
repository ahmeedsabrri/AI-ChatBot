package com.ai.chatbot.aichatbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.chatbot.aichatbot.dto.ChatMessageRequestDTO;
import com.ai.chatbot.aichatbot.dto.ChatMessageResponseDTO;
import com.ai.chatbot.aichatbot.service.ChatMessageService;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatMessageController {

   private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
         this.chatMessageService = chatMessageService;
    }

    @PostMapping("/message")
    public ResponseEntity<ChatMessageResponseDTO> getChatMessageResponse(@RequestBody ChatMessageRequestDTO chatMessageRequestDTO) {
        ChatMessageResponseDTO response = chatMessageService.getChatMessageResponse(chatMessageRequestDTO);
        return ResponseEntity.ok(response);
    }
}
