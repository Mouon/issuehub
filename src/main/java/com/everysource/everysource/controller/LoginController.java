package com.everysource.everysource.controller;

import com.everysource.everysource.domain.api.Member;
import com.everysource.everysource.dto.api.LoginRequest;
import com.everysource.everysource.dto.api.MemberJoinDTO;
import com.everysource.everysource.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private MemberService memberService;


    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberJoinDTO request) {
        try {
            memberService.join(request);
            return ResponseEntity.ok("회원가입 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        try {
            Member member = memberService.login(request.getUsername(), request.getPassword());
            session.setAttribute("memberId", member.getId());
            return ResponseEntity.ok("{\"message\":\"로그인 성공\", \"memberId\":" + member.getId() + "}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("{\"message\":\"로그아웃 성공\"}");
    }


}
