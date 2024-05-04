package com.everysource.everysource.domain.api;

import com.everysource.everysource.dto.api.MemberJoinDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "member")
    private List<MemberIssueActivity> activities;

    public Member(MemberJoinDTO member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
    }
}
