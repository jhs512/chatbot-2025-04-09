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

    public String getSystemMessageContent(ChatBotChatRoom chatBotChatRoom) {
        StringBuilder sb = new StringBuilder();

        sb.append("""
                당신은 한국인입니다. 당신은 한국어로 대답해야 합니다.
                이 채팅의 목적인 plan-it 이라는 서비스를 이용하는 고객에게 해당 서비스의 정보를 제공하는데 있습니다.
                plan-it 은 고객이 여행을 계획하는데 도움을 주는 서비스입니다.
                """.stripIndent().trim()
        );

        Member author = chatBotChatRoom.getAuthor();

        sb.append("\n");

        sb.append("현재시각 : 2025-04-09 17:10:00\n");

        sb.append("# 일정 105(2025-04-10 ~ 2025-04-11) 시작");

        sb.append("Day1 세부일정 103434 : 시작 2025-04-10 12:00, 쇼핑\n");
        sb.append("Day1 세부일정 103435 : 시작 2025-04-10 14:00, 제주도 숙소로 이동\n");
        sb.append("Day2 세부일정 103436 : 시작 2025-04-11 14:00, 제주도 숙소에서 퇴실\n");

        sb.append("# 일정 105 끝\n");

        return sb.toString();
    }
}
