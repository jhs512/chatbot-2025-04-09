package com.back.chatBot.chatRoom.controller;

import com.back.domain.chatBot.chatRoom.controller.ApiV1ChatBotChatRoomController;
import com.back.domain.chatBot.chatRoom.entity.ChatBotChatRoom;
import com.back.domain.chatBot.chatRoom.service.ChatBotRoomService;
import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import com.back.domain.post.post.service.PostService;
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
    private PostService postService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mvc;

//    @Test
//    @DisplayName("1번글 조회")
//    void t1() throws Exception {
//        ResultActions resultActions = mvc
//                .perform(
//                        get("/api/v1/posts/1")
//                )
//                .andDo(print());
//
//        Post post = postService.findById(1).get();
//
//        resultActions
//                .andExpect(handler().handlerType(ApiV1PostController.class))
//                .andExpect(handler().methodName("item"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(post.getId()))
//                .andExpect(jsonPath("$.createDate").value(Matchers.startsWith(post.getCreateDate().toString().substring(0, 20))))
//                .andExpect(jsonPath("$.modifyDate").value(Matchers.startsWith(post.getModifyDate().toString().substring(0, 20))))
//                .andExpect(jsonPath("$.authorId").value(post.getAuthor().getId()))
//                .andExpect(jsonPath("$.authorName").value(post.getAuthor().getName()))
//                .andExpect(jsonPath("$.authorProfileImgUrl").value(post.getAuthor().getProfileImgUrlOrDefault()))
//                .andExpect(jsonPath("$.thumbnailImgUrl").value(post.getThumbnailImgUrlOrDefault()))
//                .andExpect(jsonPath("$.title").value(post.getTitle()))
//                .andExpect(jsonPath("$.content").value(post.getContent()))
//                .andExpect(jsonPath("$.published").value(post.isPublished()))
//                .andExpect(jsonPath("$.listed").value(post.isListed()));
//    }
//
//    @Test
//    @DisplayName("존재하지 않는 1000000번글 조회, 404")
//    void t2() throws Exception {
//        ResultActions resultActions = mvc
//                .perform(
//                        get("/api/v1/posts/1000000")
//                )
//                .andDo(print());
//
//        resultActions
//                .andExpect(handler().handlerType(ApiV1PostController.class))
//                .andExpect(handler().methodName("item"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.resultCode").value("404-1"))
//                .andExpect(jsonPath("$.msg").value("해당 데이터가 존재하지 않습니다."));
//    }
//
//    @Test
//    @DisplayName("글 작성")
//    void t3() throws Exception {
//        Member actor = memberService.findByUsername("user1").get();
//        String actorAuthToken = memberService.genAuthToken(actor);
//
//        ResultActions resultActions = mvc
//                .perform(
//                        post("/api/v1/posts")
//                                .header("Authorization", "Bearer " + actorAuthToken)
//                                .content("""
//                                        {
//                                            "title": "제목 new",
//                                            "content": "내용 new",
//                                            "published": true,
//                                            "listed": false
//                                        }
//                                        """)
//                                .contentType(
//                                        new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
//                                )
//                )
//                .andDo(print());
//
//        Post post = postService.findLatest().get();
//
//        assertThat(post.getAuthor()).isEqualTo(actor);
//
//        resultActions
//                .andExpect(handler().handlerType(ApiV1PostController.class))
//                .andExpect(handler().methodName("write"))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.resultCode").value("201-1"))
//                .andExpect(jsonPath("$.msg").value("%d번 글이 작성되었습니다.".formatted(post.getId())))
//                .andExpect(jsonPath("$.data.id").value(post.getId()))
//                .andExpect(jsonPath("$.data.createDate").value(Matchers.startsWith(post.getCreateDate().toString().substring(0, 20))))
//                .andExpect(jsonPath("$.data.modifyDate").value(Matchers.startsWith(post.getModifyDate().toString().substring(0, 20))))
//                .andExpect(jsonPath("$.data.authorId").value(post.getAuthor().getId()))
//                .andExpect(jsonPath("$.data.authorName").value(post.getAuthor().getName()))
//                .andExpect(jsonPath("$.data.authorProfileImgUrl").value(post.getAuthor().getProfileImgUrlOrDefault()))
//                .andExpect(jsonPath("$.data.thumbnailImgUrl").value(post.getThumbnailImgUrlOrDefault()))
//                .andExpect(jsonPath("$.data.title").value(post.getTitle()))
//                .andExpect(jsonPath("$.data.published").value(post.isPublished()))
//                .andExpect(jsonPath("$.data.listed").value(post.isListed()));
//    }
//
//    @Test
//    @DisplayName("글 작성, with no input")
//    void t4() throws Exception {
//        Member actor = memberService.findByUsername("user1").get();
//        String actorAuthToken = memberService.genAuthToken(actor);
//
//        ResultActions resultActions = mvc
//                .perform(
//                        post("/api/v1/posts")
//                                .header("Authorization", "Bearer " + actorAuthToken)
//                                .content("""
//                                        {
//                                            "title": "",
//                                            "content": ""
//                                        }
//                                        """)
//                                .contentType(
//                                        new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
//                                )
//                )
//                .andDo(print());
//
//        resultActions
//                .andExpect(handler().handlerType(ApiV1PostController.class))
//                .andExpect(handler().methodName("write"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.resultCode").value("400-1"))
//                .andExpect(jsonPath("$.msg").value("""
//                        content-NotBlank-must not be blank
//                        content-Size-size must be between 2 and 10000000
//                        title-NotBlank-must not be blank
//                        title-Size-size must be between 2 and 100
//                        """.stripIndent().trim()));
//    }

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

        ChatBotChatRoom chatBotChatRoom = chatBotRoomService.findLatest().get();

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

    @Autowired
    private ChatBotRoomService chatBotRoomService;
}