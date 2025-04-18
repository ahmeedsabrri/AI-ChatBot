package com.ai.chatbot.aichatbot.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "chat_message")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "ChatMessage entity representing a message in a chat conversation")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the message")
    private Long id;

    @NonNull
    @NotBlank(message="Prompt cannot be blank")
    @Schema(description = "User's input message", example = "What is artificial intelligence?")
    private String prompt;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "AI's response to the prompt", example = "Artificial Intelligence (AI) is the simulation of human intelligence by machines...")
    private String response;

    @CreationTimestamp
    @Schema(description = "Timestamp when the message was created")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    @JsonBackReference
    @Schema(description = "Channel where the message belongs")
    private Channel channel;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setPrompt(@NonNull @NotBlank String prompt) {
        this.prompt = prompt;
    }
    public String getPrompt() {
        return prompt;
    }
    public void setResponse(String response) {
        this.response = response;
    }
    public String getResponse() {
        return response;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
