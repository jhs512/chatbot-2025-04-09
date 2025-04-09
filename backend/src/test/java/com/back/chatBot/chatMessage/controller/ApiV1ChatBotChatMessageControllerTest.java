package com.back.chatBot.chatMessage.controller;

import com.back.domain.chatBot.chatMessage.controller.ApiV1ChatBotChatMessageController;
import com.back.domain.chatBot.chatRoom.entity.ChatBotChatRoom;
import com.back.domain.chatBot.chatRoom.service.ChatBotChatRoomService;
import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ApiV1ChatBotChatMessageControllerTest {
    @Autowired
    private ChatBotChatRoomService chatBotChatRoomService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("채팅 메세지 생성")
    void t1() throws Exception {
        Member actor = memberService.findByUsername("user1").get();
        String actorAuthToken = memberService.genAuthToken(actor);

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/chatBot/chat/rooms/1/messages")
                                .header("Authorization", "Bearer " + actorAuthToken)
                                .content("""
                                        {
                                            "userMessage": "이 서비스의 이름은 뭐야?"
                                        }
                                        """)
                                .contentType(
                                        new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        ChatBotChatRoom chatBotChatRoom = chatBotChatRoomService.findById(1L).get();

        resultActions
                .andExpect(handler().handlerType(ApiV1ChatBotChatMessageController.class))
                .andExpect(handler().methodName("make"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("새 대화가 생성되었습니다."))
                .andExpect(jsonPath("$.data.botMessage").value(containsString("plan-it")));

    }

    @Test
    @DisplayName("일정 확인가능한지?")
    void t2() throws Exception {
        Member actor = memberService.findByUsername("user1").get();
        String actorAuthToken = memberService.genAuthToken(actor);

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/chatBot/chat/rooms/1/messages")
                                .header("Authorization", "Bearer " + actorAuthToken)
                                .content("""
                                        {
                                            "userMessage": "나 내일 일정 뭐지?"
                                        }
                                        """)
                                .contentType(
                                        new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        ChatBotChatRoom chatBotChatRoom = chatBotChatRoomService.findById(1L).get();

        resultActions
                .andExpect(handler().handlerType(ApiV1ChatBotChatMessageController.class))
                .andExpect(handler().methodName("make"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("새 대화가 생성되었습니다."))
                .andExpect(jsonPath("$.data.botMessage").value(containsString("쇼핑")))
                .andExpect(jsonPath("$.data.botMessage").value(containsString("제주도 숙소")));

    }
}