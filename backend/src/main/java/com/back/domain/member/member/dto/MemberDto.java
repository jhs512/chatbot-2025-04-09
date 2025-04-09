package com.back.domain.member.member.dto;

import com.back.domain.member.member.entity.Member;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;


@Getter
public class MemberDto {
    @NonNull
    private final long id;

    @NonNull
    private final LocalDateTime createDate;

    @NonNull
    private final LocalDateTime modifyDate;

    @NonNull
    private final String nickname;

    @NonNull
    private final String profileImgUrl;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.nickname = member.getNickname();
        this.profileImgUrl = member.getProfileImgUrlOrDefault();
    }
}

