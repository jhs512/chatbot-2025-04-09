package com.back.domain.member.member.dto;

import com.back.domain.member.member.entity.Member;
import lombok.Getter;
import org.springframework.lang.NonNull;


@Getter
public class MemberWithUsernameDto extends MemberDto {
    @NonNull
    private final String username;

    public MemberWithUsernameDto(Member member) {
        super(member);
        this.username = member.getUsername();
    }
}

