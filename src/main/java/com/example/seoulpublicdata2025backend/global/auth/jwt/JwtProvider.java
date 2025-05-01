package com.example.seoulpublicdata2025backend.global.auth.jwt;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoIdStatusDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type.MemberStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String rawSecretKey;

    private SecretKey secretKey;

    @Value("${jwt.expireDate.accessToken}")
    private long accessTokenValidityInMillis;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(rawSecretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성
    public String createToken(Long kakaoId, MemberStatus memberStatus) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenValidityInMillis);

        return Jwts.builder()
                .setSubject(kakaoId.toString())
                .claim("role", memberStatus.getValue())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 사용자 ID 추출
    public String getKakaoIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }

    // 토큰 유효성 검증
    public boolean validationToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
