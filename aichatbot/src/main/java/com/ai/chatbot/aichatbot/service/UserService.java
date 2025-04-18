package com.ai.chatbot.aichatbot.service;

import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ai.chatbot.aichatbot.model.User;
import com.ai.chatbot.aichatbot.repository.UserRepository;
import com.ai.chatbot.aichatbot.security.OAuth2UserProcessingEvent;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        
        return user;
    }
    
    @EventListener
    public void handleOAuth2UserProcessing(OAuth2UserProcessingEvent event) {
        OAuth2User oauth2User = event.getOAuth2User();
        processOAuthPostLogin(oauth2User);
    }

    public User processOAuthPostLogin(OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        
        User user = userRepository.findByEmail(email)
            .orElse(new User());
        
        if (user.getId() == null) {
            // For new users, use email as username if no name is provided
            String username = oauth2User.getAttribute("name");
            if (username == null || username.isEmpty()) {
                username = email.split("@")[0]; // Use part before @ as username
            }
            
            user.setEmail(email);
            user.setUsername(username);
            user.setProvider("GOOGLE");
            user.setProviderId(oauth2User.getName());
        }
        
        return userRepository.save(user);
    }

    public User createUser(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setProvider("LOCAL");
        
        return userRepository.save(user);
    }
}
