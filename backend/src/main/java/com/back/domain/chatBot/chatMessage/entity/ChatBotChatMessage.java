package com.back.domain.chatBot.chatMessage.entity;

import com.back.domain.chatBot.chatRoom.entity.ChatBotChatRoom;
import com.back.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class ChatBotChatMessage extends BaseTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatBotChatRoom chatRoom;

    @Column(columnDefinition = "LONGTEXT")
    private String userMessage;
    @Column(columnDefinition = "LONGTEXT")
    private String botMessage;

    private String title;
}
