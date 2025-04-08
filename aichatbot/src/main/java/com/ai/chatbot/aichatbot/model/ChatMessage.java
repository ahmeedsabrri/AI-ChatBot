package com.ai.chatbot.aichatbot.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message="Prompt cannot be blank")
    private String prompt;

    @Column(columnDefinition = "TEXT")
    private String response;

    @CreationTimestamp
    private LocalDateTime createdAt;


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
}
