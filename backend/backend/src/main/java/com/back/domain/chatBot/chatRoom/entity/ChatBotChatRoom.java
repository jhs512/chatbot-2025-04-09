package com.back.domain.chatBot.chatRoom.entity;

import com.back.domain.member.member.entity.Member;
import com.back.global.jpa.entity.BaseTime;
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
public class ChatBotChatRoom extends BaseTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    private String title;
}

