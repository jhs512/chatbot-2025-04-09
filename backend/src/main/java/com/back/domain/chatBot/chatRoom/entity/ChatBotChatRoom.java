package com.back.domain.chatBot.chatRoom.entity;

import com.back.domain.chatBot.chatMessage.entity.ChatBotChatMessage;
import com.back.domain.member.member.entity.Member;
import com.back.global.exceptions.ServiceException;
import com.back.global.jpa.entity.BaseTime;
import com.back.global.rsData.RsData;
import com.back.standard.base.Empty;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class ChatBotChatRoom extends BaseTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    private String title;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<ChatBotChatMessage> chatMessages = new ArrayList<>();

    public ChatBotChatMessage addChatMessage(String userMessage, String botMessage) {
        ChatBotChatMessage chatMessage = ChatBotChatMessage.builder()
                .userMessage(userMessage)
                .botMessage(botMessage)
                .chatRoom(this)
                .build();

        chatMessages.add(chatMessage);

        return chatMessage;
    }

    public RsData<Empty> getCheckActorCanAddMessageRs(Member actor) {
        if (actor == null) return new RsData<>("401-1", "로그인 후 이용해주세요.");

        if (actor.isAdmin()) return RsData.OK;

        if (actor.equals(author)) return RsData.OK;

        return new RsData<>("403-1", "채팅방 생성자만 메세지를 작성할 수 있습니다.");
    }

    public void checkActorCanAddMessage(Member actor) {
        Optional.of(
                        getCheckActorCanAddMessageRs(actor)
                )
                .filter(RsData::isFail)
                .ifPresent(rsData -> {
                    throw new ServiceException(rsData.getResultCode(), rsData.getMsg());
                });
    }
}

