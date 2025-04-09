package com.back.domain.chatBot.chatRoom.repository;

import com.back.domain.chatBot.chatRoom.entity.ChatBotChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatBotChatRoomRepository extends JpaRepository<ChatBotChatRoom, Long> {
    Optional<ChatBotChatRoom> findFirstByOrderByIdDesc();
}
