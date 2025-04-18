package com.ai.chatbot.aichatbot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ai.chatbot.aichatbot.model.ChatMessage;
import com.ai.chatbot.aichatbot.model.Channel;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChannelOrderByCreatedAtDesc(Channel channel);
    Page<ChatMessage> findByChannelOrderByCreatedAtDesc(Channel channel, Pageable pageable);
}
