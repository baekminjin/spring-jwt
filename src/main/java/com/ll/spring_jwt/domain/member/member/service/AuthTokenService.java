package com.ll.spring_jwt.domain.member.member.service;

import com.ll.spring_jwt.domain.member.member.entity.Member;
import com.ll.spring_jwt.standard.util.Ut;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Service
public class AuthTokenService {
	//정보
	@Value("${custom.jwt.secretKey}")
	private String jwtSecretKey;
	@Value("${custom.accessToken.expirationSeconds}")
	private long accessTokenExpirationSeconds;

	public String genAccessToken(Member member) {
		long id = member.getId();
		String username = member.getUsername();
		return Ut.jwt.toString(
				jwtSecretKey,
				accessTokenExpirationSeconds,
				Map.of("id", id, "username", username)
		);
	}

	public Map<String, Object> payload(String accessToken) {
		Map<String, Object> parsedPayload = Ut.jwt.payload(jwtSecretKey, accessToken);

		if (parsedPayload == null) return null;
		long id = (long) (Integer) parsedPayload.get("id"); //id가 long이므로 형변환
		String username = (String) parsedPayload.get("username");
		return Map.of("id", id, "username", username);
	}
}