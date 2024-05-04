package com.everysource.everysource.dto.api;


import com.everysource.everysource.domain.api.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class MemberJoinDTO {


    private String username;
    private String password;

    public MemberJoinDTO(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
    }
}
