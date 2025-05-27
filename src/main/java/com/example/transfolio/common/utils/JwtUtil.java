package com.example.transfolio.common.utils;

import com.example.transfolio.domain.user.model.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class JwtUtil {

    // JWT Token 발급
    public static String createToken(String loginId, String key, long expireTimeMs) {
        // Claim = Jwt Token에 들어갈 정보
        // Claim에 loginId를 넣어 줌으로써 나중에 loginId를 꺼낼 수 있음
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // Claims에서 loginId 꺼내기
    public static String getLoginId(String token, String secretKey) {
        try {
            Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            return body.get("loginId").toString();
        } catch (JwtException e) {
            log.error("JWT 파싱 오류: {}", e.getMessage()); // 파싱 오류 발생 시 로그 남기기
            throw new RuntimeException("JWT 토큰 파싱 오류");
        }
    }

    // 밝급된 Token이 만료 시간이 지났는지 체크
    public static boolean isExpired(String token, String secretKey) {

        try {
            Date expiredDate = extractClaims(token, secretKey).getExpiration();
            return expiredDate.before(new Date()); //현재 시간이 만료 시간을 초과한 경우 true 반환
        } catch (Exception e) {
            log.error("JWT 만료 검사 오류: {}", e.getMessage()); // 만료 검사 시 오류 발생 시 로그 남기기
            return true;
        }

        //return false;
    }

    // SecretKey를 사용해 Token Parsing
    private static Claims extractClaims(String token, String secretKey) throws Exception {
    try {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return body;
    } catch (JwtException e) {
        log.error("JWT 파싱 오류: {}", e.getMessage()); // 파싱 오류 발생 시 로그 남기기
        throw new JwtException("JWT 파싱 오류", e); // 예외 던지기
    }
    }

}