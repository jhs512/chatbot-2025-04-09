package com.back.domain.chatBot.chatRoom.controller;

import com.back.domain.chatBot.chatRoom.dto.ChatBotChatRoomDto;
import com.back.domain.chatBot.chatRoom.entity.ChatBotChatRoom;
import com.back.domain.chatBot.chatRoom.service.ChatBotChatRoomService;
import com.back.domain.member.member.entity.Member;
import com.back.global.rq.Rq;
import com.back.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chatBot/chat/rooms")
@RequiredArgsConstructor
@Tag(name = "ApiV1ChatBotChatRoomController", description = "API 챗봇 채팅방 컨트롤러")
@SecurityRequirement(name = "bearerAuth")
public class ApiV1ChatBotChatRoomController {
    private final Rq rq;
    private final ChatBotChatRoomService chatBotChatRoomService;

    record ChatBotRoomMakeReqBody(
            @NotBlank
            @Size(min = 2, max = 100)
            String title
    ) {
    }

    @PostMapping
    @Transactional
    @Operation(summary = "채팅방 생성")
    public RsData<ChatBotChatRoomDto> make(
            @RequestBody @Valid ChatBotRoomMakeReqBody reqBody
    ) {
        Member actor = rq.getActor();

        ChatBotChatRoom chatRoom = chatBotChatRoomService.make(
                actor,
                reqBody.title
        );

        return new RsData<>(
                "201-1",
                "%d번 챗봇 채팅방이 생성되었습니다.".formatted(chatRoom.getId()),
                new ChatBotChatRoomDto(chatRoom)
        );
    }
}
