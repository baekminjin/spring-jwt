package com.ll.spring_jwt.global.security;

import com.ll.spring_jwt.domain.member.member.entity.Member;
import com.ll.spring_jwt.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//시큐리티의 자기가 만든 회원을 고집하지 않고 특정 회원 loadUserByUsername을 통해 가져온다
//데이터베이스에서 사용자 정보를 가져와서 스프링 시큐리티가 이해할 수 있는 형태(UserDetails)로 변환
//스프링 시큐리티는 회원 구조를 모른다.
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

		return new User(
				member.getUsername(),
				member.getPassword(),
				List.of()
		);
	}
}
