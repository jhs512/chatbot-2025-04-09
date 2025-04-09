package com.back.domain.chatBot.chatRoom.dto;

import com.back.domain.chatBot.chatRoom.entity.ChatBotChatRoom;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Getter
public class ChatBotChatRoomDto {
    @NonNull
    private final long id;

    @NonNull
    private final LocalDateTime createDate;

    @NonNull
    private final LocalDateTime modifyDate;

    @NonNull
    private final long authorId;

    @NonNull
    private final String authorName;

    @NonNull
    private final String authorProfileImgUrl;

    @NonNull
    private final String title;

    public ChatBotChatRoomDto(ChatBotChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.createDate = chatRoom.getCreateDate();
        this.modifyDate = chatRoom.getModifyDate();
        this.authorId = chatRoom.getAuthor().getId();
        this.authorName = chatRoom.getAuthor().getName();
        this.authorProfileImgUrl = chatRoom.getAuthor().getProfileImgUrlOrDefault();
        this.title = chatRoom.getTitle();
    }
}
