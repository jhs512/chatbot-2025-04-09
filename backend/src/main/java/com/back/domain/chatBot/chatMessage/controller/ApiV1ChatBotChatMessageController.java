package com.back.domain.chatBot.chatMessage.controller;

import com.back.domain.chatBot.chatMessage.dto.ChatBotChatMessageSimpleDto;
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
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chatBot/chat/rooms/{chatRoomId}/messages")
@RequiredArgsConstructor
@Tag(name = "ApiV1ChatBotChatMessageController", description = "API 챗봇 채팅 메세지 컨트롤러")
@SecurityRequirement(name = "bearerAuth")
public class ApiV1ChatBotChatMessageController {
    @Autowired
    @Lazy
    private ApiV1ChatBotChatMessageController self;
    private final Rq rq;
    private final ChatBotChatRoomService chatBotChatRoomService;
    private final OpenAiChatModel chatClient;

    record ChatBotMessageMakeReqBody(
            @NotBlank
            @Size(min = 2, max = 100)
            String userMessage
    ) {
    }

    @PostMapping
    @Operation(summary = "메세지 작성")
    @Transactional(readOnly = true)
    public RsData<ChatBotChatMessageSimpleDto> make(
            @PathVariable long chatRoomId,
            @RequestBody @Valid ChatBotMessageMakeReqBody reqBody
    ) {
        ChatBotChatRoom chatBotChatRoom = chatBotChatRoomService.findById(chatRoomId).get();

        Member actor = rq.getActor();

        chatBotChatRoom.checkActorCanAddMessage(actor);

        // 시스템 메시지 추가 (한국인 컨텍스트)
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage(chatBotChatRoomService.getSystemMessageContent(chatBotChatRoom)));

        chatBotChatRoom
                .getChatMessages()
                .forEach(chatMessage -> {
                    messages.add(new UserMessage(chatMessage.getUserMessage()));
                    messages.add(new AssistantMessage(chatMessage.getBotMessage()));
                });

        messages.add(new UserMessage(reqBody.userMessage));

        String botMessage = chatClient.call(new Prompt(messages))
                .getResult()
                .getOutput()
                .getText();

        self.addChatMessage(
                chatBotChatRoom,
                reqBody.userMessage,
                botMessage
        );

        return new RsData<>(
                "201-1",
                "새 대화가 생성되었습니다.",
                new ChatBotChatMessageSimpleDto(
                        reqBody.userMessage,
                        botMessage
                )
        );
    }

    @Transactional
    public void addChatMessage(
            ChatBotChatRoom chatBotChatRoom,
            String userMessage,
            String botMessage
    ) {
        chatBotChatRoom.addChatMessage(
                userMessage,
                botMessage
        );

        chatBotChatRoomService.save(chatBotChatRoom);
    }
}
