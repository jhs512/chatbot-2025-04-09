package com.back.domain.chatBot.chatRoom.service;

import com.back.domain.chatBot.chatRoom.entity.ChatBotChatRoom;
import com.back.domain.chatBot.chatRoom.repository.ChatBotChatRoomRepository;
import com.back.domain.member.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatBotChatRoomService {
    private final ChatBotChatRoomRepository chatBotChatRoomRepository;

    public ChatBotChatRoom make(Member actor, String title) {
        return chatBotChatRoomRepository.save(
                ChatBotChatRoom.builder()
                        .author(actor)
                        .title(title)
                        .build()
        );
    }

    public Optional<ChatBotChatRoom> findLatest() {
        return chatBotChatRoomRepository.findFirstByOrderByIdDesc();
    }

    public Optional<ChatBotChatRoom> findById(long id) {
        return chatBotChatRoomRepository.findById(id);
    }

    public void save(ChatBotChatRoom chatBotChatRoom) {
        chatBotChatRoomRepository.save(chatBotChatRoom);
    }
}
