package com.ai.chatbot.aichatbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.ai.chatbot.aichatbot.dto.ApiResponse;
import com.ai.chatbot.aichatbot.dto.ChannelRequestDTO;
import com.ai.chatbot.aichatbot.model.Channel;
import com.ai.chatbot.aichatbot.model.User;
import com.ai.chatbot.aichatbot.service.ChannelService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/channels")
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Channel>> createChannel(
            @Valid @RequestBody ChannelRequestDTO request, 
            @AuthenticationPrincipal User user) {
        Channel channel = channelService.createChannel(request.getName(), user);
        return ResponseEntity.ok(ApiResponse.success("Channel created successfully", channel));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Channel>>> getUserChannels(
            @AuthenticationPrincipal User user) {
        List<Channel> channels = channelService.getUserChannels(user);
        return ResponseEntity.ok(ApiResponse.success("Channels retrieved successfully", channels));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Channel>> getChannel(
            @PathVariable Long id, 
            @AuthenticationPrincipal User user) {
        Channel channel = channelService.getChannel(id, user);
        return ResponseEntity.ok(ApiResponse.success("Channel retrieved successfully", channel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteChannel(
            @PathVariable Long id, 
            @AuthenticationPrincipal User user) {
        channelService.deleteChannel(id, user);
        return ResponseEntity.ok(ApiResponse.success("Channel deleted successfully", null));
    }
}