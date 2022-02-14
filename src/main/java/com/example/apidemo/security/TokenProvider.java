package com.example.apidemo.security;

import com.example.apidemo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/*
* 사용자의 정보를 받아 JWT를 생성하는 클래스
 */
@Slf4j
@Service
public class TokenProvider {

    private static final String SECRET_KEY = "NMA8JPctFuna59f5"; // 임의의 키?

    // JWT 토큰 생성
    public String create(UserEntity userEntity) {

        // 오늘 날짜에서 하루 더한 날짜
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)

        );

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // header에 들어갈 내용 및 서명을 위한 시크릿키

                // payload에 들어갈 내용
                .setSubject(userEntity.getId()) // sub
                .setIssuer("demo app") // iss
                .setIssuedAt(new Date()) // iat
                .setExpiration(expiryDate) // exp
                .compact();
    }

    // 토큰 디코딩 및 파싱의 위조 여부 확인
    public String validateAndGetUserId(String token) {

        // claims는 payload 리턴
        // 위조된 경우 예외처리됨
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
