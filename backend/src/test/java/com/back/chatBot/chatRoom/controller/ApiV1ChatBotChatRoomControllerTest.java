package com.back.chatBot.chatRoom.controller;

import com.back.domain.chatBot.chatRoom.controller.ApiV1ChatBotChatRoomController;
import com.back.domain.chatBot.chatRoom.entity.ChatBotChatRoom;
import com.back.domain.chatBot.chatRoom.service.ChatBotChatRoomService;
import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import org.hamcrest.Matchers;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ApiV1ChatBotChatRoomControllerTest {
    @Autowired
    private ChatBotChatRoomService chatBotChatRoomService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("채팅방 생성")
    void t1() throws Exception {
        Member actor = memberService.findByUsername("user1").get();
        String actorAuthToken = memberService.genAuthToken(actor);

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/chatBot/chat/rooms")
                                .header("Authorization", "Bearer " + actorAuthToken)
                                .content("""
                                        {
                                            "title": "채팅방"
                                        }
                                        """)
                                .contentType(
                                        new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        ChatBotChatRoom chatBotChatRoom = chatBotChatRoomService.findLatest().get();

        resultActions
                .andExpect(handler().handlerType(ApiV1ChatBotChatRoomController.class))
                .andExpect(handler().methodName("make"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("%d번 챗봇 채팅방이 생성되었습니다.".formatted(chatBotChatRoom.getId())))
                .andExpect(jsonPath("$.data.id").value(chatBotChatRoom.getId()))
                .andExpect(jsonPath("$.data.createDate").value(Matchers.startsWith(chatBotChatRoom.getCreateDate().toString().substring(0, 20))))
                .andExpect(jsonPath("$.data.modifyDate").value(Matchers.startsWith(chatBotChatRoom.getModifyDate().toString().substring(0, 20))))
                .andExpect(jsonPath("$.data.authorId").value(chatBotChatRoom.getAuthor().getId()))
                .andExpect(jsonPath("$.data.authorName").value(chatBotChatRoom.getAuthor().getName()))
                .andExpect(jsonPath("$.data.authorProfileImgUrl").value(chatBotChatRoom.getAuthor().getProfileImgUrlOrDefault()))
                .andExpect(jsonPath("$.data.title").value(chatBotChatRoom.getTitle()));
    }

    @Test
    @DisplayName("채팅방 생성, with no actor")
    void t2() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/chatBot/chat/rooms")
                                .content("""
                                        {
                                            "title": "채팅방"
                                        }
                                        """)
                                .contentType(
                                        new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.resultCode").value("401-1"))
                .andExpect(jsonPath("$.msg").value("사용자 인증정보가 올바르지 않습니다."));
    }
}