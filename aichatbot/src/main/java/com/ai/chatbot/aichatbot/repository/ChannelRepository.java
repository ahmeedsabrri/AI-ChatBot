package com.ai.chatbot.aichatbot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.chatbot.aichatbot.model.Channel;
import com.ai.chatbot.aichatbot.model.User;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    List<Channel> findByUser(User user);
    Optional<Channel> findByIdAndUser(Long id, User user);
}