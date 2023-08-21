package com.gmail.javacoded78.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Date;

import static com.gmail.javacoded78.constants.ErrorMessage.JWT_TOKEN_EXPIRED;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.header:Authorization}")
    private String authorizationHeader;

    @Value("${jwt.secret:randomToken}")
    private String secret;

    @Value("${jwt.expiration:6048000}")
    private long validityInMilliseconds;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds * 1000))
                .signWith(generateKey(secret))
                .compact();
    }

    public String resolveToken(ServerHttpRequest request) {
        return request.getHeaders().getFirst(authorizationHeader);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(generateKey(secret))
                    .build()
                    .parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date(System.currentTimeMillis()));
        } catch (JwtException | IllegalArgumentException exception) {
            throw new JwtAuthenticationException(JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED);
        }
    }

    public String parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey(secret))
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    @SneakyThrows
    public SecretKey generateKey(String secret) {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(secret.toCharArray());
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }
}
