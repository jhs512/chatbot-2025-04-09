package com.back.domain.chatBot.chatMessage.dto;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class ChatBotChatMessageSimpleDto {
    @NonNull
    private final String userMessage;

    @NonNull
    private final String botMessage;

    public ChatBotChatMessageSimpleDto(String userMessage, String botMessage) {
        this.userMessage = userMessage;
        this.botMessage = botMessage;
    }
}

