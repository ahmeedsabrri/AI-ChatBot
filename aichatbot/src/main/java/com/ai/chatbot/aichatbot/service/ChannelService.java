package com.ai.chatbot.aichatbot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.chatbot.aichatbot.exception.UnauthorizedChannelAccessException;
import com.ai.chatbot.aichatbot.model.Channel;
import com.ai.chatbot.aichatbot.model.User;
import com.ai.chatbot.aichatbot.repository.ChannelRepository;

import java.util.List;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;

    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public Channel createChannel(String name, User user) {
        Channel channel = new Channel();
        channel.setName(name.trim());
        channel.setUser(user);
        return channelRepository.save(channel);
    }

    public List<Channel> getUserChannels(User user) {
        return channelRepository.findByUser(user);
    }

    public Channel getChannel(Long id, User user) {
        return channelRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new UnauthorizedChannelAccessException("Channel not found or access denied"));
    }

    public void deleteChannel(Long id, User user) {
        Channel channel = getChannel(id, user);
        channelRepository.delete(channel);
    }
}