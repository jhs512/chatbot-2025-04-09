package com.back.global.initData;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import com.back.global.app.AppConfig;
import com.back.global.app.CustomConfigProperties;
import com.back.standard.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final CustomConfigProperties customConfigProperties;
    private final MemberService memberService;
    private final PostService postService;
    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        if (memberService.count() > 0) return;

        if (AppConfig.isTest()) {
            Ut.file.rm(AppConfig.getGenFileDirPath());
        }

        Member memberSystem = memberService.join("system", "1234", "시스템", "");
        if (AppConfig.isNotProd()) memberSystem.setApiKey("system");

        Member memberAdmin = memberService.join("admin", "1234", "관리자", "");
        if (AppConfig.isNotProd()) memberAdmin.setApiKey("admin");

        Member memberUser1 = memberService.join("user1", "1234", "유저1", "");
        if (AppConfig.isNotProd()) memberUser1.setApiKey("user1");

        Member memberUser2 = memberService.join("user2", "1234", "유저2", "");
        if (AppConfig.isNotProd()) memberUser2.setApiKey("user2");

        Member memberUser3 = memberService.join("user3", "1234", "유저3", "");
        if (AppConfig.isNotProd()) memberUser3.setApiKey("user3");

        Member memberUser4 = memberService.join("user4", "1234", "유저4", "");
        if (AppConfig.isNotProd()) memberUser4.setApiKey("user4");

        Member memberUser5 = memberService.join("user5", "1234", "유저5", "");
        if (AppConfig.isNotProd()) memberUser5.setApiKey("user5");

        Member memberUser6 = memberService.join("user6", "1234", "유저6", "");
        if (AppConfig.isNotProd()) memberUser6.setApiKey("user6");

        for (var notProdMember : customConfigProperties.getNotProdMembers()) {
            var member = memberService.join(
                    notProdMember.username(),
                    "",
                    notProdMember.nickname(),
                    notProdMember.profileImgUrl()
            );

            if (AppConfig.isNotProd()) member.setApiKey(notProdMember.apiKey());
        }
    }

    @Transactional
    public void work2() {
        if (postService.count() > 0) return;

        Member memberUser1 = memberService.findByUsername("user1").get();
        Member memberUser2 = memberService.findByUsername("user2").get();
        Member memberUser3 = memberService.findByUsername("user3").get();
        Member memberUser4 = memberService.findByUsername("user4").get();
        Member memberUser5 = memberService.findByUsername("user5").get();
        Member memberUser6 = memberService.findByUsername("user6").get();

        Post post1 = postService.write(
                memberUser1,
                "2025-05-10 ~ 2025-05-12",
                "",
                true,
                true
        );

        post1.addComment(memberUser2, "DAY 1 : 2025-05-10 14:00 : 장보기");
        post1.addComment(memberUser3, "DAY 2 : 2025-05-11 14:00 : 성산일출봉");
    }
}