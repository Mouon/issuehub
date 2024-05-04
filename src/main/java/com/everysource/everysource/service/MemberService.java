package com.everysource.everysource.service;

import com.everysource.everysource.domain.api.Member;
import com.everysource.everysource.dto.api.MemberJoinDTO;
import com.everysource.everysource.repository.api.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;


    /** 회원가입 기능 */
    public void join(MemberJoinDTO request){
        Member member = new Member(request);
        memberRepository.save(member);
    }

    /** 로그인 */
    public Member login(String username, String password) {
        Optional<Member> optionalMember = memberRepository.findByUsernameAndPassword(username, password);
        return optionalMember.orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
    }


}
