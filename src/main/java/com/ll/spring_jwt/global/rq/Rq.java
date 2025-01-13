package com.ll.spring_jwt.global.rq;

import com.ll.spring_jwt.domain.member.member.entity.Member;
import com.ll.spring_jwt.domain.member.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

// Request/Response 를 추상화한 객체
// Request, Response, Cookie, Session 등을 다룬다.
@RequestScope
@Component
@RequiredArgsConstructor
public class Rq {
    private final MemberService memberService;
    private final HttpServletRequest request;



    // 스프링 시큐리티가 이해하는 방식으로 강제 로그인 처리
    // 임시함수
    // 강제로 user1로 로그인 처리
    public void setLogin(String username) {
        UserDetails user = new User(
                username,
                "",
                List.of()
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Member getActor() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null)
            return null;
        if (authentication.getPrincipal() == null || authentication.getPrincipal() instanceof String)
            return null;

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String username = user.getUsername();
        return memberService.findByUsername(username).get();
    }
}
